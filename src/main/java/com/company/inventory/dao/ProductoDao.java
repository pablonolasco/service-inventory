package com.company.inventory.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.inventory.model.ProductoEntity;

public interface ProductoDao extends JpaRepository<ProductoEntity, Long> {

}
