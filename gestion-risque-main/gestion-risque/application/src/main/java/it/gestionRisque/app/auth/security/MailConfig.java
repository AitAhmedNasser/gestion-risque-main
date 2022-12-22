package it.gestionRisque.app.auth.security;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@ComponentScan({"com.baeldung.freemarker"})
public class MailConfig {
@Primary
@Bean
public FreeMarkerConfigurationFactoryBean factoryBean()
{
FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
freeMarkerConfigurationFactoryBean.setTemplateLoaderPath("classpath:/templates/");

return freeMarkerConfigurationFactoryBean;
}


@Bean
public SpringTemplateEngine springTemplateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addTemplateResolver(htmlTemplateResolver());
    return templateEngine;
}
@Bean
public SpringResourceTemplateResolver htmlTemplateResolver(){
    SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
    emailTemplateResolver.setPrefix("classpath:/templates/");
    emailTemplateResolver.setPrefix(""); 
    emailTemplateResolver.setSuffix(".ftl"); 
    emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
    emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    return emailTemplateResolver;
}

}