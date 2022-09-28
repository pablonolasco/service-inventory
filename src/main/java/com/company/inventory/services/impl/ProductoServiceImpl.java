package com.company.inventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.CategoriaDao;
import com.company.inventory.dao.ProductoDao;
import com.company.inventory.model.CategoriaEntity;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.response.ProductoResponseRest;
import com.company.inventory.services.ProductoService;
import com.company.inventory.util.UtilImagen;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoDao productoDao;
	private final CategoriaDao categoriaDao;

	public ProductoServiceImpl(ProductoDao productoDao, CategoriaDao categoriaDao) {
		this.productoDao = productoDao;
		this.categoriaDao = categoriaDao;
	}

	@Override
	public ResponseEntity<List<ProductoResponseRest>> obtenerProductos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductoResponseRest> obtenerProductoPorId(Long id) {
		ProductoResponseRest productoResponseRest = new ProductoResponseRest();
		List<ProductoEntity> productoEntities = new ArrayList<>();
		try {
			Optional<ProductoEntity> producto = productoDao.findById(id);
			if (!(producto.isPresent())) {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND),
						"No se encontro el producto");
				productoResponseRest.getProductoResponse().setProductoEntities(productoEntities);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);
			} else {
				byte [] imageDescomprimir=UtilImagen.decompressZLib(producto.get().getImagen());
				producto.get().setImagen(imageDescomprimir);
				productoEntities.add(producto.get());
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.OK),
						"Exito");
				productoResponseRest.getProductoResponse().setProductoEntities(productoEntities);
			}
		} catch (Exception e) {
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
					"Error al hacer la busqueda");
			productoResponseRest.getProductoResponse().setProductoEntities(productoEntities);
			return new ResponseEntity<>(productoResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public ResponseEntity<ProductoResponseRest> guardarProducto(ProductoEntity producto, Long idCategoria) {
		ProductoResponseRest productoResponseRest = new ProductoResponseRest();
		List<ProductoEntity> listProductoEntities = new ArrayList<>();

		try {
			Optional<CategoriaEntity> categoria = categoriaDao.findById(idCategoria);

			if (categoria.isPresent()) {
				producto.setCategoriaEntity(categoria.get());
			} else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND.value()),
						"Categoria no encontrada");
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);
			}

			ProductoEntity productoEntity = productoDao.saveAndFlush(producto);
			if (producto != null) {
				listProductoEntities.add(productoEntity);
				productoResponseRest.getProductoResponse().setProductoEntities(listProductoEntities);
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CREATED),
						"Producto registrado");
			} else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.BAD_REQUEST.value()),
						"Producto no registrado");
				return new ResponseEntity<>(productoResponseRest, HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
					"Error al guardar el registro");
			return new ResponseEntity<>(productoResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductoResponseRest> actualizarProducto(ProductoEntity entity, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductoResponseRest> desactivarProducto(Long id) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductoResponseRest> obtenerProductoPorNombre(String nombre) {
		ProductoResponseRest productoResponseRest= new ProductoResponseRest();
		List<ProductoEntity> productoEntity= new ArrayList<>();
		List<ProductoEntity> listaAuxProductoEntity= new ArrayList<>();
		try {
			productoEntity=productoDao.findByNombreContainingIgnoreCase(nombre);
			if (!(productoEntity.isEmpty())) {
					productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.OK), "Exito");
					productoEntity.stream().forEach(p->{
						byte [] imageDescomprimir=UtilImagen.decompressZLib(p.getImagen());
						p.setImagen(imageDescomprimir);
						listaAuxProductoEntity.add(p);
					});
					productoResponseRest.getProductoResponse().setProductoEntities(listaAuxProductoEntity);
					

			}else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND), "No se encontro el producto");
				productoResponseRest.getProductoResponse().setProductoEntities(productoEntity);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
					"Error al consultar el registro");
			productoResponseRest.getProductoResponse().setProductoEntities(productoEntity);
			return new ResponseEntity<>(productoResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(productoResponseRest,HttpStatus.OK);
	}

}
