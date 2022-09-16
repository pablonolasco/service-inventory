package com.company.inventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.CategoriaDao;
import com.company.inventory.model.CategoriaEntity;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	private final CategoriaDao categoryDao;

	public CategoriaServiceImpl(CategoriaDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> buscar() {
		CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
		try {
			List<CategoriaEntity> categoriaEntities = (List<CategoriaEntity>) categoryDao.findAll();
			categoryResponseRest.getCategoriaResponse().setCategoriaEntities(categoriaEntities);
			categoryResponseRest.setMetadata("Respuesta", "200", "Respuesta Exitosa");
		} catch (Exception e) {
			categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CONFLICT.value()),
					"Fallo la peticion");
			e.printStackTrace();
			return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> obtenerPorId(Long id) {
		CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
		List<CategoriaEntity> categoriaEntitie = new ArrayList<>();

		try {
			Optional<CategoriaEntity> categoriaEntity = categoryDao.findById(id);
			if (categoriaEntity.isPresent()) {
				categoriaEntitie.add(categoriaEntity.get());
				categoryResponseRest.getCategoriaResponse().setCategoriaEntities(categoriaEntitie);
				categoryResponseRest.setMetadata("Respuesta", "200", "Respuesta Exitosa");
			} else {
				categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND.value()),
						"Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CONFLICT.value()),
					"Fallo la peticion");
			e.printStackTrace();
			return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> crearCategoria(CategoriaEntity categoriaEntity) {
		CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
		List<CategoriaEntity> categorias = new ArrayList<>();
		try {
			CategoriaEntity categoria = categoryDao.save(categoriaEntity);

			if (categoria != null) {

				categorias.add(categoria);
				categoryResponseRest.getCategoriaResponse().setCategoriaEntities(categorias);
				categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CREATED.value()),
						"Categoria creada");
			}else {
				categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.BAD_REQUEST.value()),
						"Categoria no fue creada");
				return new ResponseEntity<>(categoryResponseRest,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CONFLICT.value()),
					"Fallo la peticion al guardar la categoria");
		}
		return new ResponseEntity<>(categoryResponseRest,HttpStatus.CREATED);
	}
	
	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest>actualizar(CategoriaEntity categoriaEntity, Long id ){
		
		CategoryResponseRest categoryResponseRest= new CategoryResponseRest();
		List<CategoriaEntity>categoriaEntities= new ArrayList<>();
		try {
			CategoriaEntity categoria=categoryDao.findById(id).orElse(null);
			if (categoria != null) {
				categoria.setDescripcion(categoriaEntity.getDescripcion());
				categoria.setNombre(categoriaEntity.getNombre());
				CategoriaEntity categoriaEntityActualizada=categoryDao.save(categoria);
				categoriaEntities.add(categoriaEntityActualizada);
				categoryResponseRest.getCategoriaResponse().setCategoriaEntities(categoriaEntities);
				categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.OK.value()),
						"Categoria actualizada");

			}else {
				categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND.value()),
						"Categoria no fue actualizada");
				return new ResponseEntity<>(categoryResponseRest,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CONFLICT.value()),
					"Fallo la peticion al actualizar la categoria");
		}
		
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<CategoryResponseRest>desactivarCategoria(Long id){
		CategoryResponseRest categoryResponseRest= new CategoryResponseRest();
		try {
			CategoriaEntity categoriaEntity= categoryDao.findById(id).orElse(null);
			if (categoriaEntity != null) {
				
			}else {
				categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.NOT_FOUND), "No se encontro la categoria");
				return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			categoryResponseRest.setMetadata("Respuesta", String.valueOf(HttpStatus.CONFLICT.value()), "Fallo la peticion");
		}
		
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.OK);
	}
}
