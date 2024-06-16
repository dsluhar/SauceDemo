package com.saucedemo.base;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseClass {

	public static WebDriver driver = null;
	public static Properties Config = new Properties();
	public static FileInputStream fis ;
	
	@BeforeSuite(groups = {"api", "regression", "smoke"})
	public void BaseClass1() throws IOException, InterruptedException 
	{
		try {
			 fis = new FileInputStream(System.getProperty("user.dir") + "\\Configuration\\Config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Config.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		   if (Config.getProperty("browser").equalsIgnoreCase("chrome")) {
			   driver = new ChromeDriver();
			   
		   }
		   else if (Config.getProperty("browser").equalsIgnoreCase("firefox")) {
			   driver = new FirefoxDriver();
			   
		   }
		   else if (Config.getProperty("browser").equalsIgnoreCase("edge")) {
			   driver = new EdgeDriver();
			   
		   }
		   driver.get(Config.getProperty("url"));
		   driver.manage().window().maximize();
		   driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitWait")), TimeUnit.SECONDS);
		}
		
	   @AfterSuite(groups = {"api", "regression", "smoke"})
	    public void generateReport() throws IOException, InterruptedException {
		   Thread.sleep(5000);
		   driver.close();
	    }
	
}
