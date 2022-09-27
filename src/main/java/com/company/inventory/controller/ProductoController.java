package com.company.inventory.controller;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.model.ProductoEntity;
import com.company.inventory.response.ProductoResponseRest;
import com.company.inventory.services.ProductoService;
import com.company.inventory.util.UtilImagen;

@RestController
@RequestMapping("/")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@PostMapping("producto")
	public ResponseEntity<?>crear(
			@RequestParam(name = "nombre", required = true)String nombre,
			@RequestParam(name = "precio", required = true)BigDecimal precio,
			@RequestParam(name = "cantidad", required = true)Integer cantidad,
			@RequestParam(name = "imagen", required = true)MultipartFile imagen,
			@RequestParam(name = "idCategoria", required = true)Long idCategoria
			) throws IOException{
		ProductoEntity productoEntity= new ProductoEntity();
		productoEntity.setNombre(nombre);
		productoEntity.setPrecio(precio);
		productoEntity.setCantidad(cantidad);
		productoEntity.setImagen(UtilImagen.compressZLib(imagen.getBytes()));
		return productoService.guardarProducto(productoEntity, idCategoria);
	}
}
