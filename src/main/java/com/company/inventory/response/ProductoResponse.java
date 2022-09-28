package com.company.inventory.response;

import java.util.List;

import com.company.inventory.model.ProductoEntity;

import lombok.Data;

@Data
public class ProductoResponse {

	public List<ProductoEntity>productos;
}
