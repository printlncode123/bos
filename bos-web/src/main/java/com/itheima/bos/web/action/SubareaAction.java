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
	 * ��ѯ����δ�����������ķ���
	 */
	public String listajax(){
		List<Subarea> subareas=subareaServiceImpl.findNotAssociation();
		java2JsonArrayString(subareas, new String[]{"decidedzone","region"});
		return NONE;
	}
	
	/**
	 *��ӷ���
	 */
	public String add(){
		subareaServiceImpl.save(model);
		return "sublist";
	}
	/**��ҳ��ѯ����*/
	public String pageQuery(){
		//��ȡ��װ��pagebean�е����߲�ѯ����
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//���ղ�ѯ�����Ĳ���
		String addresskey = model.getAddresskey();//��ַ�ؼ���
		if (StringUtils.isNotBlank(addresskey)) {
			detachedCriteria.add(Restrictions.like("addresskey", "%"+addresskey+"%"));//���ģ����ѯ����
		}
		//���ù�������ı�������һ������������������һ�£��ڶ������Ǳ���
		detachedCriteria.createAlias("region", "r");
		Region region = model.getRegion();
		if (region!=null) {
			String province = region.getProvince();//ʡ
			if (StringUtils.isNotBlank(province)) {
				detachedCriteria.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			String city = region.getCity();//��
			if (StringUtils.isNotBlank(city)) {
				detachedCriteria.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			String district = region.getDistrict();//��
			if (StringUtils.isNotBlank(district)) {
				detachedCriteria.add(Restrictions.like("r.district", "%"+district+"%"));
			}

		}
		subareaServiceImpl.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"decidedzone","currentPage","pageSize","detachedCriteria","subareas"});
		return NONE;
	}
	/**
	 * �����������ݵ�excel�ļ����浽����
	 *   ��ѯ���ݿ������з�������Ϣ
	 *   ����ѯ��������д��excel�ļ�
	 *   ��excel�ļ������������ʽ���������
	 * @throws IOException 
	 */
	public String exportXls() throws IOException{
		//��ѯ���ݿ������з�������Ϣ
		List<Subarea> subarealist=subareaServiceImpl.findAll();
		//����ѯ��������д��excel�ļ�
		HSSFWorkbook workbook=new HSSFWorkbook();//���ڴ��д���һ��excel�ļ�����(��ȫ�հ���sheetҳ)
		HSSFSheet createSheet = workbook.createSheet("������Ϣ");//�ڿհ�excel�ļ��д���sheetҳ
		HSSFRow headRow = createSheet.createRow(0);//��sheet�д����У�����һ����Ϊ������
		headRow.createCell(0).setCellValue("�������");
		headRow.createCell(1).setCellValue("��ʼ���");
		headRow.createCell(2).setCellValue("�������");
		headRow.createCell(3).setCellValue("λ��");
		headRow.createCell(4).setCellValue("ʡ����");
		for (Subarea subarea : subarealist) {
			HSSFRow row=createSheet.createRow(createSheet.getLastRowNum()+1);//��ȡsheet���һ�е�������1��Ϊ������������е�����
			row.createCell(0).setCellValue(subarea.getId());
			row.createCell(1).setCellValue(subarea.getStartnum());
			row.createCell(2).setCellValue(subarea.getEndnum());
			row.createCell(3).setCellValue(subarea.getAddresskey());
			row.createCell(4).setCellValue(subarea.getRegion().getName());
			
		}
		//һ��(�����)��ͷ(ͷ��Ϣ)
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();//�����
		/*ServletActionContext.getResponse().setContentType("application/vnd.ms-excel");//�����ļ����ʹ˴���excel�ļ���׺Ϊ.xls
		//content-dispositionһ�����Ա�ʾ�û��������ݴ�Ϊһ���ļ���ʲô��ʽ���ظ��ļ���δ���ļ��ṩĬ����
		//attachment��ʾ�Ը�������ʽ����
		//filename=?���ṩĬ���ļ���
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=subarea.xls");*/
		/*<mime-mapping>
        <extension>xls</extension>
        <mime-type>application/vnd.ms-excel</mime-type>
        </mime-mapping>*/
		String filename="������.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");//��ȡagent��ֵ
		filename = FileUtils.encodeDownloadFilename(filename,agent);
		ServletActionContext.getServletContext().getMimeType(filename);//�����ļ���ȡ�ļ�����
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);//�Ը�����ʽ���أ�����Ĭ���ļ���Ϊfilename��ֵ
		//��excel�ļ������������ʽ���������
		workbook.write(out);
		return NONE;
	}
	/**
	 * ���ݶ���id��ѯ������Ϣ
	 * @return
	 */
	public String findListByDecidedzoneId(){
		//��ȡǰ�˴��ݵĲ���������id
		String decidedzoneId = model.getDecidedzone().getId();
		//���ݶ���id��ѯ������Ϣ
		List<Subarea> subareas = subareaServiceImpl.findListByDecidedzoneId(decidedzoneId);
		//����ѯ�ķ����б�ת��json��Ӧ���ͻ��������,��Ϊ��Ҫ�õ�����������ԣ�ʡ���С������Ե��޸�subarea.hbm.xml���region,ʹ����������
		this.java2JsonArrayString(subareas, new String[]{"subareas","decidedzone"});
		return NONE;
	}
	
	public String findSubareasGroupByProvince(){
		List<Object> list=subareaServiceImpl.findSubareasGroupByProvince();
		this.java2JsonArrayString(list, new String[]{});
		return NONE;
	}
}
