package com.xuanwu.competency_model.common;

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
import java.util.Map.Entry;
import java.util.TreeMap;
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
import org.apache.poi.ss.util.CellRangeAddress;

import com.xuanwu.competency_model.annotation.ExcelFieldName;

/**
 * @Description
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月4日
 * @version 1.0.0
 */
public class ExcelExporter<T> {

	public String exportExcel(String title, List<T> itemList) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 20000);
		sheet.setColumnWidth(2, 20000);

		T first = itemList.get(0);
		Class<?> clazz = first.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<Integer,String> headers = new TreeMap<>();
		Map<String, String> headNameMap = new HashMap<>();
		List<Method> methods = new ArrayList<>();
		// 根据注解获得导出列的顺序 中文标题等
		for (Field field : fields) {
			ExcelFieldName excelFieldName = field.getAnnotation(ExcelFieldName.class);
			if (excelFieldName != null) {
				int index = excelFieldName.index();
				String fieldName = field.getName();
				while (headers.get(index) != null) {
					index++;
				}

				headers.put(index, fieldName);
				String label = excelFieldName.label();
				if (StringUtils.isBlank(label)) {
					label = fieldName;
				}
				headNameMap.put(fieldName, label);
			}
		}
		HSSFCell cell;
		// 标题行
		HSSFRow caption = sheet.createRow(0);
		HSSFCellStyle captionStyle = wb.createCellStyle();
		captionStyle.setAlignment(HorizontalAlignment.CENTER);
		captionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont captionStyleFont = wb.createFont();
		captionStyleFont.setBold(true);
		captionStyleFont.setFontHeightInPoints((short) 24);
		captionStyle.setFont(captionStyleFont);
		cell = caption.createCell(0);
		cell.setCellValue("职位名称:" + title);
		cell.setCellStyle(captionStyle);
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 2); // 起始行, 终止行, 起始列, 终止列
		sheet.addMergedRegion(cra);
		// head行
		HSSFRow row = sheet.createRow(1);
		HSSFCellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont hssfFont = wb.createFont();
		hssfFont.setBold(true);
		hssfFont.setFontHeightInPoints((short) 24);
		headerStyle.setFont(hssfFont);
		// 选出具体要到出的列 及method方法
		int rowNumber = 0;
		for (Entry<Integer,String> entry:headers.entrySet()) {
			String header = entry.getValue();
			if (header == null) {
				continue;
			}

			cell = row.createCell(rowNumber++);
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
		// 数据行 style
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) 12);
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(true);
		// 具体数据
		rowNumber = 2;
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
		// 生成文件
		try {
			File file = new File(Constants.ROOTFOLDER);
			if (!file.exists()) {
				file.mkdirs();
			}
			String name = UUID.randomUUID().toString() + Constants.EXCELTYPE;
			String fileName = Constants.ROOTFOLDER + File.separator + name;
			FileOutputStream exportXls = new FileOutputStream(fileName);
			wb.write(exportXls);
			exportXls.close();
			return name;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}