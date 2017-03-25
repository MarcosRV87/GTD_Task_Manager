package com.sdi.tests.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_RegisterForm {

	
	
   public void rellenaFormulario(WebDriver driver, String plogin, String pmail, String ppassword, String prepass)
   {
		WebElement login = driver.findElement(By.id("form-principal:login"));
		login.click();
		login.clear();
		login.sendKeys(plogin);
		WebElement mail = driver.findElement(By.id("form-principal:correo"));
		mail.click();
		mail.clear();
		mail.sendKeys(pmail);
		WebElement password = driver.findElement(By.id("form-principal:password"));
		password.click();
		password.clear();
		password.sendKeys(ppassword);
		WebElement repassword = driver.findElement(By.id("form-principal:repassword"));
		repassword.click();
		repassword.clear();
		repassword.sendKeys(prepass);
		//Pulsar el boton de Aceptar.
		By boton = By.id("form-principal:botonGuardar");
		driver.findElement(boton).click();	   
   }
	
	
	
}
