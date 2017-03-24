package com.sdi.tests.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_LoginForm {

	
	
   public void rellenaFormulario(WebDriver driver, String plogin, String ppassword)
   {
		WebElement login = driver.findElement(By.id("form-principal:login"));
		login.click();
		login.clear();
		login.sendKeys(plogin);
		WebElement password = driver.findElement(By.id("form-principal:password"));
		password.click();
		password.clear();
		password.sendKeys(ppassword);
		//Pulsar el boton de Aceptar.
		By boton = By.id("form-principal:botonAceptar");
		driver.findElement(boton).click();	   
   }
	
	
	
}
