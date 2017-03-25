package com.sdi.tests.Tests;
import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.sdi.tests.pageobjects.PO_LoginForm;
import com.sdi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class PlantillaSDI2_Tests1617 {

	static WebDriver driver = getDriver(); 
	static String URL = "http://localhost:8280/sdi-42";
	
	public PlantillaSDI2_Tests1617()
	{
	}

	private static WebDriver getDriver() {
		File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();       
		return new FirefoxDriver(ffBinary,firefoxProfile);
	}

	@Before
	public void setUp()
	{
		driver.navigate().to(URL);			
	}
	@After
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}
	@AfterClass
	public static void end()
	{
		//Cerramos el navegador
		driver.quit();
	}

	//PRUEBAS
	//ADMINISTRADOR
	//PR01: Autentificar correctamente al administrador.
	@Test
    public void prueba01() {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
    }
	//PR02: Fallo en la autenticación del administrador por introducir mal el login.
	@Test
    public void prueba02() {
		new PO_LoginForm().rellenaFormulario(driver, "administrador", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div/table/tbody/tr[1]/td[2]/input", 2);
    }
	//PR03: Fallo en la autenticación del administrador por introducir mal la password.
	@Test
    public void prueba03() {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin123");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div/table/tbody/tr[1]/td[2]/input", 2);
    }
//	//PR04: Probar que la base de datos contiene los datos insertados con conexión correcta a la base de datos.
//	@Test
//    public void prueba04() {
//		assertTrue(false);
//    }
//	//PR05: Visualizar correctamente la lista de usuarios normales. 
//	@Test
//    public void prueba05() {
//		assertTrue(false);
//    }
//	//PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de entrar con el usuario que se desactivado.
//	@Test
//    public void prueba06() {
//		assertTrue(false);
//    }
//	//PR07: Cambiar el estado de un usuario a DISABLED a ENABLED. Y Y tratar de entrar con el usuario que se ha activado.
//	@Test
//    public void prueba07() {
//		assertTrue(false);
//    }
//	//PR08: Ordenar por Login
//	@Test
//    public void prueba08() {
//		assertTrue(false);
//    }
//	//PR09: Ordenar por Email
//	@Test
//    public void prueba09() {
//		assertTrue(false);
//    }
//	//PR10: Ordenar por Status
//	@Test
//    public void prueba10() {
//		assertTrue(false);
//    }
//	//PR11: Borrar  una cuenta de usuario normal y datos relacionados.
//	@Test
//    public void prueba11() {
//		assertTrue(false);
//    }
//	//PR12: Crear una cuenta de usuario normal con datos válidos.
//	@Test
//    public void prueba12() {
//		assertTrue(false);
//    }
//	//PR13: Crear una cuenta de usuario normal con login repetido.
//	@Test
//    public void prueba13() {
//		assertTrue(false);
//    }
//	//PR14: Crear una cuenta de usuario normal con Email incorrecto.
//	@Test
//    public void prueba14() {
//		assertTrue(false);
//    }
//	//PR15: Crear una cuenta de usuario normal con Password incorrecta.
//	@Test
//    public void prueba15() {
//		assertTrue(false);
//    }
//	//USUARIO
//	//PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin categoría y que son las que tienen que. Usar paginación navegando por las tres páginas.
//	@Test
//    public void prueba16() {
//		assertTrue(false);
//    }
//	//PR17: Funcionamiento correcto de la ordenación por fecha planeada.
//	@Test
//    public void prueba17() {
//		assertTrue(false);
//    }
//	//PR18: Funcionamiento correcto del filtrado.
//	@Test
//    public void prueba18() {
//		assertTrue(false);
//    }
//	//PR19: Funcionamiento correcto de la ordenación por categoría.
//	@Test
//    public void prueba19() {
//		assertTrue(false);
//    }
//	//PR20: Funcionamiento correcto de la ordenación por fecha planeada.
//	@Test
//    public void prueba20() {
//		assertTrue(false);
//    }
//	//PR21: Comprobar que las tareas que no están en rojo son las de hoy y además las que deben ser.
//	@Test
//    public void prueba21() {
//		assertTrue(false);
//    }
//	//PR22: Comprobar que las tareas retrasadas están en rojo y son las que deben ser.
//	@Test
//    public void prueba22() {
//		assertTrue(false);
//    }
//	//PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que son las que deben ser.
//	@Test
//    public void prueba23() {
//		assertTrue(false);
//    }
//	//PR24: Funcionamiento correcto de la ordenación por día.
//	@Test
//    public void prueba24() {
//		assertTrue(false);
//    }
//	//PR25: Funcionamiento correcto de la ordenación por nombre.
//	@Test
//    public void prueba25() {
//		assertTrue(false);
//    }
//	//PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir a la pagina donde está la tarea terminada y comprobar que se muestra. 
//	@Test
//    public void prueba26() {
//		assertTrue(false);
//    }
//	//PR27: Crear una tarea sin categoría y comprobar que se muestra en la lista Inbox.
//	@Test
//    public void prueba27() {
//		assertTrue(false);
//    }
//	//PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y comprobar que se muestra en la lista Hoy.
//	@Test
//    public void prueba28() {
//		assertTrue(false);
//    }
//	//PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior a Hoy y comprobar que se muestra en la lista Semana.
//	@Test
//    public void prueba29() {
//		assertTrue(false);
//    }
//	//PR30: Editar el nombre, y categoría de una tarea (se le cambia a categoría1) de la lista Inbox y comprobar que las tres pseudolista se refresca correctamente.
//	@Test
//    public void prueba30() {
//		assertTrue(false);
//    }
//	//PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan correctamente.
//	@Test
//    public void prueba31() {
//		assertTrue(false);
//    }
//	//PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las tres pseudolistas.
//	@Test
//    public void prueba32() {
//		assertTrue(false);
//    }
//	//PR33: Salir de sesión desde cuenta de administrador.
	@Test
    public void prueba33() {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		
		WebElement signOut = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[3]/a/span", 2).get(0);
		signOut.click();
		//Vuelve a la pagina index por lo que aparece el field login
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div/table/tbody/tr[1]/td[2]/input", 2);
    }
//	//PR34: Salir de sesión desde cuenta de usuario normal.
	@Test
    public void prueba34() {
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span[2]", 2);
		
		WebElement signOut = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[4]/a/span", 2).get(0);
		signOut.click();
		//Vuelve a la pagina index por lo que aparece el field login
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div/table/tbody/tr[1]/td[2]/input", 2);
    }
//	//PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas vistas)
	@Test
    public void prueba35() throws InterruptedException {
		//Probar cambio de idioma en el index normal
		SeleniumUtils.textoPresentePagina(driver, "Registrar usuario");
		SeleniumUtils.textoPresentePagina(driver, "IDIOMA");
		SeleniumUtils.textoPresentePagina(driver, "Aceptar");
		//Cambio de idioma a ingles
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuIdioma", "form-cabecera:menuEng");
		//Comprobamos que se cambio a ingles
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div/table/tbody/tr[1]/td[2]/input", 2);
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Login:");
		SeleniumUtils.textoPresentePagina(driver, "Password:");
		SeleniumUtils.textoPresentePagina(driver, "Accept");
		//Accedemos como admin para probar el cambio de idioma en dicha vista
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "List Users");
		SeleniumUtils.textoPresentePagina(driver, "Log out");
		SeleniumUtils.textoPresentePagina(driver, "LANGUAGE");
		//Se pueden añadir mas vistas si queremos
    }
//	//PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al idioma por defecto. (Probar algunas vistas)
	@Test
	public void prueba36() throws InterruptedException {
		//Probar cambio de idioma en el index normal
		SeleniumUtils.textoPresentePagina(driver, "Registrar usuario");
		SeleniumUtils.textoPresentePagina(driver, "IDIOMA");
		SeleniumUtils.textoPresentePagina(driver, "Aceptar");
		//Cambio de idioma a ingles
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuIdioma", "form-cabecera:menuEng");
		//Comprobamos que se cambio a ingles
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div/table/tbody/tr[1]/td[2]/input", 2);
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Login:");
		SeleniumUtils.textoPresentePagina(driver, "Password:");
		SeleniumUtils.textoPresentePagina(driver, "Accept");
		//Volvemos a ponernos al idioma por defecto (Español)
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuIdioma", "form-cabecera:menuSpa");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Registrar usuario");
		SeleniumUtils.textoPresentePagina(driver, "IDIOMA");
		SeleniumUtils.textoPresentePagina(driver, "Aceptar");
		//Accedemos como admin para probar el cambio de idioma en dicha vista
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Listar Usuarios");
		SeleniumUtils.textoPresentePagina(driver, "Cerrar");
		SeleniumUtils.textoPresentePagina(driver, "IDIOMA");
		//Se pueden añadir mas vistas si queremos
	}
//	//PR37: Intento de acceso a un  URL privado de administrador con un usuario autenticado como usuario normal.
//	@Test
//    public void prueba37() {
//		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
//		driver.navigate().to("http://localhost:8280/sdi-42/restricted/principalAdmin.xhtml");
//		
//    }
//	//PR38: Intento de acceso a un  URL privado de usuario normal con un usuario no autenticado.
//	@Test
//    public void prueba38() {
//		assertTrue(false);
//    }

	



	
	


    
}