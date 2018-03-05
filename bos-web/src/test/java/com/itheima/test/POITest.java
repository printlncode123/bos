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
	String path="E:\\eclipse workspace\\bos-parent\\bos-web\\src\\main\\resources\\区域导入测试数据.xls";
		//包装一个excel文件对象
	HSSFWorkbook hWorkbook=new HSSFWorkbook(new FileInputStream(path));
	//获取该excel文件的第一个sheet页
	HSSFSheet sheetAt = hWorkbook.getSheetAt(0);
	//遍历sheet的行
	for (Row row : sheetAt) {
		System.out.println();
		//遍历每行的列
		for (Cell cell : row) {
			System.out.print(cell+"  ");
		}
	}
	}
}
