package com.company.inventory.response;

import java.util.ArrayList;
import java.util.List;

import com.company.inventory.model.CategoriaEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CategoriaResponse {

	public List<CategoriaEntity>categoriaEntities;
	
	//public CategoriaEntity categoriaEntity;
}
