package com.company.inventory.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {
	
	private ArrayList<HashMap<String, String>>metadata= new ArrayList<>();

	public ArrayList<HashMap<String, String>> getMetadata() {
		return metadata;
	}

	public void setMetadata(String tipo, String codigo, String mensaje) {
		HashMap<String, String>map= new HashMap<>();
		map.put("tipo", tipo);
		map.put("codigo", codigo);
		map.put("mensaje", mensaje);
		map.put("fecha", LocalDateTime.now().toString());
		metadata.add(map);
	}
	
	

}
