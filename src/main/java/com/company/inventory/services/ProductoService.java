package com.company.inventory.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.ProductoEntity;
import com.company.inventory.response.ProductoResponseRest;

public interface ProductoService {

	ResponseEntity<ProductoResponseRest>obtenerProductos();
	ResponseEntity<ProductoResponseRest>obtenerProductoPorId(Long id);
	ResponseEntity<ProductoResponseRest>obtenerProductoPorNombre(String nombre);
	ResponseEntity<ProductoResponseRest>guardarProducto(ProductoEntity entity, Long idCategoria);
	ResponseEntity<ProductoResponseRest>actualizarProducto(ProductoEntity entity, Long id);
	ResponseEntity<ProductoResponseRest>desactivarProducto(Long id);

}
