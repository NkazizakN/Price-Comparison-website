package cw.kazi.cst3130;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
/*
 * Scrapper for GoldSmith Univeristy
 */
public class GoldSmith {
	HibernateConnections dao;
	/*
	 * No argument constrctor
	 * Initialises hibernate connections to database
	 */
	GoldSmith()
	{
		dao = new HibernateConnections();
		dao.init();
	}
	/*
	 * Scrapper for GoldSmith Univeristy
	 * @throws IndexOutOfBound expection
	 */
	public void GetLinksforGoldsmith() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		WebDriver driver = new ChromeDriver(options);
		//scraping the university courses page		
		System.out.println("Scrapping GoldSmith Univeristy ------------>\n\n\n\n");
		driver.get("https://www.gold.ac.uk/ug/a-z/");
		List<WebElement> list = driver.findElements(By.className("teaser__title"));
		String links[] = new String[250];
		String course_name[] = new String[250];
		int j = 0;
		for(WebElement i : list) {
			String l = i.findElement(By.tagName("a")).getAttribute("href");
			String c = i.findElement(By.tagName("a")).getText();
			links[j] = l;
			course_name[j] = c;
			j++;
		}
		System.out.println("done scr1");
		int i = 0;
		while(links[i] != null)
		{
			//scraping individual course page for particular descriptions			
		driver.get(links[i]);
		//Subject
		String coursename = course_name[i];
		//description
		WebElement desc = driver.findElement(By.className("emphasis"));
		String description = desc.getText();
		//fees		
		WebElement stuff;
		//different pages has different class and position in the page where the fees is displayed
		//nested if statements to cut out exactly the specific parts from all the pages
		try {
			try {
				Thread.sleep(500);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			stuff = driver.findElements(By.xpath(".//*[@id='-contents']/div/div/div/ul/li[3]")).get(2);
			char v = stuff.getAttribute("innerText").charAt(0);
			if(v == 'I')
			{
				stuff = driver.findElements(By.xpath(".//*[@id='-contents']/div/div/div/ul/li[3]")).get(2);
			}
			else
			{
				stuff = driver.findElements(By.xpath("//*[@id='-contents']/div/div/div/ul/li[2]")).get(2);
				char v1 = stuff.getAttribute("innerText").charAt(0);
				if(v1 != 'I')
				{
					stuff = driver.findElements(By.xpath("//*[@id='-contents']/div/div/div/ul/li[2]")).get(3);
					char v2 = stuff.getAttribute("innerText").charAt(0);
					if(v2 != 'I')
					{
						stuff = driver.findElements(By.xpath("//*[@id='-contents']/div/div/div/ul/li[2]")).get(4);
						char v3 = stuff.getAttribute("innerText").charAt(0);
						if(v3 != 'I')
						{
							stuff = driver.findElements(By.xpath("//*[@id='-contents']/div/div/div/ul/li[2]")).get(5);
							char v4 = stuff.getAttribute("innerText").charAt(0);
							if(v4 != 'I')
							{
								stuff = driver.findElements(By.xpath("//*[@id='-contents']/div/div/div/ul/li[2]")).get(6);
								char v5 = stuff.getAttribute("innerText").charAt(0);
								if(v5 != 'I')
								{
									System.out.println("in here");
									stuff = driver.findElements(By.xpath("//*[@id=\"-contents\"]/div/div/div/ul/li[2]")).get(7);
									char v6 = stuff.getAttribute("innerText").charAt(0);
									if(v6 != 'I')
									{
										stuff = driver.findElements(By.xpath("//*[@id='-contents']/div/div/div/ul/li")).get(10);
									}
									
								}
							}
						}
					}
				}
				
			}
		}
		catch(IndexOutOfBoundsException ex){
			i++;
			continue;
		}
		String fees = "";
		fees = stuff.getAttribute("innerText");
		//for a particular page in the courses the fees is placed in an arbitrary position 
		//for that particular page the fees string is over written
		if(i == 18) {
			stuff = driver.findElements(By.xpath("//*[@id='-contents']/div/div/div/ul/li")).get(10);
			fees = stuff.getAttribute("innerText");
		}

		fees = fees.substring(28);
		int fees_final = Integer.parseInt(fees);
		//location
		String location = "London";
		//url
		String url = links[i];
		

		universityC obj1 = new universityC();
		DegreesC obj2 = new DegreesC();
		db_comp obj3 = new db_comp();

		//CourseDetails obj1 = new CourseDetails();
		obj1.setLocation(location);
		obj1.setName("Goldsmith");
		int uni_id = dao.saveUniversity(obj1);
		
		obj2.setdescription(description);
		obj2.setSubject(coursename);
		obj2.setType("Bsc");
		int deg_id = dao.saveDegree(obj2);
		
		obj3.setFees(fees_final);
		obj3.setUrl(url);
		obj3.setDeg_id(deg_id);
		obj3.setUni_id(uni_id);
		dao.savedb_comp(obj3);
		i++;
		
		//call Hibernate function
		}
		System.out.println("done");
		driver.quit();
	}

}
