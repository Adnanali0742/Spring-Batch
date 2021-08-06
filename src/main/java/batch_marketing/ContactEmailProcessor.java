package batch_marketing;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ContactEmailProcessor implements ItemProcessor<Contact, Contact> {
	
	private static final Logger log = LoggerFactory.getLogger(ContactEmailProcessor.class);

	@Override
	public Contact process(final Contact contact) throws Exception {
		final String email = contact.getContact_email();
		final String address = contact.getContact_address();
		final String firstName = contact.getContact_first_name();
		final String cityName = contact.getContact_city();
		
		final Contact transformedContact = new Contact(email, address, firstName, cityName);

		log.info("Converting (" + contact + ") into (" + transformedContact + ")");
		
		Path filePath = Paths.get("src/main/resources/campaign-free-gift-template.html");
	    String content = Files.readString(filePath);
	    content = content.replace("${firstName}", firstName);
	    content = content.replace("${city}", cityName);
	    content = content.replace("contact@example.com", email);
	    content = content.replace("New York, 222 West 23rd", address);
	    
//		StringBuffer html = new StringBuffer();
//		FileReader fr = new FileReader("src/main/resources/email.html");
//
//		try {
//			BufferedReader br = new BufferedReader(fr);
//			FileWriter fileWriter = new FileWriter("src/main/resources/templates/email.html");
//			String val;
//			while((val=br.readLine())!=null) {
//				html.append(val);
//			}
//			br.close();
//			String result = html.toString();
////			System.out.println(result);
//			
//			String replaceString=result.replace("${name}", firstName);
//			fileWriter.write(replaceString);					
//			
////			try(FileWriter fileWriter = new FileWriter("src/main/resources/templates/email.html")) {
//				//inherited method from java.io.OutputStreamWriter 
////					fileWriter.write(replaceString);					
////
////			} catch (Exception e) {
////				e.printStackTrace();
////							}
//			
//			System.out.println(replaceString);
//			
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.getMessage());
//		}
		
	    String filePathWriter = "src/main/resources/emailFiles/Mr-" + firstName + ".html";
		Path path = Paths.get(filePathWriter);
        if (!Files.exists(path)) {
    		File file = new File(filePathWriter);
    		if (file.exists()) {
    		} else {
    			System.out.println("File doesn't exists.");
    		}
        }
		FileWriter writer = new FileWriter(filePathWriter);
	    writer.write(content);
	    writer.close();
	    return transformedContact;
	}
}