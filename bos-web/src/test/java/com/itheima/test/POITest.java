package com.itheima.test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class POITest {
	@Test
	public void testPOI() throws FileNotFoundException, IOException{
	String path="E:\\eclipse workspace\\bos-parent\\bos-web\\src\\main\\resources\\�������������.xls";
		//��װһ��excel�ļ�����
	HSSFWorkbook hWorkbook=new HSSFWorkbook(new FileInputStream(path));
	//��ȡ��excel�ļ��ĵ�һ��sheetҳ
	HSSFSheet sheetAt = hWorkbook.getSheetAt(0);
	//����sheet����
	for (Row row : sheetAt) {
		System.out.println();
		//����ÿ�е���
		for (Cell cell : row) {
			System.out.print(cell+"  ");
		}
	}
	}
}
