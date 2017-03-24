package com.sdi.tests.Tests;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.sdi.tests.utils.SeleniumUtils;

public class PrimeFaces_Tests {

	WebDriver driver; // = new FirefoxDriver();
	List<WebElement> elementos = null;
	public PrimeFaces_Tests()
	{
	}

	@Before
	public void run()
	{
		//Vamos a la pagina principal
		driver = new FirefoxDriver();
 	}
	@After
	public void end()
	{
	  driver.quit();
	}

	

	@Test
	public void PrimefacesOrg_Autocomplete() throws InterruptedException
	{
		//Vamos a la vista de autocomplete
		driver.get("http://www.primefaces.org/showcase/ui/input/autoComplete.xhtml");

		//Esperamos que aparezca el elemento themeCustom_input 
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "themeCustom_input", 2); 
		
		//Pinchamos en el elemento themeCustom y escribimos un texto
		elementos.get(0).click();
		//Escribimos en el campo tecla a tecla. Eso lo hace builder.sendKeys. (No vale el sendKeys de WebElement)
		Actions builder = new Actions(driver);	    
		builder.sendKeys("a").perform(); 
		
		//Esperar porque se cargue la lista de sugerencias
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "ui-autocomplete-item", 2); 
		
		//Movemos el ratón al cuarto elemento
		builder.moveToElement(elementos.get(4)).perform();

		//Pinchamos con el ratón en la opción cuarta
		elementos.get(4).click();

		//OJO OJO. No intentes acceder al atributo al atributo value
		//del campo themeCuston ya que estará vacío.
		//POrque xpath sólo accede al arbol DOM del página cuando se creó.
		//tendríamos que acceder vía Js para acceder a la "propiedad" value
		//del campo themeCustom para comprobar su valor.
		//O bien pinchar el botón ACEPTAR y comprobar
		//el diálogo con los valores que se han introducido.
		//La cuestión es que el campo ya está rellenado para ser enviado 
		//via Primefaces a la propiedad del bean correspoindiente.
	}


	@Test
	public void PrimefacesOrg_Filtrado () throws InterruptedException
	{
		//Vamos a la vista de filtrado-ordenacion-paginacion
		driver.get("http://www.primefaces.org/showcase/ui/data/datatable/lazy.xhtml");

		//Esperamos que aparezca el elemento filter			
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "ui-column-filter", 2); 
		
		//Pinchamos en segundo filtro (el de año)
		elementos.get(1).click();

		//Escribimos en el campo tecla a tecla. Eso lo hace builder.sendKeys. (No vale el sendKeys de WebElement)
		Actions builder = new Actions(driver);	    
		builder.sendKeys("1984").perform(); // moveToElement(hoverElement).perform();

		//Esperar porque se recargue la página el resultado del filtrado
		Thread.sleep(1000);

		//COmo los datos son aleatorios es imposible saber lo que
		//vamos a encontrarnos pero podríamos...
		//Contar el número de filas resultados del filtrado (POr ejemplo  4)
		//tr[contains(@class, 'datatable-selectable')]
		//By busqueda = By.xpath("//tr[contains(@class, 'datatable-selectable')]");
		//campos = driver.findElements(busqueda);			
		//assertTrue("Filtrado incorrecto", campos.size()== 4);		
	}
	
	@Test
	public void PrimefacesOrg_Ordenacion () throws InterruptedException
	{
		//Vamos a la vista de filtrado-ordenacion-paginacion
		driver.get("http://www.primefaces.org/showcase/ui/data/datatable/lazy.xhtml");

		//Esperamos que aparezcan los botones de ordenacion
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "sortable-column-icon", 2); 
				
		//Pinchamos el segundo criterio de ordenacion
		Thread.sleep(500); //Esta espera es para poder el efecto de ordenación
		elementos.get(1).click();			
		//Pinchamos el cuarto criterio de ordenacion
		Thread.sleep(500); //Esta espera es para poder el efecto de ordenación
		elementos.get(3).click();
		//Pinchamos el cuarto criterio de ordenacion otra vez
		Thread.sleep(500); //Esta espera es para poder el efecto de ordenación
		elementos.get(3).click();

		//Ahora comprobamos que los datos están ordenados
	}
    @Test
	public void PrimefacesOrg_Paginacion () throws InterruptedException
	{
		//Vamos a la vista de filtrado-ordenacion-paginacion
		driver.get("http://www.primefaces.org/showcase/ui/data/datatable/lazy.xhtml");

		//Esperamos que aparezcan los enlaces de paginacion		
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "ui-paginator-next", 2); 
		
		//movemos el raton sobre el botón "siguiente pagina" (el de arriba)
		Actions builder = new Actions(driver);
        builder.moveToElement(elementos.get(0)).perform();   
		//Pinchamos el botón
        elementos.get(0).click();
		
		//Esperamos de nuevo
      	elementos = SeleniumUtils.EsperaCargaPagina(driver, "class", "ui-paginator-next", 2); 
      	
        //movemos el raton sobre el botón "siguiente pagina" (el de abajo)		
        builder.moveToElement(elementos.get(1)).perform();   
		//Pinchamos el botón
        elementos.get(1).click();

		//Ahora comprobamos que se ha cargado la pagina 3 de 20.
      	elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", "(3 of 20)", 2); 
      	   
	}




}