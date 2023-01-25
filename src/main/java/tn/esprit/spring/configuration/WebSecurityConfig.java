package tn.esprit.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tn.esprit.spring.Security.jwt.AuthEntryPointJwt;
import tn.esprit.spring.Security.jwt.AuthTokenFilter;
import tn.esprit.spring.Security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/Auth/**").permitAll()
			.antMatchers("/Auth/login").permitAll()
			.antMatchers("/User/savePassword").permitAll()
			.antMatchers("/User/resetPassword").permitAll()
			.antMatchers("/User/**").permitAll()
			.antMatchers("/Photo/**").permitAll()	//		
			.antMatchers("/**").permitAll()
			.antMatchers("/User/**").permitAll()
			.antMatchers("/Poll/**").permitAll()
			.antMatchers("/User/getAllUsers").permitAll()
			.antMatchers("/Photo/getImageById/{id}").permitAll()
			.antMatchers("/Poll/create").permitAll()
            .antMatchers("/Badge/**").permitAll()
			.antMatchers("/User/rateColleague/{id}/{rate}").permitAll()
			.antMatchers("/User/rateCollaboration/{id}/{rate}").permitAll()
			.antMatchers("/User/rateEvent/{id}/{rate}").permitAll()
			.antMatchers("/FeedBack/makeFeedBack/{id}").permitAll()
			.antMatchers("/FeedBack/**").permitAll()
			.antMatchers("/Event/getAllEvents").permitAll()
			.antMatchers("/FeedBack/addFeedBackToUser/{id}").permitAll()
			.antMatchers("/User/user").permitAll()
			.antMatchers("/chat").permitAll()
			.antMatchers("/showCurrentResult/{id}").permitAll()
			.antMatchers("/Event/**").permitAll()
			.antMatchers("/Category/**").permitAll()
			.antMatchers("/Activity/**").permitAll()
			.antMatchers("/Notification/**").permitAll()
			.antMatchers("/Badge/**").permitAll()
			.antMatchers("/Collaboration/**").permitAll()
			.antMatchers("/Offer/**").permitAll()
			.antMatchers("/Post/**").permitAll()
			.antMatchers("/Comment/**").permitAll()
			.antMatchers("/React/**").permitAll()
			.antMatchers("/Reclamation/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}