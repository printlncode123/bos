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
		String path="E:\\eclipse workspace\\bos-parent\\bos-web\\src\\main\\resources\\�������������.xls";
		//��װһ��excel�ļ�����
	HSSFWorkbook hWorkbook=new HSSFWorkbook(new FileInputStream(path));
	//��ȡ��excel�ļ��ĵ�һ��sheetҳ
	HSSFSheet sheetAt = hWorkbook.getSheetAt(0);
	//����sheet����
	for (Row row : sheetAt) {
		System.out.println();
		/*//����ÿ�е���
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
		//ͨ��province��city,district��������===���ӱ�ʡ ʯ��ׯ�� ¹Ȫ�еļ���(HBSJZLQ)
		String info=province+city+district;
		String[] headByString = PinYin4jUtils.getHeadByString(info);
		String join = StringUtils.join(headByString);
		System.out.println(join);
		String hanziToPinyin = PinYin4jUtils.hanziToPinyin(city,"");
		System.out.println(hanziToPinyin);
	}
	}
}
