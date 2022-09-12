package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.response.CategoryResponseRest;

public interface CategoriaService {

	public ResponseEntity<CategoryResponseRest> buscar();
}
