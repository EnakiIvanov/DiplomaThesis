package com.BusTicketSystem.ticketSystem.logic.bot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BusTicketSystem.ticketSystem.DatabaseAccess;

@Service
public class Bot {
	
	@Autowired
	DatabaseAccess dba;
	
	private List<List<String>> lists = new ArrayList<List<String>>();
	
	public void getInfo(){
		lists.clear();  
        String urll = "http://hebrosbus.com/bg/pages/razpisaniya/.5/-1/460/";
        
        System.setProperty("webdriver.chrome.driver","D:/EclipseEEWorkspace/DiplomaThesis/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless", "--disable-gpu", "--window-size=1280,1024","--ignore-certificate-errors");        
        
        WebDriver driver = new ChromeDriver(options);
        
        // Navigate to URL
        driver.get(urll);
        driver.findElement(By.xpath("//*[@id='ddlArrive_chosen']")).click();
	    
	  //  List<WebElement> lis = driver.findElements(By.xpath("//*[@id='ddlArrive_chosen']/div/ul/li"));
	    List<Integer> listDest = Arrays.asList(148,518,573,660,3653,4459);
	      for(int i = 0; i < listDest.size(); i++){//to work for all i = 1; i < lis.size()
	    	  int k = listDest.get(i);// to work for all remove this
	    	if(i > 0){// to work for all i > 1
	    		driver.findElement(By.xpath("//*[@id='ddlArrive_chosen']")).click();
	    	}
	    	// //*[@id="ddlArrive_chosen"]/div/ul/li[148] Bansko +
	    	// //*[@id="ddlArrive_chosen"]/div/ul/li[518] Brestovica +
	    	// //*[@id="ddlArrive_chosen"]/div/ul/li[573] Burgas +
	    	// //*[@id="ddlArrive_chosen"]/div/ul/li[660] Varna +
	    	// //*[@id="ddlArrive_chosen"]/div/ul/li[3653] Pleven +
	    	// //*[@id="ddlArrive_chosen"]/div/ul/li[4459] Sofia +
	    	driver.findElement(By.xpath("//*[@id='ddlArrive_chosen']/div/ul/li["+ k +"]")).click();// to work for all ["+i+"]
	    	driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_bAdvSearch']")).click();

	    	if(driver.findElements(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_gvRoutes']/tbody/tr/td/span")).size() != 0){
		    	Boolean checkSpan = driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_gvRoutes']/tbody/tr/td/span")).getText().equals("Няма намерени резултати.");
		    	if(checkSpan) continue;
	    	}

	    	String[] locations = driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_searchResults' or @id='ctl00_ctl00_cphBody_cphBody_additionalResults']/div/div[1]/div[1]/span[2]")).getText().split(" ");
	    	String goesFrom = locations[1];
	    	String arrivesTo = locations[3];

	    	List<WebElement> destInfo = driver.findElements(By.xpath("/html/body/form/div[3]/div[3]/div[2]/div[5]/div[1]/div[3]/table/tbody/tr"));
	    	for(int j = 2; j <= destInfo.size(); j++){
	    		String departureTime = driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_searchResults' or @id='ctl00_ctl00_cphBody_cphBody_additionalResults']/div/div[3]/table/tbody/tr["+j+"]/td[1]/span")).getText();												
	    		String hourOfArrival = driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_searchResults' or @id='ctl00_ctl00_cphBody_cphBody_additionalResults']/div/div[3]/table/tbody/tr["+j+"]/td[3]/span")).getText();												
	    		String price = driver.findElement(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_searchResults' or @id='ctl00_ctl00_cphBody_cphBody_additionalResults']/div/div[3]/table/tbody/tr["+j+"]/td[13]/span")).getText();										
	    		List<WebElement> activeDays = driver.findElements(By.xpath("//*[@id='ctl00_ctl00_cphBody_cphBody_searchResults' or @id='ctl00_ctl00_cphBody_cphBody_additionalResults']/div[1]/div[3]/table/tbody/tr["+j+"]/td[@class='active_days']/span"));
		    	
	    		for(WebElement wb : activeDays){
	    			String datte = getDaysFromTheCurrentWeek()[getDayNumByName(wb.getText())];
			    	String datanazaminavane = datte +" "+ departureTime+":00";
			    	String datapristigane = datte +" "+ hourOfArrival+":00";
	    			lists.add(Arrays.asList(goesFrom, arrivesTo, datanazaminavane, datapristigane, price));
	    		}	    			    
	    	}
	      }
        // Close driver
        driver.quit();
	}
	
	public void populateDB() {
		lists = lists.stream().distinct().collect(Collectors.toList());
		for(int i = 0; i < lists.size(); i++) {
			String goesFrom = lists.get(i).get(0).toString();
			String arrivesTo = lists.get(i).get(1).toString();
			String departureTime = lists.get(i).get(2).toString();
			String hourOfArrival = lists.get(i).get(3).toString();
			String price = String.valueOf(lists.get(i).get(4).split(",")[0].equals("-") ? "0" : lists.get(i).get(4).split(",")[0]);
			
			dba.addDestinations(goesFrom, arrivesTo, departureTime, hourOfArrival, price);
		}
	}
	
	public String[] getDaysFromTheCurrentWeek(){
		Calendar now = Calendar.getInstance();

	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	    String[] days = new String[7];
	    int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday

	    now.add(Calendar.DAY_OF_MONTH, delta );
	    for (int i = 0; i < 7; i++)
	    {
	        days[i] = format.format(now.getTime());
	        now.add(Calendar.DAY_OF_MONTH, 1);
	    }

	    return days;
	}
	
	public int getDayNumByName(String name){
		switch(name){
			case "Пн": return 0;
			case "Вт": return 1;
			case "Ср": return 2;
			case "Чт": return 3;
			case "Пт": return 4;
			case "Сб": return 5;
			case "Нд": return 6;
		}
		return 0;
	}
	
}
