package br.com.kevinaryel.bibliosystem.utils.validate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "br.com.kevinaryel.bibliosystem.utils.validate")
public class ValidateConfig {

    @Bean
    public Validate validateUtils() {
        return new Validate();
    }
}
