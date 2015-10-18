package mveo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @SpringBootApplication is a convenience annotation that adds all of the
 *                        following:
 * @Configuration tags the class as a source of bean definitions for the
 *                application context.
 * @EnableAutoConfiguration tells Spring Boot to start adding beans based on
 *                          classpath settings, other beans, and various
 *                          property settings. Normally you would add @EnableWebMvc
 *                          for a Spring MVC app, but Spring Boot adds it
 *                          automatically when it sees spring-webmvc on the
 *                          classpath. This flags the application as a web
 *                          application and activates key behaviors such as
 *                          setting up a DispatcherServlet.
 * @ComponentScan tells Spring to look for other components, configurations, and
 *                services in the the hello package, allowing it to find the
 *                HelloController.
 * 
 * @author vhc
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(DataSourceProperties.class)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Application extends WebMvcConfigurerAdapter {
	// private static Log logger = LogFactory.getLog(Application.class);
	@Autowired
	private DataSource dataSource;

	// @Autowired
	// private DataSourceProperties properties;
	//
	// @Bean
	// @ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
	// public DataSource dataSource() {
	//
	// logger.debug(properties.getUrl() + " " + properties.getUsername());
	// DataSourceBuilder factory =
	// DataSourceBuilder.create(this.properties.getClassLoader())
	// .driverClassName(this.properties.getDriverClassName()).url(this.properties.getUrl())
	// .username(this.properties.getUsername()).password(this.properties.getPassword());
	//
	// return factory.build();
	// }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
