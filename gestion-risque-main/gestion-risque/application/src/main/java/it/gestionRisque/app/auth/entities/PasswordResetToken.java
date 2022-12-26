package it.gestionRisque.app.auth.entities;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter 
@Data
public class PasswordResetToken {
    public PasswordResetToken() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PasswordResetToken(Long id, String token, User user, LocalDateTime expiryDate, Boolean used) {
		super();
		this.id = id;
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
		this.used = used;
	}


	private static final int EXPIRATION = 60 * 24;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    private String token;
    
    @ManyToOne()
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    
    private LocalDateTime expiryDate;
    
    private Boolean used = false;
	

}
