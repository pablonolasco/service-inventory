package com.company.inventory.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productos")
public class ProductoEntity implements Serializable{

	private static final long serialVersionUID = -2320313241212164747L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Long id;
	
	private String nombre;
	
	private BigDecimal precio;
	
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id",referencedColumnName = "id_categoria")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler "})
	private CategoriaEntity categoriaEntity;
	
	@Column(name = "imagen", length = 1000)
	private byte [] imagen;
}
