package cw.kazi.cst3130;
import javax.persistence.*;
/*
 * Model of db_comparison class
 * associates with the table in database
 */
@Entity
@Table(name="db_comparison")
public class db_comp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "fees")
	private int fees;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "degree_id")
	private int deg_id;
	
	@Column(name="university_id")
	private int uni_id;
	
	//constructor
	/*
	 * No argument constructor for class db_comp
	 */
	public db_comp() {
		fees = 0;
		url = "";
	}
	
	//getters
	/*
	 * getter function for private member fees
	 * return integer 
	 */
	public int getFees() {
		return this.fees;
	}
	/*
	 * getter function for private member url
	 * return string
	 */
	public String getUrl() {
		return this.url;
	}
	/*
	 * getter function for private member degree Id
	 * return integer 
	 */
	public int getDeg_id()
	{
		return this.deg_id;
	}
	/*
	 * getter function for private member university ID
	 * return integer 
	 */
	public int getUni_id()
	{
		return this.uni_id;
	}

	//setters
	/*
	 * setter function for private member fees
	 * @param int 
	 */
	public void setFees(int x) {
		this.fees = x;
	}
	/*
	 * setter function for private member url
	 * @param string 
	 */
	public void setUrl(String x) {
		this.url = x;
	}
	/*
	 * setter function for private member deggree id
	 * @param int 
	 */
	public void setDeg_id(int x)
	{
		this.deg_id = x;
	}
	/*
	 * setter function for private member university id
	 * @param int 
	 */
	public void setUni_id(int x)
	{
		this.uni_id = x;
	}
	
}
