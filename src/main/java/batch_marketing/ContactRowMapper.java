package batch_marketing;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContactRowMapper implements RowMapper<Contact> {


	@Override
	public Contact mapRow(ResultSet rs, int i) throws SQLException {
		Contact contact = new Contact();
		contact.setContact_email(rs.getString("contact_email"));
		contact.setContact_address(rs.getString("contact_address"));
		contact.setContact_first_name(rs.getString("contact_first_name"));
		contact.setContact_city(rs.getString("contact_city"));
		return contact;
	}
	
	
	//public class ContactRowMapper implements RowMapper<Contact> {
//	 public static final String EMAIL_COLUMN = "email";
//	 public static final String ADDRESS_COLUMN = "address";
//	 public static final String FIRST_NAME_COLUMN = "firstname";
//
//
//	@Override
//	public Contact mapRow(ResultSet resultSet, int i) throws SQLException {
//		Contact contact = new Contact();
//		contact.setEmail(EMAIL_COLUMN);
//		contact.setAddress(ADDRESS_COLUMN);
//		contact.setFirstName(FIRST_NAME_COLUMN);
//		
//		return contact;
//	}
	
}