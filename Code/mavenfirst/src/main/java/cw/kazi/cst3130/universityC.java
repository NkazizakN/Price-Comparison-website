package cw.kazi.cst3130;

import javax.persistence.*;

@Entity(name = "univ")
@Table(name="university")
public class universityC {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="location")
	private String location;
	
	@Column(name="name")
	private String name;
	
	//constructor
	/*
	 * No argument constructor for class universityC
	 */
	public universityC()
	{
		location = "";
		name = "";
	}

	//getters
	/*
	 * getter function for private memeber Name
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
	/*
	 * getter function for private memeber Location
	 * @return String
	 */
	public String getLoation() {
		return this.location;
	}
	/*
	 * getter function for private memeber id
	 * @return Integer
	 */
	public int getId() {
		return this.id;
	}
	//setters
	/*
	 * setter function for private memeber Name
	 * @param String
	 */
	public void setName(String x) {
		this.name = x;
	}
	/*
	 * setter function for private memeber Location
	 * @param String
	 */
	public void setLocation(String x) {
		this.location = x;
	}
	/*
	 * setter function for private memeber Id
	 * @param Integer
	 */
	public void setId(int x) {
		this.id = x;
	}
}
