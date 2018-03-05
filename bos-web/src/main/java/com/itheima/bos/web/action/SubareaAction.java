package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	
	private static final long serialVersionUID = 1L;
	@Resource
	private ISubareaService subareaServiceImpl;
	
	/**
	 * 查询所有未关联到定区的分区
	 */
	public String listajax(){
		List<Subarea> subareas=subareaServiceImpl.findNotAssociation();
		java2JsonArrayString(subareas, new String[]{"decidedzone","region"});
		return NONE;
	}
	
	/**
	 *添加分区
	 */
	public String add(){
		subareaServiceImpl.save(model);
		return "sublist";
	}
	/**分页查询分区*/
	public String pageQuery(){
		//获取封装在pagebean中的离线查询条件
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//接收查询条件的参数
		String addresskey = model.getAddresskey();//地址关键字
		if (StringUtils.isNotBlank(addresskey)) {
			detachedCriteria.add(Restrictions.like("addresskey", "%"+addresskey+"%"));//添加模糊查询条件
		}
		//设置关联对象的别名，第一个参数必须与属性名一致，第二参数是别名
		detachedCriteria.createAlias("region", "r");
		Region region = model.getRegion();
		if (region!=null) {
			String province = region.getProvince();//省
			if (StringUtils.isNotBlank(province)) {
				detachedCriteria.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			String city = region.getCity();//市
			if (StringUtils.isNotBlank(city)) {
				detachedCriteria.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			String district = region.getDistrict();//区
			if (StringUtils.isNotBlank(district)) {
				detachedCriteria.add(Restrictions.like("r.district", "%"+district+"%"));
			}

		}
		subareaServiceImpl.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"decidedzone","currentPage","pageSize","detachedCriteria","subareas"});
		return NONE;
	}
	/**
	 * 分区导出数据到excel文件保存到本地
	 *   查询数据库中所有分区的信息
	 *   将查询到的数据写到excel文件
	 *   将excel文件以输出流的形式输出到本地
	 * @throws IOException 
	 */
	public String exportXls() throws IOException{
		//查询数据库中所有分区的信息
		List<Subarea> subarealist=subareaServiceImpl.findAll();
		//将查询到的数据写到excel文件
		HSSFWorkbook workbook=new HSSFWorkbook();//在内存中创建一个excel文件对象(完全空白无sheet页)
		HSSFSheet createSheet = workbook.createSheet("分区信息");//在空白excel文件中穿件sheet页
		HSSFRow headRow = createSheet.createRow(0);//在sheet中创建行，将第一行作为标题行
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("起始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置");
		headRow.createCell(4).setCellValue("省市区");
		for (Subarea subarea : subarealist) {
			HSSFRow row=createSheet.createRow(createSheet.getLastRowNum()+1);//获取sheet最后一行的索引加1作为除标题行外的行的索引
			row.createCell(0).setCellValue(subarea.getId());
			row.createCell(1).setCellValue(subarea.getStartnum());
			row.createCell(2).setCellValue(subarea.getEndnum());
			row.createCell(3).setCellValue(subarea.getAddresskey());
			row.createCell(4).setCellValue(subarea.getRegion().getName());
			
		}
		//一流(输出流)两头(头信息)
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();//输出流
		/*ServletActionContext.getResponse().setContentType("application/vnd.ms-excel");//设置文件类型此处是excel文件后缀为.xls
		//content-disposition一个属性表示用户请求内容存为一个文件以什么方式下载该文件并未该文件提供默认名
		//attachment表示以附件的形式下载
		//filename=?是提供默认文件名
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=subarea.xls");*/
		/*<mime-mapping>
        <extension>xls</extension>
        <mime-type>application/vnd.ms-excel</mime-type>
        </mime-mapping>*/
		String filename="分区表.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");//获取agent的值
		filename = FileUtils.encodeDownloadFilename(filename,agent);
		ServletActionContext.getServletContext().getMimeType(filename);//根据文件获取文件类型
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);//以附件形式下载，并且默认文件名为filename的值
		//将excel文件以输出流的形式输出到本地
		workbook.write(out);
		return NONE;
	}
	/**
	 * 根据定区id查询分区信息
	 * @return
	 */
	public String findListByDecidedzoneId(){
		//获取前端传递的参数：定区id
		String decidedzoneId = model.getDecidedzone().getId();
		//根据定区id查询分区信息
		List<Subarea> subareas = subareaServiceImpl.findListByDecidedzoneId(decidedzoneId);
		//将查询的分区列表转成json响应到客户端浏览器,因为需要用到区域里的属性：省、市、区所以得修改subarea.hbm.xml里的region,使其立即加载
		this.java2JsonArrayString(subareas, new String[]{"subareas","decidedzone"});
		return NONE;
	}
	
	public String findSubareasGroupByProvince(){
		List<Object> list=subareaServiceImpl.findSubareasGroupByProvince();
		this.java2JsonArrayString(list, new String[]{});
		return NONE;
	}
}
