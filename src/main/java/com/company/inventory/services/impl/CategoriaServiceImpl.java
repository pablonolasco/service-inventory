package com.company.inventory.services.impl;

import java.util.List;

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
		CategoryResponseRest categoryResponseRest= new CategoryResponseRest();
		try {
			List<CategoriaEntity>categoriaEntities=(List<CategoriaEntity>) categoryDao.findAll();
			categoryResponseRest.getCategoriaResponse().setCategoriaEntities(categoriaEntities);
			categoryResponseRest.setMetadata("Respuesta", "200", "Respuesta Exitosa");
		} catch (Exception e) {
			categoryResponseRest.setMetadata("Respuesta", HttpStatus.CONFLICT.toString(), "Fallo la peticion");
			e.printStackTrace();
			return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.CONFLICT);

		}
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.OK);
	}

}
