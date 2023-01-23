package cw.kazi.cst3130;


public class App 
{
	/**
	 * Main function
	 */
    public static void main( String[] args )
    {
    	//creating instance of grenwich university
    	GreenWich grnwch = new GreenWich();
    	//running scraper
    	grnwch.GetLinksforGreenwich();

    	//creating instance of GoldSmith University
    	GoldSmith gldsmt = new GoldSmith();
    	//calling scraper of Gold smith university
    	gldsmt.GetLinksforGoldsmith();

    	//creating scraper of Westminster university
    	WestMinster wstmnstr = new WestMinster();	
    	//calling scrapper of westminster university
    	wstmnstr.GetLinksforWestminster();

	
	
	
	System.out.println("Done");
	return;
        
    }
}
