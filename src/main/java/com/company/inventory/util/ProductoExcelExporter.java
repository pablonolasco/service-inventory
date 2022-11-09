package com.company.inventory.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.inventory.model.ProductoEntity;

public class ProductoExcelExporter {
	
	private XSSFWorkbook workbook;

	private XSSFSheet sheet;

	private List<ProductoEntity>productoEntities;
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductoExcelExporter.class);

	

	public ProductoExcelExporter(List<ProductoEntity> productoEntities) {
		super();
		this.productoEntities = productoEntities;
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
		this.createCellRow(row, 2, "Cantidad", style);
		this.createCellRow(row, 3, "Precio", style);
		this.createCellRow(row, 4, "Categoria", style);
	}
	
	private void createCellRow(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell= row.createCell(columnCount);
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else if(value instanceof BigDecimal){
			Object obj = value;
			String precio = String.valueOf(obj);  //method 1
		
			cell.setCellValue(precio);
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
		int rowCount=1;
				
		for (ProductoEntity producto : productoEntities) {
			Row row= sheet.createRow(rowCount++);
			int columnCount=0;
			createCellRow(row, columnCount++, String.valueOf(producto.getId()), style);
			createCellRow(row, columnCount++, producto.getNombre(), style);
			createCellRow(row, columnCount++, producto.getCantidad(), style);
			createCellRow(row, columnCount++, producto.getPrecio(), style);
			createCellRow(row, columnCount++, producto.getCategoriaEntity().getNombre(), style);
			
			
		}
	}
	
	public void exportar(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		
		ServletOutputStream outputStream= response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.close();
	}


}
