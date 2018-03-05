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
	private File regionFile;//����ҳ�洫�ݵ�file����(regionFile)
	@Autowired
	private IRegionService regionService;
	public String region() throws FileNotFoundException, IOException {
		//����excel�ļ�������ݣ�������д�����ݿ��region����
				//��װһ��excel�ļ�����
				HSSFWorkbook hwWorkbook=new HSSFWorkbook(new FileInputStream(regionFile));
				//��ȡ��һ��sheetҳ
				/*hwWorkbook.getSheetAt(0);*/
				HSSFSheet sheet = hwWorkbook.getSheet("Sheet1");
				List<Region> regions=new ArrayList<Region>();//������װÿһ��
				//ѭ������ÿһ��
				for (Row row : sheet) {
					//ȥ����һ�У����⣩
					if (row.getRowNum()==1) {//����ǵ�һ�о�������ִ�н�����һ�ε�ѭ��
						continue;
					}
					//����ѭ������ÿһ��
					String id = row.getCell(0).toString();//������
					String province = row.getCell(1).toString();//ʡ��
					String city = row.getCell(2).toString();//����
					String district = row.getCell(3).toString();//����
					String postcode = row.getCell(4).toString();//�ʱ�
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
	
//	��ҳ��ѯ
	
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
	
	//���������Ϊÿ��������������ͨ��������ѡ��
	//mode string ��������μ����б����ݵ��ı��ı䡣����Ϊ��remote����������б��ӷ��������ء�������Ϊ��remot��ģʽ��,�û����ͽ������͵�http�����������Ϊ��q�������������������ݡ�
	private String q;//����������������ʱ�����������Ľ���ģ��ƥ����ʾ���������У����q��ʾ����������͵Ĳ�����������������������ݣ�
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String ajaxlist(){//����ajax����ˢ��ҳ�棨ҳ�治��ת������Ҫ�ύ���ֲ���ˢ��ҳ��ʱ����ʹ��<iframe src="">
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
