package com.xuanwu.competency_model.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.xuanwu.competency_model.annotation.ExcelFieldName;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月4日
 * @version 1.0.0
 */
@Slf4j
public class ExcelExporter<T> {

	/**
	 * @param title
	 *            文档标题,对应第一行，会做跨列。不需要时请传null
	 * @param itemList
	 *            要导出的数据集
	 * @param excludeFiledString
	 *            已有注解标注，但本次导出又不需要的字段
	 * @return 最终生成的文件名
	 */
	public String exportExcel(String title, List<T> itemList, String... excludeFiledString) {
		Set<String> excludeFileds = new HashSet<>();
		for (String field : excludeFiledString) {
			excludeFileds.add(field);
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		T first = itemList.get(0);
		Class<?> clazz = first.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<Integer, ExcelFieldName> headers = new TreeMap<>();
		Map<Integer, String> headNameMap = new HashMap<>();
		List<Method> methods = new ArrayList<>();
		// 根据注解获得导出列的顺序 中文标题等
		for (Field field : fields) {
			ExcelFieldName excelFieldName = field.getAnnotation(ExcelFieldName.class);
			if (excelFieldName != null && !excludeFileds.contains(field.getName())) {
				int index = excelFieldName.index();
				String fieldName = field.getName();
				while (headers.get(index) != null) {
					index++;
				}

				headers.put(index, excelFieldName);
				String label = excelFieldName.label();
				if (StringUtils.isBlank(label)) {
					label = fieldName;
				}
				headNameMap.put(index, fieldName);
			}
		}
		HSSFCell cell;
		int rowNumber = 0;
		if (title != null) {
			// 标题行
			HSSFRow caption = sheet.createRow(rowNumber++);
			HSSFCellStyle captionStyle = wb.createCellStyle();
			captionStyle.setAlignment(HorizontalAlignment.CENTER);
			captionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFFont captionStyleFont = wb.createFont();
			captionStyleFont.setBold(true);
			captionStyleFont.setFontHeightInPoints((short) 24);
			captionStyle.setFont(captionStyleFont);
			cell = caption.createCell(0);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(title);
			cell.setCellStyle(captionStyle);
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, headers.size() - 1); // 起始行, 终止行, 起始列, 终止列
			sheet.addMergedRegion(cra);
		}

		// head行
		HSSFRow row = sheet.createRow(rowNumber++);
		HSSFCellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont hssfFont = wb.createFont();
		hssfFont.setBold(true);
		hssfFont.setFontHeightInPoints((short) 24);
		headerStyle.setFont(hssfFont);
		// 选出具体要到出的列 及method方法
		int columnNumber = 0;
		for (Entry<Integer, ExcelFieldName> entry : headers.entrySet()) {
			String header = entry.getValue().label();
			if (header == null) {
				continue;
			}

			sheet.setColumnWidth(columnNumber, entry.getValue().width());
			cell = row.createCell(columnNumber++);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
			String filedName = headNameMap.get(entry.getKey());

			Method method;
			try {
				method = clazz.getMethod("get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1));
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
		for (T item : itemList) {
			row = sheet.createRow(rowNumber++);
			row.setHeightInPoints(40);
			int cellNumber = 0;
			for (Method method : methods) {
				Object value = null;
				if (method != null) {
					try {
						value = method.invoke(item, new Object[] {});
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						log.error("invoke method {} on class {} error,message: {}", method.getName(), clazz.getName(),
								e.getMessage());
						value = "";
					}

				}
				value = value == null ? "" : String.valueOf(value);
				cell = row.createCell(cellNumber++);
				cell.setCellValue(String.valueOf(value));
				cell.setCellStyle(cellStyle);
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
			log.error("write file error:{}", e.getMessage());
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				log.error("close sheet error:{}", e.getMessage());
			}
		}
		return null;
	}

}