package com.xuanwu.competency_model.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.xuanwu.competency_model.annotation.ExcelFieldName;
import com.xuanwu.competency_model.entity.Competency;

/**
 * @Description 
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月4日
 * @version 1.0.0
 */
public class ExcelUtil<T> {

	private static final String ROOTFOLDER = "file";

	public String exportExcel(String title, List<T> itemList) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 20000);
		sheet.setColumnWidth(2, 20000);

		T first = itemList.get(0);
		Class<?> clazz = first.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<String> headers = new ArrayList<>(fields.length);
		Map<String, String> headNameMap = new HashMap<>();
		List<Method> methods = new ArrayList<>();

		for (Field field : fields) {
			ExcelFieldName excelFieldName = field.getAnnotation(ExcelFieldName.class);
			if (excelFieldName != null) {
				int index = excelFieldName.index();
				String fieldName = field.getName();
				headers.add(index, fieldName);
				String label = excelFieldName.label();
				if (StringUtils.isBlank(label)) {
					label = fieldName;
				}
				headNameMap.put(fieldName, label);
			}
		}

		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont hssfFont = wb.createFont();
		hssfFont.setBold(true);
		hssfFont.setFontHeightInPoints((short) 24);
		headerStyle.setFont(hssfFont);
		HSSFCell cell;
		for (int i = 0; i < headers.size(); i++) {
			cell = row.createCell(i);
			String header = headers.get(i);
			cell.setCellValue(headNameMap.get(header));
			cell.setCellStyle(headerStyle);

			Method method;
			try {
				method = clazz.getMethod("get" + header.substring(0, 1).toUpperCase() + header.substring(1));
			} catch (NoSuchMethodException | SecurityException e) {
				method = null;
			}
			methods.add(method);
		}

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) 16);
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(true);

		int rowNumber = 1;
		for (T item : itemList) {
			row = sheet.createRow(rowNumber++);
			row.setHeightInPoints(40);
			int cellNumber = 0;
			for (Method method : methods) {
				if (method != null) {
					Object value;
					try {
						value = method.invoke(item, new Object[] {});
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						value = "";
					}
					value = value == null ? "" : String.valueOf(value);
					cell = row.createCell(cellNumber++);
					cell.setCellValue(String.valueOf(value));
					cell.setCellStyle(cellStyle);
				}
			}
		}

		try {
			File file = new File(ROOTFOLDER);
			if(!file.exists()){
				file.mkdirs();
			}
			String name = UUID.randomUUID().toString();
			String fileName = "file/" + name + ".xls";
			FileOutputStream exportXls = new FileOutputStream(fileName);
			wb.write(exportXls);
			exportXls.close();
			return name;
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * 使用例子
	 */
	public static void main(String[] args) {
		List<Competency> list = new ArrayList<>();
		Competency c = new Competency();
		c.setName("资源统驭");
		c.setDefinition("重视资源的优化配置和投入产出比，系统、灵活地采集、调动和配置资源，最高限度地提高资源的利用效率，促进资源的增值和发展。");
		c.setDescription("敢于在行业中、市场中胜出，不畏惧强大的对手，敢于采取行动与强大的对手展开竞争，并力保胜出，力求成为所在领域的标杆；	");
		list.add(c);
		ExcelUtil<Competency> util = new ExcelUtil<>();
		util.exportExcel("sss", list);
	}
}