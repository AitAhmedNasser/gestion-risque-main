package it.gestionRisque.app.auth.entities;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
	private String from;
    private String to;
    private String subject;
    private String Content;
    private Map<String, Object> model;
}
