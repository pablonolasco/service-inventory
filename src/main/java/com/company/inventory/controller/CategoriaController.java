package com.company.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.CategoriaService;

@RestController
@RequestMapping("/api/v1")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/categorias")
	public ResponseEntity<?>listarCategorias(){
		ResponseEntity<CategoryResponseRest>responseEntity=categoriaService.buscar();
		return responseEntity;
	}
	
	@GetMapping("/categoria/{id}")
	public ResponseEntity<?>buscarCategoria(@PathVariable Long id){
		ResponseEntity<CategoryResponseRest> responseEntity=categoriaService.obtenerPorId(id);
		return responseEntity;
	}
}
