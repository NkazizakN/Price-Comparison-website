package cw.kazi.cst3130;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * Scraper Class for WestMinster University
 */
public class WestMinster {
	HibernateConnections dao;
	
	/*
	 * No argument constructor
	 * Initialises hibernate connections to database
	 */
	WestMinster()
	{
		dao = new HibernateConnections();
		dao.init();
	}
	/*
	 * Scraper Function for westminster Univeristy
	 * @throws NumberFormatException when converting scrapped fees to interger not the correct format for cleaning
	 */
	public void GetLinksforWestminster() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		WebDriver driver = new ChromeDriver(options);
		String links[] = new String[200];
		int page = 0;
		int j = 0;
		System.out.println("Scrapping WestMinster Univeristy ------------>\n\n\n\n");
		for(int k=0; k<19; k++)
		{
			//the web site is designed to in a way to have 19 course pages
			//scraping individual course page to get the links of each course page			
			
		driver.get("https://www.westminster.ac.uk/course-search?f%5B0%5D=course_type%3A926&course=&page=" + page);
		page++;		
		//scraping the links to each course details
		List<WebElement> linkList = driver.findElements(By.className("details-pane__results-header"));
		for(WebElement l : linkList) {
			String link = l.findElement(By.tagName("a")).getAttribute("href");
			links[j] = link;
			j++;
		}
		}
		System.out.println("done scr1");
		int i= 0;
		while(links[i] != null)
		{
			//sleep to let javascrit load the page
			try {
				Thread.sleep(500);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			//scraping individual course details from the links scrapped earlier
			driver.get(links[i]);
			System.out.println("working..");
			String course = driver.findElement(By.className("h1--small")).getText();
			

			String fees = driver.findElements(By.className("course-overview__result-value")).get(1).getText();
			String fee1 = fees.substring(1, 3);
			String fee2 = fees.substring(4, 7);
			String fee_final = fee1.concat(fee2);
			int final_fees;
			if(fee_final == "2,00 ")
			{
				final_fees = 2400;
			}
			else
			{
				try{

					final_fees = Integer.parseInt(fee_final);
				}
				catch(NumberFormatException ex)
				{
					final_fees = 2400;
				}
			}

			String location = driver.findElements(By.className("course-overview__result-value")).get(3).getText();		
			String description = driver.findElements(By.className("row")).get(5).findElement(By.tagName("p")).getText();
			String url = links[i];
			System.out.println(url);
			
			universityC obj1 = new universityC();
			DegreesC obj2 = new DegreesC();
			db_comp obj3 = new db_comp();
			
			//CourseDetails obj1 = new CourseDetails();
			obj1.setLocation(location);
			obj1.setName("WestMinster");
			int uni_id = dao.saveUniversity(obj1);
			
			obj2.setdescription(description);
			obj2.setSubject(course);
			obj2.setType("Bsc");
			int deg_id = dao.saveDegree(obj2);
			
			obj3.setFees(final_fees);
			obj3.setUrl(url);
			obj3.setDeg_id(deg_id);
			obj3.setUni_id(uni_id);
			dao.savedb_comp(obj3);
			i++;
		}	
		System.out.println("done");
		driver.quit();
	}
}
