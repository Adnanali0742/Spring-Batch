package batch_marketing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public JdbcCursorItemReader<Contact> reader() {
		JdbcCursorItemReader<Contact> itemReader = new JdbcCursorItemReader<Contact>();
		itemReader.setDataSource(postgresDatasource());
		itemReader.setSql("SELECT contact_email, contact_address, contact_first_name, contact_city FROM contacts");
		itemReader.setRowMapper(new ContactRowMapper());
		int counter = 0;
		ExecutionContext executionContext = new ExecutionContext();
		itemReader.open(executionContext);
		Object contact = new Object();
		while(contact != null) {
			try {
				contact = itemReader.read();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
		}
		itemReader.close();
		return itemReader;
	}
//	@Bean
//	public DataSource dataSource(){
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		dataSource.setDriverClass(Driver.class);
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("321");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/houseDB"); //TODO complete
//		return dataSource;
//	}
	
    @Bean(name = "postgresDataSource")
    public DataSource postgresDatasource() {
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("org.postgresql.Driver");
        datasource.setUrl("jdbc:postgresql://localhost:5432/spring");
        datasource.setUsername("postgres");
        datasource.setPassword("321");
        return datasource;    }
	
	
//	@Bean
//	public JdbcCursorItemReader<Contact> itemReader(){
//	    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	    dataSource.setDriverClassName("org.postgresql.Driver");
	    
	    
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		dataSource.setDriverClass(Driver.class);
//	    dataSource.setUrl("jdbc:postgresql://localhost:5432/testDB");
//	    dataSource.setUsername("postgres");
//	    dataSource.setPassword("321");
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		dataSource.setDriverClass(Driver.class);
	    
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/testDB"); //TODO complete	    
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("321");
		
//		DriverManagerDataSource dataSource= new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/testDB");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("321");  
//
//	 
//	    JdbcCursorItemReader<Contact> reader = new JdbcCursorItemReader<Contact>();
//	    reader.setDataSource(dataSource);
//	    reader.setSql("SELECT contact_email, contact_address, contact_first_name FROM contacts");
//	    reader.setRowMapper(new ContactRowMapper());
//	    return reader;
//	}

	@Bean
	public ContactEmailProcessor processor() {
		return new ContactEmailProcessor();
	}

//	@Bean
//	public FlatFileItemWriter<Contact> writer() {
//		FlatFileItemWriter<Contact> writer = new FlatFileItemWriter<Contact>();
//		writer.setResource(new ClassPathResource("emails.csv"));
//		writer.setLineAggregator(new DelimitedLineAggregator<Contact>() {{
//			   setDelimiter(",");
//			   setFieldExtractor(new BeanWrapperFieldExtractor<Contact>() {{
//			    setNames(new String[] { "contact_email", "contact_address" });
//			   }});
//			  }});
//		return writer;
//		//FlatFileItemWriter<Contact> --> FlatFile...Writer<Email>
//	}
	
	@Bean
	public FlatFileItemWriter<Contact> itemWriter() {
	    FlatFileItemWriter<Contact> itemWriter = new FlatFileItemWriter<Contact>();
//	    String userHome = System.getProperty("user.home");
	    Resource outputResource = new FileSystemResource("src/main/resources/test_user.csv");
	    itemWriter.setResource(outputResource);
	    itemWriter.setLineAggregator(new DelimitedLineAggregator<Contact>() {{
	        setDelimiter(",");
	        setFieldExtractor(new BeanWrapperFieldExtractor<Contact>() {{
	            setNames(new String[] { "contact_email", "contact_address", "contact_first_name", "contact_city"});
	        }});
	    }});
	    return itemWriter;
	}
	
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("marketing-email-job")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Contact, Contact> chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(itemWriter())
				.build();
	}
}