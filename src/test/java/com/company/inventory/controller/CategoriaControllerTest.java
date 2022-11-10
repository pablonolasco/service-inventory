/**
 * 
 */
package com.company.inventory.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.inventory.model.CategoriaEntity;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.CategoriaService;

/**
 * @author pablo
 *
 */
class CategoriaControllerTest {

	@InjectMocks
	CategoriaController categoriaController;
	
	@Mock
	private CategoriaService categoriaService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void saveTest() {
		// configuracion de mockito
		MockHttpServletRequest request= new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		// entidad para realizar prueba
		CategoriaEntity categoriaEntity= new CategoriaEntity();
		categoriaEntity.setId(1L);
		categoriaEntity.setNombre("refrescos");
		categoriaEntity.setDescripcion("refrescos descripcion");
		
		// cuando intercepte una prueba de la clase category
		when(categoriaService.crearCategoria(any(CategoriaEntity.class))).thenReturn(
				new ResponseEntity<CategoryResponseRest>(HttpStatus.OK)
				);
		
		// realiza la peticion
		ResponseEntity<CategoryResponseRest>responseEntity=categoriaController.crearCategoria(categoriaEntity);
		
		// valida el resultado
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
	}
	
	@Test
	void actualizarTest() {
		// configuracion de mockito
		MockHttpServletRequest request= new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		// entidad para realizar prueba
		CategoriaEntity categoriaEntity= new CategoriaEntity();
		//categoriaEntity.setId(1L);
		categoriaEntity.setNombre("refrescos");
		categoriaEntity.setDescripcion("refrescos descripcion");
		
		// cuando intercepte una prueba de la clase category
		when(categoriaService.actualizar(any(CategoriaEntity.class),any(Long.class))).thenReturn(
				new ResponseEntity<CategoryResponseRest>(HttpStatus.OK)
				);
		
		// realiza la peticion
		ResponseEntity<CategoryResponseRest>responseEntity=categoriaController.actualizarCategoria(categoriaEntity,2L);
		
		// valida el resultado
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
	}
	
	@Test
	void buscarTest() {
		// configuracion de mockito
		MockHttpServletRequest request= new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
			
		// cuando intercepte una prueba de la clase category
		when(categoriaService.obtenerPorId(any(Long.class))).thenReturn(
				new ResponseEntity<CategoryResponseRest>(HttpStatus.OK)
				);
		
		// realiza la peticion
		ResponseEntity<CategoryResponseRest>responseEntity=categoriaController.buscarCategoria(2L);
		
		// valida el resultado
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
	}
	
	@Test
	void obtenerTest() {
		// configuracion de mockito
		MockHttpServletRequest request= new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
			
		// cuando intercepte una prueba de la clase category
		when(categoriaService.buscar()).thenReturn(
				new ResponseEntity<CategoryResponseRest>(HttpStatus.OK)
				);
		
		// realiza la peticion
		ResponseEntity<CategoryResponseRest>responseEntity=categoriaController.listarCategorias();
		
		// valida el resultado
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void eliminarTest() {
		// configuracion de mockito
		MockHttpServletRequest request= new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
			
		// cuando intercepte una prueba de la clase category
		when(categoriaService.desactivarCategoria(any(Long.class))).thenReturn(
				new ResponseEntity<CategoryResponseRest>(HttpStatus.OK)
				);
		
		// realiza la peticion
		ResponseEntity<CategoryResponseRest>responseEntity=categoriaController.eliminar(2L);
		
		// valida el resultado
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
	}

}
