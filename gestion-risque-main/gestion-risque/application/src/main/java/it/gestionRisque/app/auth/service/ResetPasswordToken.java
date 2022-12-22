package it.gestionRisque.app.auth.service;

import it.gestionRisque.app.auth.entities.PasswordResetToken;

public interface ResetPasswordToken {

	
	
	public PasswordResetToken findByToken(String token);
	
	public PasswordResetToken savePasswordResetToken(PasswordResetToken passwordResetToken);
	
	public String validatePasswordResetToken(String token);
	
	public boolean isTokenFound(PasswordResetToken passToken);
	
	public boolean isTokenExpired(PasswordResetToken passToken);
	

	public boolean isTokenUsed(PasswordResetToken passToken);

	
	public boolean isTokenValidAndNotUsed(String token);
}
