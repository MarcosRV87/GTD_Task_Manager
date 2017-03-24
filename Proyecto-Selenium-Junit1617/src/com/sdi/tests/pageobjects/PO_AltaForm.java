package com.sdi.tests.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_AltaForm {

	
	
   public void rellenaFormulario(WebDriver driver, String pnombre, String papellidos, String piduser, String pemail)
   {
		WebElement nombre = driver.findElement(By.id("form-principal:nombre"));
		nombre.click();
		nombre.clear();
		nombre.sendKeys(pnombre);
		WebElement apellidos = driver.findElement(By.id("form-principal:apellidos"));
		apellidos.click();
		apellidos.clear();
		apellidos.sendKeys(papellidos);
		WebElement iduser = driver.findElement(By.id("form-principal:iduser"));
		iduser.click();
		iduser.clear();
		iduser.sendKeys(piduser);
		WebElement idcorreo = driver.findElement(By.id("form-principal:correo"));
		idcorreo.click();
		idcorreo.clear();
		idcorreo.sendKeys(pemail);
		//Pulsar el boton de Alta.
		By boton = By.id("form-principal:boton");
		driver.findElement(boton).click();	   
   }
	
	
	
}
