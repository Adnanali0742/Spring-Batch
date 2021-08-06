package batch_marketing;

import java.util.Date;

public class Contact {

	private String contact_email;
	private String contact_first_name;
	private String contact_last_name;
	private String contact_address;
	private String contact_city;
	private String contact_country;
	private Date contact_birthdate;

	public Contact(){

	}
	
	public Contact(String email, String address, String firstName, String city){
		this.contact_email = email;
		this.contact_address = address;
		this.contact_first_name = firstName;
		this.contact_city = city;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_first_name() {
		return contact_first_name;
	}

	public void setContact_first_name(String contact_first_name) {
		this.contact_first_name = contact_first_name;
	}

	public String getContact_last_name() {
		return contact_last_name;
	}

	public void setContact_last_name(String contact_last_name) {
		this.contact_last_name = contact_last_name;
	}

	public String getContact_address() {
		return contact_address;
	}

	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}

	public String getContact_city() {
		return contact_city;
	}

	public void setContact_city(String contact_city) {
		this.contact_city = contact_city;
	}

	public String getContact_country() {
		return contact_country;
	}

	public void setContact_country(String contact_country) {
		this.contact_country = contact_country;
	}

	public Date getContact_birthdate() {
		return contact_birthdate;
	}

	public void setContact_birthdate(Date contact_birthdate) {
		this.contact_birthdate = contact_birthdate;
	}

	@Override
	public String toString() {
		return "Contact [contact_email=" + contact_email + ", contact_first_name=" + contact_first_name
			 + ", contact_address=" + contact_address + ", contact_city=" + contact_city
			 + "]";
	}	
}