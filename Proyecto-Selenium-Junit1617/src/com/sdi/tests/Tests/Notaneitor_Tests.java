package com.sdi.tests.Tests;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sdi.tests.pageobjects.PO_AltaForm;

import com.sdi.tests.utils.SeleniumUtils;



public class Notaneitor_Tests {

	WebDriver driver; // = new FirefoxDriver();
	List<WebElement> elementos = null;
	public Notaneitor_Tests()
	{
		//driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

	}

	@Before
	public void run()
	{
		//Vamos a la pagina principal
		driver = new FirefoxDriver();
		driver.get("http://localhost:8180/Notaneitorv2_0_SOLUCION_pruebas/");			
	}
	@After
	public void end()
	{
		//Cerramos el navegador
		driver.quit();
	}



	public void testAltaParametros(String nombreform, String nombre, String apellidos, String iduser, String email) {
		//Pinchamos la opción de menu Alta Alumno
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:misubmenu1", "form-cabecera:linkalta");

		//Esperamos a que se cargue la pagina de alta de alumno	
		SeleniumUtils.EsperaCargaPagina(driver, "text", nombreform, 10); 

		//Vamos a rellenar el formulario
		new PO_AltaForm().rellenaFormulario(driver, nombre, apellidos, iduser, email);

		//Esperamos a que se cargue la pagina de listado
		//concretamente la tabla "tablalistado"
		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablalistado", 10); 

		//Comprobamos que aparezca el elemento insertado en el listado
		SeleniumUtils.textoPresentePagina(driver, email);
	}
	@Test
	public void testAlta() {

		testAltaParametros("Alta de un alumno", "manolo1", "Suarez1", "idmano1", "manolo1@correo.com");
	}

	@Test
	public void testListado() {
		//Pinchamos la opción de menu Listado
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:misubmenu1", "form-cabecera:linklistado");

		//Esperamos a que se cargue la pagina de listado de alumnos	
		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablalistado", 10); 
	}

	//Buscar un enlace de baja o edicion correspondiente a una fila
	//que contiene los datos de un alumno recien introducido
	////td[contains(text(), 'idpepe@correo.com')]/following-sibling::*/a[contains(@id, 'bajalink')]")
	//td[contains(text(), 'idpepe@correo.com')]/following-sibling::*/a[contains(@id, 'editlink')]")

	@Test
	public void testBaja() throws InterruptedException {
		//Insertamos un alumno de prueba y llegamos hasta ver el listado
		//con el alumno 
		testAltaParametros("Alta de un alumno", "manolo2", "Suarez2", "idmano2", "manolo2@correo.com");		

		//Vamos a borrar el alumno insertado
		//Pinchamos el enlace de baja de manolo
		By enlace = By.xpath("//td[contains(text(), 'manolo2@correo.com')]/following-sibling::*/a[contains(@id, 'bajalink')]");
		driver.findElement(enlace).click();

		//Comprobamos que ahora ya no está el alumno
		//textoNoPresentePagina(driver, "manolo2@correo.com");
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "manolo2@correo.com", 5);

	}

	@Test
	public void testEdit() throws InterruptedException {
		//Insertamos un alumno de prueba y llegamos hasta ver el listado
		//con el alumno 
		testAltaParametros("Alta de un alumno", "manolo3", "Suarez3", "idmano3", "manolo3@correo.com");		

		//Vamos a editar el alumno insertado
		//Pinchamos el enlace de edicion de manolo
		By enlace = By.xpath("//td[contains(text(), 'manolo3@correo.com')]/following-sibling::*/a[contains(@id, 'editlink')]");
		driver.findElement(enlace).click();

		//Esperamos a que se cargue la pagina de edición de alumno	
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Edición de un alumno", 10); 

		//Vamos a rellenar el formulario
		new PO_AltaForm().rellenaFormulario(driver, "manolo3", "Suarez3 modif", "idmano3", "manolomodif@correo.com");

		//Esperamos a que se cargue la pagina de listado
		//concretamente la tabla "tablalistado"
		SeleniumUtils.EsperaCargaPagina(driver, "id", "tablalistado", 10); 

		//Comprobamos que aparezca el elemento modificado en el listado
		SeleniumUtils.textoPresentePagina(driver, "Suarez3 modif");
	}	

}