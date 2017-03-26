package com.sdi.tests.Tests;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
import com.sdi.infrastructure.Factories;
import com.sdi.tests.pageobjects.PO_LoginForm;
import com.sdi.tests.pageobjects.PO_RegisterForm;
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
    public void prueba01() throws InterruptedException {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		Thread.sleep(1000);
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0).getText().equals("Listar Usuarios");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[2]/a/span", 2);
		SeleniumUtils.textoPresentePagina(driver, "Bienvenido a tu Gestor de Usuarios, admin!");
		SeleniumUtils.textoPresentePagina(driver, "Log-in correcto");
    }
	//PR02: Fallo en la autenticación del administrador por introducir mal el login.
	@Test
    public void prueba02() throws InterruptedException {
		new PO_LoginForm().rellenaFormulario(driver, "administrador", "admin1234");
		Thread.sleep(1000);
		SeleniumUtils.textoNoPresentePagina(driver, "Bienvenido a tu Gestor de Usuarios, admin!");
		SeleniumUtils.textoPresentePagina(driver, "Credenciales inválidas");
    }
	//PR03: Fallo en la autenticación del administrador por introducir mal la password.
	@Test
    public void prueba03() throws InterruptedException {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin123");
		Thread.sleep(1000);
		SeleniumUtils.textoNoPresentePagina(driver, "Bienvenido a tu Gestor de Usuarios, admin!");
		SeleniumUtils.textoPresentePagina(driver, "Credenciales inválidas");
    }
//	//PR04: Probar que la base de datos contiene los datos insertados con conexión correcta a la base de datos.
//	@Test
//    public void prueba04() throws InterruptedException {
//		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
//		Thread.sleep(1000);
//		WebElement resetDB = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[2]/a/span", 2).get(0);
//		resetDB.click();
//		
//    }
	//PR05: Visualizar correctamente la lista de usuarios normales. 
	@Test
    public void prueba05() throws InterruptedException {
		//Se inicia la sesion en admin
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Accedemos al listado de usuarios
		WebElement listaUsers = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers.click();
		Thread.sleep(2000);
		
		try {
			List<User> users = Factories.services.getUserService().findAll();
			List<User> aux = new ArrayList<User>();
			for(User user : users){
				if(user.getIsAdmin())
					aux.add(user);
			}
			//Ahora borramos de la lista de usuarios los que sean admin
			users.removeAll(aux);
			//Recorremos la lista de users, para comprobar que están presentes en la vista actual
			for(User user : users){
				//Miramos si el nombre de usuario está presente en la vista actual
				String nombre = user.getLogin();
				String email = user.getEmail();
				String id = String.valueOf(user.getId());
				SeleniumUtils.textoPresentePagina(driver, id);
				SeleniumUtils.textoPresentePagina(driver, email);
				SeleniumUtils.textoPresentePagina(driver, nombre);
			}
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	//PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de entrar con el usuario que se desactivado.
	@Test
    public void prueba06() throws InterruptedException {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		WebElement listaUsers = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers.click();
		//Ahora que visualizamos la lista de usuarios deshabilitamos a john
		WebElement deshabJohn = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[1]/form[2]/div/div[3]/table/tbody/tr[2]/td[5]/a", 3).get(0);
		deshabJohn.click();
		Thread.sleep(2000);
		SeleniumUtils.textoPresentePagina(driver, "Se ha deshabilitado un usuario.");
		//Cerramos sesion con el admin e intentamos entrar con John
		WebElement signOut = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form/div/ul/li[3]/a/span", 2).get(0);
		signOut.click();
		Thread.sleep(1000);
		new PO_LoginForm().rellenaFormulario(driver, "john", "john123");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "GTD Gestor de tareas");
		SeleniumUtils.textoPresentePagina(driver, "Login:");
		
		//Una vez probado volvemos a habilitarlo para que no haya problemas posteriormente
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		Thread.sleep(1000);
		WebElement listaUsers2 = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers2.click();
		WebElement habJohn = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[1]/form[2]/div/div[3]/table/tbody/tr[2]/td[5]/a", 3).get(0);
		habJohn.click();
		Thread.sleep(1000);
		SeleniumUtils.textoNoPresentePagina(driver, "DISABLED");
    }
	//PR07: Cambiar el estado de un usuario a DISABLED a ENABLED. Y Y tratar de entrar con el usuario que se ha activado.
	@Test
    public void prueba07() throws InterruptedException {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		WebElement listaUsers = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers.click();
		Thread.sleep(1000);
		//Ahora que visualizamos la lista de usuarios deshabilitamos a john
		WebElement deshabJohn = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[1]/form[2]/div/div[3]/table/tbody/tr[2]/td[5]/a", 3).get(0);
		deshabJohn.click();
		Thread.sleep(1000);
		//Salimos de sesion con el admin
		WebElement signOut = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form/div/ul/li[3]/a/span", 2).get(0);
		signOut.click();
		Thread.sleep(1000);
		//Una vez hecho volvemos a habilitarlo para realizar la posterior prueba
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		Thread.sleep(1000);
		WebElement listaUsers2 = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers2.click();
		WebElement habJohn = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[1]/form[2]/div/div[3]/table/tbody/tr[2]/td[5]/a", 3).get(0);
		habJohn.click();
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Se ha habilitado un usuario.");
		//Ahora cerramos sesion y la iniciamos como john, que al estar habilitado deberia dejarnos
		WebElement signOut2 = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form/div/ul/li[3]/a/span", 2).get(0);
		signOut2.click();
		Thread.sleep(1000);
		//Entramos como John
		new PO_LoginForm().rellenaFormulario(driver, "john", "john123");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Bienvenido a tu Gestor de Tareas, john!");
    }
	//PR08: Ordenar por Login
	@Test
	public void prueba08() throws InterruptedException {
		//Accedemos como admin
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		WebElement listaUsers = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers.click();
		Thread.sleep(1000);
		//Comprobamos que existe el boton de login para poder clickarlo
		WebElement ordenUser = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/thead/tr/th[3]/span[1]", 2).get(0);
		ordenUser.click();
		//Creo lista de los logins
		try {
			List<User> users = Factories.services.getUserService().findAll();
			List<String> aux = new ArrayList<String>();
			for(User user : users){
				aux.add(user.getLogin());
			}
			java.util.Collections.sort(aux);
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/tbody/tr[1]/td[3]", 2).get(0).getText().equals(aux.get(0));
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/tbody/tr[8]/td[3]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//PR09: Ordenar por Email
	@Test
	public void prueba09() throws InterruptedException {
		//Accedemos como admin
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		WebElement listaUsers = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers.click();
		Thread.sleep(1000);
		//Comprobamos que existe el boton de login para poder clickarlo
		WebElement ordenUser = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/thead/tr/th[3]/span[1]", 2).get(0);
		ordenUser.click();
		//Creo lista de los logins
		try {
			List<User> users = Factories.services.getUserService().findAll();
			List<String> aux = new ArrayList<String>();
			for(User user : users){
				aux.add(user.getEmail());
			}
			java.util.Collections.sort(aux);
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/tbody/tr[1]/td[2]", 2).get(0).getText().equals(aux.get(0));
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/tbody/tr[8]/td[2]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR10: Ordenar por Status
	@Test
	public void prueba10() throws InterruptedException {
		//Accedemos como admin
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		WebElement listaUsers = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers.click();
		Thread.sleep(1000);
		//Comprobamos que existe el boton de login para poder clickarlo
		WebElement ordenUser = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/thead/tr/th[3]/span[1]", 2).get(0);
		ordenUser.click();
		//Creo lista de los logins
		try {
			List<User> users = Factories.services.getUserService().findAll();
			List<UserStatus> aux = new ArrayList<UserStatus>();
			for(User user : users){
				aux.add(user.getStatus());
			}
			java.util.Collections.sort(aux);
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/tbody/tr[1]/td[4]", 2).get(0).getText().equals(aux.get(0));
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[3]/table/tbody/tr[8]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR11: Borrar  una cuenta de usuario normal y datos relacionados.
	@Test
    public void prueba11() throws InterruptedException {
		new PO_LoginForm().rellenaFormulario(driver, "admin", "admin1234");
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2);
		//Esperamos a que se cargue la pagina de admin y clickamos en listar usuarios
		WebElement listaUsers = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		listaUsers.click();
		//Ahora que mostramos los users probamos a eliminar el user hola
		WebElement deleteUser = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[4]/td[6]/a", 2).get(0);
		deleteUser.click();
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Se ha borrado el usuario.");
		SeleniumUtils.textoNoPresentePagina(driver, "hola@hola.com");
		SeleniumUtils.textoNoPresentePagina(driver, "hola");
		SeleniumUtils.textoNoPresentePagina(driver, "15");
		//Salimos de sesion de admin
		WebElement signOut = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[2]/a/span", 2).get(0);
		signOut.click();
		Thread.sleep(1000);
		//Intentamos loggearnos como hola pero no podemos
		new PO_LoginForm().rellenaFormulario(driver, "hola", "hola1234");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "GTD Gestor de tareas");
		SeleniumUtils.textoPresentePagina(driver, "Login:");
		//Faltaria mirarlo en la base de datos si fuese necesario
    }
	//PR12: Crear una cuenta de usuario normal con datos válidos.
	@Test
    public void prueba12() throws InterruptedException {
		//Accedemos a la pagina de alta de usuario
		WebElement registro = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		registro.click();
		Thread.sleep(1000);
		//Comprobamos que accedimos bien al alta de usuario
		SeleniumUtils.textoPresentePagina(driver, "Alta de un alumno");
		SeleniumUtils.textoPresentePagina(driver, "LOGIN");
		SeleniumUtils.textoPresentePagina(driver, "CORREO");
		SeleniumUtils.textoPresentePagina(driver, "CONTRASEÑA");
		SeleniumUtils.textoPresentePagina(driver, "REPITA");
		//Rellenamos los campos correctamente para el alta de un usuario
		new PO_RegisterForm().rellenaFormulario(driver, "PruebaAlta", "pruebaA@mail.com", "prueba12", "prueba12");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Te has registrado satisfactoriamente.");
		//Accedemos con el usuario que acabamos de crear para comprobar que de verdad lo creamos
		new PO_LoginForm().rellenaFormulario(driver, "PruebaAlta", "prueba12");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Bienvenido a tu Gestor de Tareas, PruebaAlta!");
		
    }
	//PR13: Crear una cuenta de usuario normal con login repetido.
	@Test
	public void prueba13() throws InterruptedException {
		//Accedemos a la pagina de alta de usuario
		WebElement registro = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		registro.click();
		Thread.sleep(1000);
		//Comprobamos que accedimos bien al alta de usuario
		SeleniumUtils.textoPresentePagina(driver, "Alta de un alumno");
		SeleniumUtils.textoPresentePagina(driver, "LOGIN");
		SeleniumUtils.textoPresentePagina(driver, "CORREO");
		SeleniumUtils.textoPresentePagina(driver, "CONTRASEÑA");
		SeleniumUtils.textoPresentePagina(driver, "REPITA");
		//Rellenamos los campos correctamente para el alta de un usuario
		new PO_RegisterForm().rellenaFormulario(driver, "mary", "pruebaB@mail.com", "prueba123", "prueba123");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Ya existe el login escogido, inténtelo de nuevo.");
		//No se creo el alumno porque mary ya existe
		SeleniumUtils.textoPresentePagina(driver, "Alta de un alumno");
		//Habría que mostrar un mensaje y probarlo ahora
	}
	//PR14: Crear una cuenta de usuario normal con Email incorrecto.
	@Test
	public void prueba14() throws InterruptedException {
		//Accedemos a la pagina de alta de usuario
		WebElement registro = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		registro.click();
		Thread.sleep(1000);
		//Rellenamos los campos correctamente para el alta de un usuario
		new PO_RegisterForm().rellenaFormulario(driver, "PruebaAlta2", "pruebaBmail.com", "prueba123", "prueba123");
		Thread.sleep(1000);
		//No se creo el alumno porque el mail es incorrecto
		SeleniumUtils.textoPresentePagina(driver, "Alta de un alumno");
		SeleniumUtils.textoPresentePagina(driver, "El campo CORREO E. presenta formato inválido (usuario@servidor.dominio)");
	}
	//PR15: Crear una cuenta de usuario normal con Password incorrecta.
	@Test
	public void prueba15() throws InterruptedException {
		//Accedemos a la pagina de alta de usuario
		WebElement registro = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span", 2).get(0);
		registro.click();
		Thread.sleep(1000);
		//Rellenamos los campos correctamente para el alta de un usuario
		new PO_RegisterForm().rellenaFormulario(driver, "PruebaAlta2", "pruebaB@mail.com", "prueba", "prueba");
		Thread.sleep(1000);
		//No se creo el alumno porque el mail es incorrecto
		SeleniumUtils.textoPresentePagina(driver, "Alta de un alumno");
		SeleniumUtils.textoPresentePagina(driver, "La contraseña ha de tener minimo 8 caracteres conteniendo letras y números");
		SeleniumUtils.textoPresentePagina(driver, "Las contraseñas han de ser iguales, inténtelo de nuevo.");
	}
	//USUARIO
	//PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin categoría y que son las que tienen que. Usar paginación navegando por las tres páginas.
	@Test
    public void prueba16() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de inbox
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listInbox");
		Thread.sleep(1000);
		SeleniumUtils.textoNoPresentePagina(driver, "Categoria");
		//Buscamos las tareas de inbox del usuario
		List<Task> tareasInbox;
		try {
			tareasInbox = Factories.services.getTaskService().findInboxTasksByUserId((long) 1);
			List<Task> aux1 = tareasInbox.subList(0, 7);
			List<Task> aux2 = tareasInbox.subList(8, tareasInbox.size()-1);
			for(Task task : aux1){
				String idTask = String.valueOf(task.getId());
				SeleniumUtils.textoPresentePagina(driver, idTask);
			}
//			Thread.sleep(1000);
			WebElement pasoPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[4]/span", 2).get(0);
			pasoPagina.click();
			Thread.sleep(1000);
			for(Task task : aux2){
				String idTask = String.valueOf(task.getId());
				SeleniumUtils.textoPresentePagina(driver, idTask);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	//PR17: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba17() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de week
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listInbox");
		Thread.sleep(1000);
		List<Task> tareasInbox;
		try {
			tareasInbox = Factories.services.getTaskService().findInboxTasksByUserId((long) 1);
			List<Date> aux = new ArrayList<Date>();
			for(Task tarea : tareasInbox){
				aux.add(tarea.getPlanned());
			}
			java.util.Collections.sort(aux);
			//Comprobamos que la primera fecha de las tareas visualizadas sea la primera fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[4]", 2).get(0).getText().equals(aux.get(0));
//			Thread.sleep(1000);
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que la ultima fecha de las tareas visualizadas sea la ultima fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//PR18: Funcionamiento correcto del filtrado.
	@Test
	public void prueba18() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de week
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listInbox");
		Thread.sleep(1000);
		//Rellenamos el campo de filtrado con lo que queramos
		WebElement filtrado = driver.findElement(By.id("form-principal:tasksTable:j_idt17:filter"));
		filtrado.click();
		filtrado.clear();
		filtrado.sendKeys("wakakaka");
		Thread.sleep(1000);
		//Miramos que efectivamente aparezcan en la pagina lo relacionado con el filtrado
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr/td[2]", 2).get(0).getText().equals("Wakakaka");
		//Realizamos un filtrado para una tarea que no exista
		filtrado.click();
		filtrado.clear();
		filtrado.sendKeys("noexistoporqueno");
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "No records found.");
	}
	//PR19: Funcionamiento correcto de la ordenación por categoría.
	@Test
	public void prueba19() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de week
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listWeek");
		Thread.sleep(1000);
		List<Task> tareasWeek;
		try {
			tareasWeek = Factories.services.getTaskService().findWeekTasksByUserId((long) 1);
			List<Date> aux = new ArrayList<Date>();
			for(Task tarea : tareasWeek){
				aux.add(tarea.getPlanned());
			}
			java.util.Collections.sort(aux);
			//Comprobamos que la primera fecha de las tareas visualizadas sea la primera fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[4]", 2).get(0).getText().equals(aux.get(0));
			//					Thread.sleep(1000);
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que la ultima fecha de las tareas visualizadas sea la ultima fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR20: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba20() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de week
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listToday");
		Thread.sleep(1000);
		List<Task> tareasToday;
		try {
			tareasToday = Factories.services.getTaskService().findTodayTasksByUserId((long) 1);
			List<Date> aux = new ArrayList<Date>();
			for(Task tarea : tareasToday){
				aux.add(tarea.getPlanned());
			}
			java.util.Collections.sort(aux);
			//Comprobamos que la primera fecha de las tareas visualizadas sea la primera fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[4]", 2).get(0).getText().equals(aux.get(0));
			//					Thread.sleep(1000);
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que la ultima fecha de las tareas visualizadas sea la ultima fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	//PR24: Funcionamiento correcto de la ordenación por día.
	@Test
	public void prueba24() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de week
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listWeek");
		Thread.sleep(1000);
		List<Task> tareasWeek;
		try {
			tareasWeek = Factories.services.getTaskService().findWeekTasksByUserId((long) 1);
			List<Date> aux = new ArrayList<Date>();
			for(Task tarea : tareasWeek){
				aux.add(tarea.getPlanned());
			}
			java.util.Collections.sort(aux);
			//Comprobamos que la primera fecha de las tareas visualizadas sea la primera fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[4]", 2).get(0).getText().equals(aux.get(0));
			//					Thread.sleep(1000);
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que la ultima fecha de las tareas visualizadas sea la ultima fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR25: Funcionamiento correcto de la ordenación por nombre.
	@Test
	public void prueba25() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de week
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listWeek");
		Thread.sleep(1000);
		List<Task> tareasWeek;
		try {
			tareasWeek = Factories.services.getTaskService().findTodayTasksByUserId((long) 1);
			List<String> aux = new ArrayList<String>();
			for(Task tarea : tareasWeek){
				aux.add(tarea.getTitle());
			}
			java.util.Collections.sort(aux);
			//Comprobamos que la primera fecha de las tareas visualizadas sea la primera fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[4]", 2).get(0).getText().equals(aux.get(0));
			//					Thread.sleep(1000);
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que la ultima fecha de las tareas visualizadas sea la ultima fecha de las tareas de la BD
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	//PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir a la pagina donde está la tarea terminada y comprobar que se muestra. 
//	@Test
//    public void prueba26() {
//		assertTrue(false);
//    }
	//PR27: Crear una tarea sin categoría y comprobar que se muestra en la lista Inbox.
	@Test
	public void prueba27() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en Añadir tarea
		WebElement addTask = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[2]/a/span", 2).get(0);
		addTask.click();
		Thread.sleep(1000);
		//Metemos el nuevo titulo a la tarea que vamos a crear
		WebElement titulo = driver.findElement(By.id("form-principal:title"));
		titulo.clear();
		titulo.click();
		titulo.sendKeys("Prueba 27");
		//Ponemos fecha de planificacion
		WebElement planned = driver.findElement(By.id("form-principal:planned_input"));
		planned.clear();
		planned.click();
		WebElement fecha = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[2]/table/tbody/tr[5]/td[3]/a", 2).get(0);
		fecha.click();
		//Guardamos la tarea que acabamos de crear
		By boton = By.id("form-principal:botonGuardar");
		driver.findElement(boton).click();
		Thread.sleep(1000);
		SeleniumUtils.textoPresentePagina(driver, "Se ha añadido la tarea correctamente.");
		List<Task> tareasInbox;
		try {
			tareasInbox = Factories.services.getTaskService().findInboxTasksByUserId((long) 1);
			List<Long> aux = new ArrayList<Long>();
			for(Task tarea : tareasInbox){
				aux.add(tarea.getId());
			}
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que el ultimo id es el de la tarea que acabamos de crear en la BD
			SeleniumUtils.textoPresentePagina(driver, "Prueba 27");
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y comprobar que se muestra en la lista Hoy.
	@Test
	public void prueba28() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en Añadir Tarea
		WebElement addTask = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[2]/a/span", 2).get(0);
		addTask.click();
		Thread.sleep(1000);
		//Metemos el nuevo titulo a la tarea que vamos a crear
		WebElement titulo = driver.findElement(By.id("form-principal:title"));
		titulo.clear();
		titulo.click();
		titulo.sendKeys("Prueba 28");
		//Ponemos fecha de planificacion
		WebElement planned = driver.findElement(By.id("form-principal:planned_input"));
		planned.clear();
		planned.click();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		planned.sendKeys(String.valueOf(dateFormat.format(date)));
		//Asignamos que la categoria sea la categoria1
		WebElement category = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[4]/td[2]/div[1]/label", 3).get(0);
		category.click();
		WebElement choosen = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[3]/div/ul/li[2]", 2).get(0);
		choosen.click();
		//Guardamos la tarea que acabamos de crear
		By boton = By.id("form-principal:botonGuardar");
		driver.findElement(boton).click();
		Thread.sleep(1000);
		List<Task> tareasToday;
		try {
			tareasToday = Factories.services.getTaskService().findTodayTasksByUserId((long) 1);
			List<Long> aux = new ArrayList<Long>();
			for(Task tarea : tareasToday){
				aux.add(tarea.getId());
			}
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que el ultimo id es el de la tarea que acabamos de crear en la BD
			SeleniumUtils.textoPresentePagina(driver, "Prueba 28");
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior a Hoy y comprobar que se muestra en la lista Semana.
	@Test
	public void prueba29() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en Añadir Tarea
		WebElement addTask = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[2]/a/span", 2).get(0);
		addTask.click();
		Thread.sleep(1000);
		//Metemos el nuevo titulo a la tarea que vamos a crear
		WebElement titulo = driver.findElement(By.id("form-principal:title"));
		titulo.clear();
		titulo.click();
		titulo.sendKeys("Prueba 29");
		//Ponemos fecha de planificacion
		WebElement planned = driver.findElement(By.id("form-principal:planned_input"));
		planned.clear();
		planned.click();
		WebElement nextFecha = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[2]/div/a[2]/span", 2).get(0);
		nextFecha.click();
		Thread.sleep(1000);
		WebElement fecha = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[2]/table/tbody/tr[5]/td[3]/a", 2).get(0);
		fecha.click();
		//Asignamos que la categoria sea la categoria1
		WebElement category = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[4]/td[2]/div[1]/label", 2).get(0);
		category.click();
		Thread.sleep(1000);
		WebElement choosen = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[3]/div/ul/li[2]", 2).get(0);
		choosen.click();
		//Guardamos la tarea que acabamos de crear
		By boton = By.id("form-principal:botonGuardar");
		driver.findElement(boton).click();
		Thread.sleep(1000);
		List<Task> tareasWeek;
		try {
			tareasWeek = Factories.services.getTaskService().findWeekTasksByUserId((long) 1);
			List<Long> aux = new ArrayList<Long>();
			for(Task tarea : tareasWeek){
				aux.add(tarea.getId());
			}
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que el ultimo id es el de la tarea que acabamos de crear en la BD
			SeleniumUtils.textoPresentePagina(driver, "Prueba 29");
			SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td[4]", 2).get(0).getText().equals(aux.get(aux.size()-1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR30: Editar el nombre, y categoría de una tarea (se le cambia a categoría1) de la lista Inbox y comprobar que las tres pseudolista se refresca correctamente.
	@Test
	public void prueba30() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "john", "john123");
		Thread.sleep(1000);
		//Hacemos click en Editar la Tarea 1
		WebElement editTask = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[5]/a", 2).get(0);
		editTask.click();
		Thread.sleep(1000);
		//Metemos el nuevo titulo a la tarea que vamos a editar
		WebElement titulo = driver.findElement(By.id("form-principal:title"));
		titulo.clear();
		titulo.click();
		titulo.sendKeys("Prueba 30");
		//Asignamos que la categoria sea la categoria1
		WebElement category = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[4]/td[2]/div[1]/label", 2).get(0);
		category.click();
		Thread.sleep(1000);
		WebElement choosen = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[3]/div/ul/li[2]", 2).get(0);
		choosen.click();
		//Guardamos la tarea que acabamos de crear
		WebElement boton = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td/input", 2).get(0);
		boton.click();
		Thread.sleep(2000);
		SeleniumUtils.textoPresentePagina(driver, "Se ha modificado la tarea correctamente.");
		List<Task> tareasWeek;
		try {
			tareasWeek = Factories.services.getTaskService().findWeekTasksByUserId((long) 2);
			List<Long> aux = new ArrayList<Long>();
			for(Task tarea : tareasWeek){
				aux.add(tarea.getId());
			}
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que el ultimo id es el de la tarea que acabamos de crear en la BD
			SeleniumUtils.textoPresentePagina(driver, "Prueba 30");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan correctamente.
	@Test
	public void prueba31() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de today
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listToday");
		Thread.sleep(1000);
		//Hacemos click en Editar la Tarea 1
		WebElement editTask = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[6]/a", 2).get(0);
		editTask.click();
		Thread.sleep(1000);
		//Metemos el nuevo titulo a la tarea que vamos a editar
		WebElement titulo = driver.findElement(By.id("form-principal:title"));
		titulo.clear();
		titulo.click();
		titulo.sendKeys("Prueba 31");
		//Asignamos que la categoria sea la categoria1
		WebElement category = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[4]/td[2]/div[1]/label", 2).get(0);
		category.click();
		Thread.sleep(1000);
		WebElement choosen = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/div[3]/div/ul/li[2]", 2).get(0);
		choosen.click();
		//Guardamos la tarea que acabamos de crear
		WebElement boton = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[5]/td/input", 2).get(0);
		boton.click();
		Thread.sleep(2000);
		List<Task> tareasWeek;
		try {
			tareasWeek = Factories.services.getTaskService().findInboxTasksByUserId((long) 1);
			List<Long> aux = new ArrayList<Long>();
			for(Task tarea : tareasWeek){
				aux.add(tarea.getId());
			}
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que el ultimo id es el de la tarea que acabamos de crear en la BD
			SeleniumUtils.textoPresentePagina(driver, "Prueba 31");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las tres pseudolistas.
	@Test
	public void prueba32() throws InterruptedException {
		//Accedemos como usuario normal para poder visualizar la lista de tareas
		new PO_LoginForm().rellenaFormulario(driver, "mary", "mary1234");
		Thread.sleep(1000);
		//Hacemos click en el submenú de today
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:menuListas", "form-cabecera:listToday");
		Thread.sleep(1000);
		//Hacemos click en Finalizar la Tarea 1
		WebElement finTask = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[2]/table/tbody/tr[1]/td[7]/a", 2).get(0);
		finTask.click();
		Thread.sleep(1000);
		List<Task> tareasWeek;
		try {
			tareasWeek = Factories.services.getTaskService().findTodayTasksByUserId((long) 1);
			List<Long> aux = new ArrayList<Long>();
			for(Task tarea : tareasWeek){
				aux.add(tarea.getId());
			}
			//Hacemos click en el boton de acceder a la ultima pagina para ir a la última tarea
			WebElement pasoUltimaPagina = SeleniumUtils.EsperaCargaPaginaxpath(driver, "/html/body/form[2]/div/div[1]/span[5]/span", 2).get(0);
			pasoUltimaPagina.click();
			Thread.sleep(1000);
			//Comprobamos que el ultimo id es el de la tarea que acabamos de crear en la BD
			SeleniumUtils.textoPresentePagina(driver, "Prueba 31");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PR33: Salir de sesión desde cuenta de administrador.
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