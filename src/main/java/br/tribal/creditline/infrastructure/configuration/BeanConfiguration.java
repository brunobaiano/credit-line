package br.tribal.creditline.infrastructure.configuration;

import br.tribal.creditline.CreditLineApplication;
import br.tribal.creditline.domain.service.CreditLineService;
import br.tribal.creditline.domain.service.TribalCreditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CreditLineApplication.class)
public class BeanConfiguration {

    @Bean
    CreditLineService creditLineService(){
        return new TribalCreditService();
    }
}
