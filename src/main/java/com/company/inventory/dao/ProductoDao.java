package com.company.inventory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.inventory.model.ProductoEntity;

public interface ProductoDao extends JpaRepository<ProductoEntity, Long> {

	@Query("select p from ProductoEntity p where p.nombre like %:nombre%")
	List<ProductoEntity>findByNombreLike(@Param("nombre") String nombre);
	
	
	List<ProductoEntity>findByNombreContaining(String nombre);
	
	List<ProductoEntity>findByNombreIgnoreCase(String nombre);


}
