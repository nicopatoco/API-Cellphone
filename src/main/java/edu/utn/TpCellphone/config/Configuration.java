package edu.utn.TpCellphone.config;

import edu.utn.TpCellphone.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.context.annotation.Configuration
@PropertySource("app.properties")
@EnableScheduling
public class Configuration {
    
    @Autowired
    SessionFilter sessionFilter;

    @Bean
    public FilterRegistrationBean adminFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/admin/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean clientFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/client/*");
        return registration;
    }
}
