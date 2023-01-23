package cw.kazi.cst3130;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.NoSuchElementException;
/*
 * Scraper class for Greenwich University
 */
public class GreenWich {
	HibernateConnections dao;
	
	/*
	 * No argument constructor
	 * Initialises hibernate connections to database
	 */
	GreenWich()
	{
		dao = new HibernateConnections();
		dao.init();
	}
	/*
	 * Scrapper function for GreenWich univeristy
	 * @throws if the link trying to get is not found
	 * @throws NoSuchElementException if the item in the DOM is not found
	 * @throws StringIndexOutOfBoundsException for string cleaning if not the correct field is scraped throws exception
	 * @throws NullPointerException if field scrapping for is not found and cannot scrape any data
	 * @throws ArrayIndexOutOfBoundsException the array size is limited if the number of courses does exceed the limit
	 */
	public void GetLinksforGreenwich() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		WebDriver driver = new ChromeDriver(options);
		String links[] = new String[500];
		System.out.println("Scrapping Greenwich Univeristy ------------>\n\n\n\n");
		driver.get("https://www.gre.ac.uk/undergraduate-courses/a-z");
		List<WebElement> linkList = driver.findElements(By.className("gre-three-columns"));			
		int j=0;	
		for(WebElement l : linkList) {
			List<WebElement> link = l.findElements(By.tagName("a"));
			for(WebElement k : link) {
				links[j] = k.getAttribute("href");
				j++;
			}
		}
		driver.get("https://www.gre.ac.uk/finance/fees/international-fees#international_fees");
		String fees_list[] = new String[500];
		try {
			System.out.println("sleeping");
			//westminster university has a different page that discusses all the 
			//this website takes unusually long time to load the webpage that contains the fees
			Thread.sleep(15000);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		for(int a = 1; a < 199; a++)
		{
			String fms = driver.findElement(By.xpath("//*[@id='fees-listing-0']/tr[" +a+"]/td[4]")).getAttribute("innerText").trim();
			fees_list[a] = fms;
		}				
		int i= 150;
		while(links[i] != null)
		{
			//CourseDetails obj1 = new CourseDetails();
			//scraping individual course details from the links scrapped earlier
			try{
			driver.get(links[i]);
			}
			catch(Exception ex)
			{
				i++;
				continue;
			}

			//subject
			String coursename = "";
			try {
			coursename = driver.findElement(By.className("gre-long-title")).getText();
			}
			catch(NoSuchElementException e)
			{
				i++;
				continue;
			}
			//Url
			 String url = links[i];
			 //location
			 //Four some courses the campus information is not disclosed yet
			 String location;
			 WebElement a;
			 try {
				 a = driver.findElement(By.id("prog-campus"));
				 WebElement b = a.findElement(By.tagName("a"));
				 location = b.getText();
			 }
			 catch(NoSuchElementException ex){
				 location = "Not disclosed By University"; 
			 }
			 //course description
			 String description = "";
			 try {
			WebElement x  = driver.findElement(By.className("gre-prog-overview-text"));
			description = x.getText();
			 }
			 catch(NoSuchElementException e)
			 {
				i++;
				 continue;
			 }
						
			int final_fees = 0;
			try {
			String fee1 = fees_list[i].substring(1, 3);
			String fee2 = fees_list[i].substring(4);
			String fee3 = fee1.concat(fee2);
			final_fees = Integer.parseInt(fee3);
			}
			catch(StringIndexOutOfBoundsException e)
			{
				i++;
				continue;
			}
			catch(NullPointerException e)
			{
				i++;
				continue;
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("done");
				driver.quit();
				return;
			}
			i++;
			universityC obj1 = new universityC();
			DegreesC obj2 = new DegreesC();
			db_comp obj3 = new db_comp();
			
			//CourseDetails obj1 = new CourseDetails();
			obj1.setLocation(location);
			obj1.setName("Greenwich");
			int uni_id = dao.saveUniversity(obj1);
			
			obj2.setdescription(description);
			obj2.setSubject(coursename);
			obj2.setType("Bsc");
			int deg_id = dao.saveDegree(obj2);
			
			obj3.setFees(final_fees);
			obj3.setUrl(url);
			obj3.setDeg_id(deg_id);
			obj3.setUni_id(uni_id);
			dao.savedb_comp(obj3);
			
		}		
		System.out.println("done");
		driver.quit();		
	}
}
