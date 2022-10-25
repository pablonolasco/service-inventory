package com.company.inventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

	public ProductoServiceImpl(ProductoDao productoDao, CategoriaDao categoriaDao) {
		this.productoDao = productoDao;
		this.categoriaDao = categoriaDao;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductoResponseRest> obtenerProductos() {
		ProductoResponseRest productoResponseRest = new ProductoResponseRest();
		List<ProductoEntity> productoEntities = new ArrayList<>();
		try {
			Optional<List<ProductoEntity>> productos = Optional.of(productoDao.findAll());
			if (!(productos.get().isEmpty())) {
				productos.get().stream().forEach(p -> {

					byte[] imageDescomprimir = UtilImagen.decompressZLib(p.getImagen());
					p.setImagen(imageDescomprimir);
					productoEntities.add(p);
				});

				productoResponseRest.setMetadata("Respuesta", "200", "Exito");
				productoResponseRest.getProductoResponse().setProductos(productoEntities);

			} else {

				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND),
						"No se encontraron productos");
				productoResponseRest.getProductoResponse().setProductos(productoEntities);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
					"Error al hacer la busqueda");
			productoResponseRest.getProductoResponse().setProductos(productoEntities);
			return new ResponseEntity<>(productoResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
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
				productoResponseRest.getProductoResponse().setProductos(productoEntities);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);
			} else {
				byte[] imageDescomprimir = UtilImagen.decompressZLib(producto.get().getImagen());
				producto.get().setImagen(imageDescomprimir);
				productoEntities.add(producto.get());
				productoResponseRest.setMetadata("Respuesta", "200", "Exito");
				productoResponseRest.getProductoResponse().setProductos(productoEntities);
			}
		} catch (Exception e) {
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
					"Error al hacer la busqueda");
			productoResponseRest.getProductoResponse().setProductos(productoEntities);
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
				productoResponseRest.getProductoResponse().setProductos(listProductoEntities);
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
	public ResponseEntity<ProductoResponseRest> actualizarProducto(ProductoEntity producto, Long id) {
		ProductoResponseRest productoResponseRest = new ProductoResponseRest();
		List<ProductoEntity> productoEntities = new ArrayList<>();
		try {
			Optional<ProductoEntity> productoEntity = productoDao.findById(producto.getId());
			Optional<CategoriaEntity> categoriaEntity = categoriaDao.findById(id);
			if (!categoriaEntity.isPresent()) {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND.value()),
						"Categoria no encontrada");
				productoResponseRest.getProductoResponse().setProductos(productoEntities);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);

			}

			if (productoEntity.isPresent()) {

				byte[] comprimir = UtilImagen.compressZLib(producto.getImagen());
				productoEntity.get().setCantidad(producto.getCantidad());
				productoEntity.get().setImagen(comprimir);
				productoEntity.get().setNombre(producto.getNombre());
				productoEntity.get().setPrecio(producto.getPrecio());
				productoEntity.get().setCategoriaEntity(categoriaEntity.get());
				ProductoEntity productoEntityActualizado = productoDao.save(productoEntity.get());
				if (productoEntityActualizado != null) {
					productoEntities.add(productoEntityActualizado);
					productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.OK), "Exito");
					productoResponseRest.getProductoResponse().setProductos(productoEntities);
				} else {
					productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.BAD_REQUEST.value()),
							"Producto no actualizado");
					return new ResponseEntity<>(productoResponseRest, HttpStatus.BAD_REQUEST);
				}

			} else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND.value()),
						"Producto no encontrado");
				productoResponseRest.getProductoResponse().setProductos(productoEntities);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.info("error: {}", e.getMessage());
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
					"Error al actualizar el producto");
			productoResponseRest.getProductoResponse().setProductos(productoEntities);
			return new ResponseEntity<>(productoResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductoResponseRest> desactivarProducto(Long id) {
		ProductoResponseRest productoResponseRest = new ProductoResponseRest();
		List<ProductoEntity> listProductoEntities = new ArrayList<>();
		try {
			ProductoEntity productoEntity = productoDao.findById(id).orElse(null);
			if (productoEntity != null) {
				productoDao.delete(productoEntity);
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NO_CONTENT),
						"Se desactivo el producto");
				productoResponseRest.getProductoResponse().setProductos(listProductoEntities);
			} else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND),
						"No existe el producto");
				productoResponseRest.getProductoResponse().setProductos(listProductoEntities);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
			}

		} catch (Exception e) {
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
					"Error al desactivar el producto");
			productoResponseRest.getProductoResponse().setProductos(listProductoEntities);
			return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
		}
		return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductoResponseRest> obtenerProductoPorNombre(String nombre) {
		ProductoResponseRest productoResponseRest = new ProductoResponseRest();
		List<ProductoEntity> productoEntity = new ArrayList<>();
		List<ProductoEntity> listaAuxProductoEntity = new ArrayList<>();
		try {
			productoEntity = productoDao.findByNombreContainingIgnoreCase(nombre);
			if (!(productoEntity.isEmpty())) {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.OK), "Exito");
				productoEntity.stream().forEach(p -> {
					byte[] imageDescomprimir = UtilImagen.decompressZLib(p.getImagen());
					p.setImagen(imageDescomprimir);
					listaAuxProductoEntity.add(p);
				});
				productoResponseRest.getProductoResponse().setProductos(listaAuxProductoEntity);

			} else {
				productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND),
						"No se encontro el producto");
				productoResponseRest.getProductoResponse().setProductos(productoEntity);
				return new ResponseEntity<>(productoResponseRest, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			productoResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
					"Error al consultar el registro");
			productoResponseRest.getProductoResponse().setProductos(productoEntity);
			return new ResponseEntity<>(productoResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(productoResponseRest, HttpStatus.OK);
	}

}
