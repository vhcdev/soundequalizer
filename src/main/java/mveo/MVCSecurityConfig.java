package mveo;

import javax.sql.DataSource;

import mveo.services.DirectoryManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity
public class MVCSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource datasource;
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	DirectoryManagerService dirmanager;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers("/**").hasAnyRole("ANONYMOUS", "ADMIN", "USER").and()
				.formLogin().loginPage("/login").successHandler(new AuthenticationSuccessHandler("/", dirmanager));
		//
		// http.csrf()
		// .disable()
		// .authorizeRequests()
		// .antMatchers("/payment", "/registration", "/pictures/**", "/js/**",
		// "/", "/home", "/about",
		// "/equalizing", "/faq", "/download").hasAnyRole("ANONYMOUS", "ADMIN",
		// "USER")
		// .antMatchers("/user/**").hasAnyRole("ADMIN",
		// "USER").and().formLogin().loginPage("/login")
		// .successHandler(new SimpleUrlAuthenticationSuccessHandler("/"));

		//
		// http.csrf().disable().authorizeRequests()
		// .antMatchers("/payment", "/registration", "/pictures/**", "/js/**",
		// "/", "/home")
		// .hasAnyRole("ANONYMOUS", "ADMIN",
		// "USER").antMatchers("/download").hasAnyRole("ADMIN", "USER").and()
		// .formLogin().loginPage("/login");

		//
		// http.csrf().disable().authorizeRequests()
		// .antMatchers("/registration", "/pictures/**", "/js/**", "/", "/home",
		// "/faq").permitAll().anyRequest()
		// .authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);

	}
}