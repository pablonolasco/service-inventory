package com.company.inventory.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.response.CategoryResponseRest;

public interface CategoriaService {

	ResponseEntity<CategoryResponseRest> buscar();

	ResponseEntity<CategoryResponseRest> obtenerPorId(Long id);
}
