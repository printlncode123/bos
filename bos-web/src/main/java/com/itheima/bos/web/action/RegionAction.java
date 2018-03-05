package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	private File regionFile;//接收页面传递的file参数(regionFile)
	@Autowired
	private IRegionService regionService;
	public String region() throws FileNotFoundException, IOException {
		//解析excel文件里的数据，将数据写到数据库的region表中
				//包装一个excel文件对象
				HSSFWorkbook hwWorkbook=new HSSFWorkbook(new FileInputStream(regionFile));
				//获取第一个sheet页
				/*hwWorkbook.getSheetAt(0);*/
				HSSFSheet sheet = hwWorkbook.getSheet("Sheet1");
				List<Region> regions=new ArrayList<Region>();//用来封装每一行
				//循环遍历每一行
				for (Row row : sheet) {
					//去除第一行（标题）
					if (row.getRowNum()==1) {//如果是第一行就跳过不执行进入下一次的循环
						continue;
					}
					//否则循环遍历每一列
					String id = row.getCell(0).toString();//区域编号
					String province = row.getCell(1).toString();//省份
					String city = row.getCell(2).toString();//城市
					String district = row.getCell(3).toString();//区域
					String postcode = row.getCell(4).toString();//邮编
					Region region=new Region(id, province, city, district, postcode, null, null, null);
					province=province.substring(0, province.length()-1);
					city=city.substring(0, city.length()-1);
					district=district.substring(0, district.length()-1);
					String info=province+city+district;
					String[] headByString = PinYin4jUtils.getHeadByString(info);
					String shortcode = StringUtils.join(headByString);
					String citycode = PinYin4jUtils.hanziToPinyin(city,"");
					region.setCitycode(citycode);
					region.setShortcode(shortcode);
					regions.add(region);
				}
				
				regionService.saveBatch(regions);
		return NONE;
	}
	
//	分页查询
	
	public String pageQuery() throws IOException{
		regionService.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","subareas"});
		return NONE;
	}
	public File getRegionFile(){
		return regionFile;
	}
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
	//管理分区，为每个分区设置区域（通过下拉框选择）
	//mode string 定义了如何加载列表数据当文本改变。设置为“remote“如果下拉列表框从服务器加载。当设置为“remot“模式下,用户类型将被发送的http请求参数命名为“q”服务器来检索新数据。
	private String q;//当在下拉框中输入时会根据你输入的进行模糊匹配显示在下拉框中，这个q表示向服务器发送的参数（在下拉框中输入的内容）
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String ajaxlist(){//发送ajax请求不刷新页面（页面不跳转）；当要提交表单又不想刷新页面时可以使用<iframe src="">
		List<Region> regions=null;
		if (StringUtils.isNotBlank(q)) {
			regions=regionService.findByQ(q);
		}else{
		regions=regionService.findAll();
		}
		java2JsonArrayString(regions, new String[]{"subareas"});
		return NONE;
	}
}
