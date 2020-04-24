package com.qa.hs.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.hs.keyward.base.Base;

public class KeywordEngine {
	public WebDriver driver;
	public WebElement element;
	public Base base;
	public Properties prop;
	public static Workbook book;
	public static Sheet sheet;
	String locatorName=null;
	String locatorValue=null;
	
	public final String SCENARIO_SHEET_PATH="C:\\Users\\Satya\\eclipse-workspace\\KeywarddrivenHubspot\\src\\main\\java\\com\\qa\\hs\\keyword\\scenarious\\hubspot_scenarios.xlsx";
	
	public void startExcution(String sheetName) {
		FileInputStream file=null;
		try {
			file=new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			book=WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		sheet=book.getSheet(sheetName);
		int k=0;
		for(int i=0;i<sheet.getLastRowNum();i++) {
			try {
			String locatorcolvalue=sheet.getRow(i+1).getCell(k+1).toString().trim(); //id=username
			if(!locatorcolvalue.equalsIgnoreCase("NA")) {
				locatorName=locatorcolvalue.split("=")[0].trim(); //id
				locatorValue=locatorcolvalue.split("=")[1].trim(); //username
			}
			String action=sheet.getRow(i+1).getCell(k+2).toString().trim();
			String value=sheet.getRow(i+1).getCell(k+3).toString().trim();
			switch (action) {
			case "open browser":
				prop=base.init_properties();
				base=new Base();
				if(value.isEmpty()||value.equals("NA")) {
					base.init_driver(prop.getProperty("browser"));
				}
				else {
					driver=base.init_driver(value);
				}
				break;
			case "enter url":
				if(value.isEmpty()||value.equals("NA")) {
					driver.get(prop.getProperty("url"));
					
				}
				else {
					driver.get(value);
				}
				break;
			case "quit":
				driver.quit();
				break;

			default:
				break;
			}
			switch (locatorName) {
			case "id":
				 element=driver.findElement(By.id(locatorValue));
				if(action.equalsIgnoreCase("sendkeys")) {
					element.sendKeys(value);
				}
				else if(action.equalsIgnoreCase("click")){
					element.click();
				}
				locatorName=null;
				break;
			case "linkText":
				element=driver.findElement(By.linkText(locatorValue));
				element.click();
				locatorName=null;
				break;
				default:
				break;
			}
			
		}
			catch(Exception e) {
			}
			
			}
		
	}
	

}
