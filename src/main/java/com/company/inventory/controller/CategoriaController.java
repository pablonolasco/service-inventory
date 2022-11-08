package com.company.inventory.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.CategoriaEntity;
import com.company.inventory.response.CategoriaResponse;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.CategoriaService;
import com.company.inventory.util.CategoriaExcelExporter;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/categorias")
	public ResponseEntity<?> listarCategorias() {
		ResponseEntity<CategoryResponseRest> responseEntity = categoriaService.buscar();
		return responseEntity;
	}

	@GetMapping("/categoria/{id}")
	public ResponseEntity<?> buscarCategoria(@PathVariable Long id) {
		ResponseEntity<CategoryResponseRest> responseEntity = categoriaService.obtenerPorId(id);
		return responseEntity;
	}

	@PostMapping("/categoria")
	public ResponseEntity<?> crearCategoria(@RequestBody CategoriaEntity categoriaEntity) {
		ResponseEntity<CategoryResponseRest> response = categoriaService.crearCategoria(categoriaEntity);
		return response;
	}

	@PutMapping("/categoria/{id}")
	public ResponseEntity<?> actualizarCategoria(@RequestBody CategoriaEntity categoriaEntity, @PathVariable Long id) {
		ResponseEntity responseEntity = categoriaService.actualizar(categoriaEntity, id);
		return responseEntity;
	}

	@DeleteMapping("/categoria/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		ResponseEntity<CategoryResponseRest> responseEntity = categoriaService.desactivarCategoria(id);
		return responseEntity;
	}

	@GetMapping("/categoria/export/documento-excel")
	public void descargarExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=result_category.xlsx";

		response.setHeader(headerKey, headerValue);

		ResponseEntity<CategoryResponseRest> categoriaResponse = categoriaService.buscar();
		
		CategoriaExcelExporter categoriaExcelExporter = new CategoriaExcelExporter(
				categoriaResponse.getBody().getCategoriaResponse().getCategoriaEntities());
		categoriaExcelExporter.exportar(response);
	}
}
