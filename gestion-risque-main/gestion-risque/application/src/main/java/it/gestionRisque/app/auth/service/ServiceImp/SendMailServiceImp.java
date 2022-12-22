
package  it.gestionRisque.app.auth.service.ServiceImp;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.spring5.SpringTemplateEngine;

import freemarker.template.Configuration;
import it.gestionRisque.app.auth.entities.Mail;
import it.gestionRisque.app.auth.service.MailService;


@Service
@Transactional
public class SendMailServiceImp  implements MailService{

    
	@Autowired(required = true)
	JavaMailSender emailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;

	@Autowired
	private Configuration config;

	@Override
	 public void send(Mail mail) {

		 MimeMessage mimeMessage =emailSender.createMimeMessage();
	        try {
	 
	            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
	 
	            mimeMessageHelper.setSubject(mail.getSubject());
	            mimeMessageHelper.setFrom(mail.getFrom());
	            mimeMessageHelper.setTo(mail.getTo());
	             mail.setContent(geContentFromTemplate(mail.getModel()));
	            mimeMessageHelper.setText(mail.getContent(), true);
	 
	            emailSender.send(mimeMessageHelper.getMimeMessage());
	            
	            
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	        
	        
	    }
	 
	    public String geContentFromTemplate(Map < String, Object >model)     { 
	        StringBuffer content = new StringBuffer();
	 
	        try {
	            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(config.getTemplate("TemplateEmail.ftl"), model));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return content.toString();
	    }
	 }


	


