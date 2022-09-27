package com.company.inventory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categorias")
public class CategoriaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3577736698367991600L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String nombre;
	
	@Column(length = 100, nullable = true)
	private String descripcion;
}
