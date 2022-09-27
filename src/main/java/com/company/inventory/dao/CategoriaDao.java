package com.company.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.inventory.model.CategoriaEntity;

public interface CategoriaDao extends CrudRepository<CategoriaEntity, Long> {

}
