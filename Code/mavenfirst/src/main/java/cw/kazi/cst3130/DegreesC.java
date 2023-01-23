package cw.kazi.cst3130;

import javax.persistence.*;
/*
 * Model of Degree class that associates with the database
 */
@Entity
@Table(name="degrees")
public class DegreesC {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name="type")
	private String type;
	
	@Column(name="subject")
	private String subject;
	
	/*
	 * No argument constructor for class DegreesC
	 */
	public DegreesC() {
		description = "";
		type = "";
		subject = "";
	}
	/*
	 * getter function for private memeber id
	 * @return integer Id 
	 */
	public int getId() {
		return this.id;
	}
	/*
	 * getter function for private memeber Subject
	 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}
	/*
	 * getter function for private memeber Description
	 * @return String
	 */
	public String getdescription() {
		return this.description;
	}
	/*
	 * getter function for private member type 
	 * @return String
	 */
	public String getType() {
		return this.type;
	}
	/*
	 * setter function for private memeber Subject
	 * @param String
	 */
	public void setSubject(String x) {
		this.subject = x;
	}
	/*
	 * setter function for private memeber Description
	 * @param String
	 */
	public void setdescription(String x) {
		this.description = x;
	}/*
	 * setter function for private memeber type
	 * @param String
	 */
	public void setType(String x) {
		this.type = x;
	}
	/*
	 * setter function for private memeber id
	 * @param Integer
	 */
	public void setId(int x) {
		this.id = x;
	}
}
