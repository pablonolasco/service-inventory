package com.company.inventory;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceInventoryApplicationTests {
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
	 */

	@Test
	void contextLoads() {

	}

}
