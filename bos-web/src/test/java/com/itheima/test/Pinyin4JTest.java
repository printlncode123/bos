package com.itheima.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.itheima.bos.utils.PinYin4jUtils;

public class Pinyin4JTest {
	@Test
	public void test() throws FileNotFoundException, IOException{
		String path="E:\\eclipse workspace\\bos-parent\\bos-web\\src\\main\\resources\\区域导入测试数据.xls";
		//包装一个excel文件对象
	HSSFWorkbook hWorkbook=new HSSFWorkbook(new FileInputStream(path));
	//获取该excel文件的第一个sheet页
	HSSFSheet sheetAt = hWorkbook.getSheetAt(0);
	//遍历sheet的行
	for (Row row : sheetAt) {
		System.out.println();
		/*//遍历每行的列
		for (Cell cell : row) {
			System.out.print(cell+"  ");
		}*/
		String id = row.getCell(0).toString();
		String province = row.getCell(1).toString();
		province=province.substring(0, province.length()-1);
		String city = row.getCell(2).toString();
		city=city.substring(0, city.length()-1);
		String district = row.getCell(3).toString();
		district=district.substring(0, district.length()-1);
		//通过province，city,district创建简码===》河北省 石家庄市 鹿泉市的简码(HBSJZLQ)
		String info=province+city+district;
		String[] headByString = PinYin4jUtils.getHeadByString(info);
		String join = StringUtils.join(headByString);
		System.out.println(join);
		String hanziToPinyin = PinYin4jUtils.hanziToPinyin(city,"");
		System.out.println(hanziToPinyin);
	}
	}
}
