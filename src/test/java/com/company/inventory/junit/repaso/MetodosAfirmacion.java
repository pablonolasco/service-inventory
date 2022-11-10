package com.company.inventory.junit.repaso;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @Test indica que es un metodo de prueba
 * @BeforeAll se ejecuta siempre una sola vez, se usa para conexiones
 * @BeforeEach se ejecuta antes de iniciar las pruebas
 * @AfterAll se ejecuta una sola vez cuando finalice la prueba
 * @AfterEach se ejecuta despues de terminar las pruebas 
 * @Disabled no se ejecuta el metodo en las pruebas
 * Metodos de afirmacion
 *            assertEquals(2,sumar(2,2)); si es igual retorna true
 *            assertNotEquals(1, 2); assertTrue(false); assertFalse(false);
 *            assertArrayEquals(expectativaArray, actualArray,"mensaje
 *            optional"); assertTimeout(duracion, funcion a ejecutar, mensaje
 *            opcional); assertNotNull(getClass(),"mensaje optional");
 * https://junit.org/junit5/docs/current/user-guide/
 */

public class MetodosAfirmacion {

	Calculadora calcula;
	private static final Logger log = LoggerFactory.getLogger(MetodosAfirmacion.class);

	@BeforeAll
	public static void primero() {
		log.info("primero");
	}
	
	@BeforeEach
	public void primeroCadaPrueba() {
		log.info("primero por cada prueba");
		calcula= new Calculadora();
	}
	
	@Test
	@DisplayName("descripcion de prueba")
	public void testEquals() {
		assertEquals(1, calcula.suma(1, 0));
	}
	
	@Test
	public void testNotEquals() {
		assertNotEquals(1, 2);
	}
	
	@Test
	public void testTrueFalse() {
		
		boolean expresion1=(1==1);
		boolean expresion2=(2==4);
		assertTrue(expresion1);
		assertFalse(expresion2);
	}
	
	@Test
	@Disabled("se deshabilita por prueba")
	public void testArray() {
		String []array1= {"a","b","c","d"};
		String []array2= {"a","b","c","d"};
		String []array3= {"a","b"};
		
		assertArrayEquals(array1,array3);
	}
	
	@AfterAll
	public static void ultimo() {
		log.info("ultimo");
	}
	
	@AfterEach
	public void ultimoCadaPrueba() {
		log.info("ultimo por cada prueba");
	}
	
}
