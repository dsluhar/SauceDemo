package com.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.base.BaseClass;

public class LoginPage extends BaseClass {

	public LoginPage(WebDriver driver) 
	{
	this.driver = driver;
	}

	@FindBy (id = "user-name")
	@CacheLookup
	public WebElement UserName ;
	
	@FindBy (id = "password")
	@CacheLookup
	public WebElement Password ;	
	
	@FindBy (id = "login-button")
	@CacheLookup
	public WebElement LoginSubmit ;	
	
	public void ClickOnSignIn() {
		UserName.sendKeys("standard_user");
		Password.sendKeys("secret_sauce");
		LoginSubmit.submit();
	}
	
}