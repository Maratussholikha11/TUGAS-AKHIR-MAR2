package tugasakhir.pemesanan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import tugasakhir.pemesanan.service.UserDetailService;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
//        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/product/findById").hasAnyRole("CUSTOMER","ADMIN","OWNER")
                .antMatchers("/product/show").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/product/create").hasAnyRole("ADMIN","OWNER")
                .antMatchers("/product/update").hasAnyRole("ADMIN","OWNER")
                .antMatchers("/product/allproduct").permitAll()
                .antMatchers("/product/detail/{id}").permitAll()

                .antMatchers("/sale/**").hasAnyRole("ADMIN","OWNER")

                .antMatchers("/role/**").hasAnyRole("ADMIN","OWNER")

                .antMatchers("/ordering/order").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/ordering/createorder").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/ordering/show").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/ordering/myOrder").hasRole("CUSTOMER")

                .antMatchers("/transaction/form").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/transaction/create").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/transaction/show").hasAnyRole("ADMIN","CUSTOMER","OWNER")
                .antMatchers("/ordering/mytransaction").hasAnyRole("ADMIN","CUSTOMER","OWNER")

                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/user/changepassword").permitAll()

                .antMatchers("/login").permitAll()

                .antMatchers("/static/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/templates/**").permitAll()
                .antMatchers("/package tugasakhir.pemesanan.model;/**").permitAll()
                .antMatchers("/webapp/resources/static/upload").permitAll()
                .antMatchers("/persadakonveksi").permitAll()
                //.anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");

//                .and().httpBasic();

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userDetailService);
        return provider;
    }


    // script di bawah ini untuk memeberitahu implemetasi dari appUSerServices / user details
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());
    }
}
