package com.company.inventory.controller;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.model.ProductoEntity;
import com.company.inventory.services.ProductoService;
import com.company.inventory.util.UtilImagen;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@GetMapping("/productos")
	public ResponseEntity<?> obtener() {
		return productoService.obtenerProductos();
	}

	@PostMapping("/producto")
	public ResponseEntity<?> crear(@RequestParam(name = "nombre", required = true) String nombre,
			@RequestParam(name = "precio", required = true) BigDecimal precio,
			@RequestParam(name = "cantidad", required = true) Integer cantidad,
			@RequestParam(name = "imagen", required = true) MultipartFile imagen,
			@RequestParam(name = "idCategoria", required = true) Long idCategoria) throws IOException {
		ProductoEntity productoEntity = new ProductoEntity();
		productoEntity.setNombre(nombre);
		productoEntity.setPrecio(precio);
		productoEntity.setCantidad(cantidad);
		productoEntity.setImagen(UtilImagen.compressZLib(imagen.getBytes()));
		return productoService.guardarProducto(productoEntity, idCategoria);
	}

	@PutMapping("/producto/{id}")
	public ResponseEntity<?> actualizar(@PathVariable(name = "id", required = true) Long id,
			@RequestParam(name = "nombre", required = true) String nombre,
			@RequestParam(name = "precio", required = true) BigDecimal precio,
			@RequestParam(name = "cantidad", required = true) Integer cantidad,
			@RequestParam(name = "imagen", required = false) MultipartFile imagen,
			@RequestParam(name = "idCategoria", required = true) Long idCategoria) throws IOException {
		ProductoEntity productoEntity = new ProductoEntity();
		productoEntity.setId(id);
		productoEntity.setNombre(nombre);
		productoEntity.setPrecio(precio);
		productoEntity.setCantidad(cantidad);
		productoEntity.setImagen(imagen != null ? UtilImagen.compressZLib(imagen.getBytes()) : null);
		return productoService.actualizarProducto(productoEntity, idCategoria);
	}

	@GetMapping("/producto/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		return productoService.obtenerProductoPorId(id);
	}

	@GetMapping("/producto")
	public ResponseEntity<?> buscar(@RequestParam(name = "nombre", required = true) String nombre) {
		return productoService.obtenerProductoPorNombre(nombre);
	}

	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> desactivar(@PathVariable Long id) {
		return productoService.desactivarProducto(id);
	}

}
