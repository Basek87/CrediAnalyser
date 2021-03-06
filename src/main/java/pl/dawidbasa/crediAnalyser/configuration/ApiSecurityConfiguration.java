package pl.dawidbasa.crediAnalyser.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/")
		.permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/registration/**").permitAll()
		.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
		.anyRequest().authenticated()
		.and()
		.csrf().disable()
		.formLogin().loginPage("/login")
		.failureUrl("/login?error=true")
		.defaultSuccessUrl("/admin/home")
		.usernameParameter("email")
		.passwordParameter("password")
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}	
}
