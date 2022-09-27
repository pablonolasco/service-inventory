package com.company.inventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.inventory.dao.CategoriaDao;
import com.company.inventory.dao.ProductoDao;
import com.company.inventory.model.CategoriaEntity;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.response.ProductoResponseRest;
import com.company.inventory.services.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoDao productoDao;
	private final CategoriaDao categoriaDao;
	
	
	public ProductoServiceImpl(ProductoDao productoDao, CategoriaDao categoriaDao) {
		this.productoDao = productoDao;
		this.categoriaDao= categoriaDao;
	}

	@Override
	public ResponseEntity<List<ProductoResponseRest>> obtenerProductos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ProductoResponseRest> obtenerProductoPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ProductoResponseRest> guardarProducto(ProductoEntity producto, Long idCategoria) {
		ProductoResponseRest productoResponseRest= new ProductoResponseRest();
		List<ProductoEntity> listProductoEntities= new ArrayList<>();
		
		try {
			Optional<CategoriaEntity>categoria=categoriaDao.findById(idCategoria);
			
			if (categoria.isPresent()) {
				producto.setCategoriaEntity(categoria.get());
			}else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND.value()),
						"Categoria no encontrada");
				return new ResponseEntity<ProductoResponseRest>(productoResponseRest,HttpStatus.NOT_FOUND);
			}
			
			ProductoEntity productoEntity=productoDao.saveAndFlush(producto);
			if (producto != null) {
				listProductoEntities.add(productoEntity);
				productoResponseRest.getProductoResponse().setProductoEntities(listProductoEntities);
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CREATED),"Producto registrado");
			} else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.BAD_REQUEST.value()),
						"Producto no registrado");
				return new ResponseEntity<ProductoResponseRest>(productoResponseRest,HttpStatus.BAD_REQUEST);

			}
			
			
			
		} catch (Exception e) {
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
					"Error al guardar el registro");
			return new ResponseEntity<ProductoResponseRest>(productoResponseRest,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProductoResponseRest>(productoResponseRest,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductoResponseRest> actualizarProducto(ProductoEntity entity, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ProductoResponseRest> desactivarProducto(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
