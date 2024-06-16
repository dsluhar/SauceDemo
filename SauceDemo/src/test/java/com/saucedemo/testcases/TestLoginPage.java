package com.saucedemo.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucedemo.base.BaseClass;
import com.saucedemo.pageobjects.*;

import net.datafaker.Faker;

public class TestLoginPage extends BaseClass {
	
@Test(testName = "Verify Login", priority=1)
public void TestVerifyLogin() throws IOException  {
	LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
	loginPage.ClickOnSignIn();    					//TestCase No.1
	}


@Test(dependsOnMethods={"TestVerifyLogin"},priority=2,dataProvider="dpData" )
public void TestClickProducts(Integer val) {
	
													//TestCase No.2
	WebDriverWait wt = new WebDriverWait(driver,Duration.ofSeconds(10));
	WebElement Prod1 = driver.findElement(By.xpath("(//button[@class='btn btn_primary btn_small btn_inventory '])[1]"));
	wt.until(ExpectedConditions.elementToBeClickable(Prod1));
	Prod1.click();
	}

@Test(dependsOnMethods={"TestClickProducts"})
public void TestClickCartButton() {

	driver.findElement(By.xpath("//div[@id='shopping_cart_container']//a[@class='shopping_cart_link']")).click();   //TestCase No.3
	String ProdTitle1 = "Sauce Labs Backpack";
	String ProdTitle2 = "Sauce Labs Bike Light";
	String ProdTitle3 = "Sauce Labs Bolt T-Shirt";
	
	WebElement WebELProd1 = driver.findElement(By.xpath("//a[@id='item_4_title_link' and @data-test='item-4-title-link']"));
	WebElement WebELProd2 = driver.findElement(By.xpath("//a[@id='item_0_title_link' and @data-test='item-0-title-link']"));
	WebElement WebELProd3 = driver.findElement(By.xpath("//a[@id='item_1_title_link' and @data-test='item-1-title-link']"));
	
	String ELProd1 = WebELProd1.getText();
	String ELProd2 = WebELProd2.getText();
	String ELProd3 = WebELProd3.getText();
	
	Assert.assertEquals(ELProd1, ProdTitle1);     	//TestCase No.4
	Assert.assertEquals(ELProd2, ProdTitle2);
	Assert.assertEquals(ELProd3, ProdTitle3);
	
}

@Test(dependsOnMethods={"TestClickCartButton"})
public void TestClickCheckOutButton() {
	driver.findElement(By.id("checkout")).click();    //TestCase No.5
}

@Test(dependsOnMethods={"TestClickCheckOutButton"})
public void GenerateRandomUser() {
	   
	   Faker faker = new Faker();
       // Generate a fake name
       String fUserName = faker.name().firstName();    //TestCase No.6
       String fPassword = faker.name().lastName();
       String Code = faker.number().digits(6);
       
       driver.findElement(By.id("first-name")).sendKeys(fUserName);
       driver.findElement(By.id("last-name")).sendKeys(fPassword);
       driver.findElement(By.id("postal-code")).sendKeys(Code);

       driver.findElement(By.id("continue")).click();     //TestCase No.7
       
       WebElement WP1 = driver.findElement(By.xpath("(//div[@class='inventory_item_price' and @data-test='inventory-item-price' and contains(text(), '$')])[1]")); 
       String Price1 = WP1.getText().replace("$", "").trim();
       double dValue1 = Double.parseDouble(Price1);

       WebElement WP2 = driver.findElement(By.xpath("(//div[@class='inventory_item_price' and @data-test='inventory-item-price' and contains(text(), '$')])[2]"));     
       String Price2 = WP2.getText().replace("$", "").trim();
       double dValue2 = Double.parseDouble(Price2);
       
       WebElement WP3 = driver.findElement(By.xpath("(//div[@class='inventory_item_price' and @data-test='inventory-item-price' and contains(text(), '$')])[3]"));     
       String Price3 = WP3.getText().replace("$", "").trim();
       double dValue3 = Double.parseDouble(Price3);

       double Sum = 0;
       Sum = dValue1+dValue2+dValue3;
              
       System.out.println("The Sum is : " +Sum);
       
       Assert.assertEquals(Sum, 55.97);              //TestCase No.8
       
       WebElement wTP = driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"));
       String PricewTP = wTP.getText().replaceAll("[^0-9.]", "");
       double dPricewTP = Double.parseDouble(PricewTP);

       WebElement wTax = driver.findElement(By.xpath("//div[@class='summary_tax_label']"));
       String PricewTax = wTax.getText().replaceAll("[^0-9.]", "");
       double dPricewTax = Double.parseDouble(PricewTax);
       
       double TotSum = 0;
       TotSum = dPricewTP + dPricewTax ;
       
       System.out.println("Total Sum is : " +TotSum);
       
       Assert.assertEquals(TotSum, 60.45);              //TestCase No.8
}

@Test(dependsOnMethods={"GenerateRandomUser"}, enabled=true)
public void ClickOnFinishButton() throws InterruptedException, IOException {
	driver.findElement(By.id("finish")).click();           
	boolean flag = false;
	WebElement OrderText = driver.findElement(By.xpath("//*[text()='Thank you for your order!']"));
	System.out.println("Text is : " + OrderText.getText());
	if (OrderText.getText().contains("Thank you for your order!"))  //TestCase No. 9 
	{ 
		flag=true;
		Assert.assertTrue(flag);
	}
	
	File Source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);   //ScreenShot
    FileUtils.copyFile(Source, new File("C:\\Dilip\\OrderAck.png"));

	
	WebElement Burger = driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")); 
	Burger.click();                            //TestCase No. 10
	
	Thread.sleep(1000);                     	//TestCase No. 11
    WebElement Logout = driver.findElement(By.xpath("//a[@id='logout_sidebar_link' and contains(@class, 'bm-item') and contains(@class, 'menu-item') and @data-test='logout-sidebar-link' and text()='Logout']"));
    Logout.click();
}

@Test(dependsOnMethods={"ClickOnFinishButton"})  
public void HompPage()   					//TestCase No. 12 
	{
	WebElement HomePg = driver.findElement(By.id("user-name"));
	boolean flag = false;
	if (HomePg.isDisplayed()) {
		flag=true;
		Assert.assertTrue(flag);
	}
	
}

@DataProvider (name = "dpData")     // Part of TestCase No.2
public Object[][] dpMethod() {
	return new Object[][] { { 1 }, { 2 }, { 3 } };
	}
}

