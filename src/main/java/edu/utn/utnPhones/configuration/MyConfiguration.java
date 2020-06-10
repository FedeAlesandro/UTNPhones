package edu.utn.utnPhones.configuration;

import edu.utn.utnPhones.session.EmployeeSessionFilter;
import edu.utn.utnPhones.session.InfrastructureSessionFilter;
import edu.utn.utnPhones.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class MyConfiguration {

    @Autowired
    SessionFilter sessionFilter;

    @Autowired
    EmployeeSessionFilter employeeSessionFilter;

    @Autowired
    InfrastructureSessionFilter infrastructureSessionFilter;

    @Bean
    public FilterRegistrationBean mySessionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean myEmployeeSessionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(employeeSessionFilter);
        registration.addUrlPatterns("/employee/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean myInfrastructureSessionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(infrastructureSessionFilter);
        registration.addUrlPatterns("/infrastructure/*");
        return registration;
    }
}
