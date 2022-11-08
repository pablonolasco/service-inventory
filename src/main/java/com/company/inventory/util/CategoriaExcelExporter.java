package com.company.inventory.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.company.inventory.model.CategoriaEntity;

public class CategoriaExcelExporter {

	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List<CategoriaEntity>categoriaEntities;
	
	private CategoriaExcelExporter (List<CategoriaEntity>entities) {
		this.categoriaEntities=entities;
		this.workbook= new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet=workbook.createSheet("Resultado");
		Row row= sheet.createRow(0);
		CellStyle style= workbook.createCellStyle();
		
		XSSFFont font= workbook.createFont();
		font.setBold(true);
		font.setFontHeight(17);
		style.setFont(font);
		
		this.createCellRow(row, 0, "ID", style);
		this.createCellRow(row, 1, "Nombre", style);
		this.createCellRow(row, 2, "Descripcion", style);
	}
	
	private void createCellRow(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell= row.createCell(columnCount);
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else {
			cell.setCellValue((String) value);
		}
		
		cell.setCellStyle(style);
	}
	
	
	private void writeDataLines() {
		CellStyle style = workbook.createCellStyle();
		XSSFFont font= workbook.createFont();
		
		font.setFontHeight(14);
		
		style.setFont(font);
		
		this.categoriaEntities.stream().forEach(categoria->{
			int rowCount=1;

			Row row= sheet.createRow(rowCount==1?rowCount++:rowCount++);
			int columnCount=0;
			createCellRow(row, columnCount++, String.valueOf(categoria.getId()), style);
			createCellRow(row, columnCount++, categoria.getNombre(), style);
			createCellRow(row, columnCount++, categoria.getDescripcion(), style);
			
		});
	}
	
	private void exportar(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeHeaderLine();
		
		ServletOutputStream outputStream= response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.close();
	}
}
