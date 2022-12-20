
package  it.gestionRisque.app.auth.service.ServiceImp;
import java.time.LocalDateTime;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.auth.entities.PasswordResetToken;
import it.gestionRisque.app.auth.repository.RestPasswordTokenRepository;
import it.gestionRisque.app.auth.service.ResetPasswordToken;



@Service
@Transactional
public class ForgotPasswordServiceImpl implements ResetPasswordToken{

	@Autowired
	private RestPasswordTokenRepository PasswordRestTokenRepo;

	
	

	@Override
	public PasswordResetToken savePasswordResetToken(PasswordResetToken passwordResetToken) {
		// TODO Auto-generated method stub
	
		PasswordResetToken _passwordResetToken = PasswordRestTokenRepo.save(new PasswordResetToken(passwordResetToken.getId(),passwordResetToken.getToken(),passwordResetToken.getUser(), passwordResetToken.getExpiryDate(),passwordResetToken.getUsed()));

		return _passwordResetToken;
				}
	
	
	
	
	@Override
	public PasswordResetToken findByToken(String token) {
		// TODO Auto-generated method stub
		return PasswordRestTokenRepo.findByToken(token);
	}

	@Override
	public String validatePasswordResetToken(String token) {
		// TODO Auto-generated method stub
		 final PasswordResetToken passToken = PasswordRestTokenRepo.findByToken(token);

		    return !isTokenFound(passToken) ? "invalidToken"
		            : isTokenExpired(passToken) ? "expired"
		            : null;
	}




	@Override
	public boolean isTokenFound(PasswordResetToken passToken) {
		// TODO Auto-generated method stub
		return passToken != null;
	}




	@Override
	public boolean isTokenExpired(PasswordResetToken passToken) {
		// TODO Auto-generated method stub
		 	LocalDateTime tokenExpiryDate = LocalDateTime.from(passToken.getExpiryDate());
		    return LocalDateTime.now().compareTo(tokenExpiryDate) >= 0;
	}



	@Override
	public boolean isTokenUsed(PasswordResetToken passToken) {
		// TODO Auto-generated method stub
		return passToken.getUsed();
	}




	@Override
	public boolean isTokenValidAndNotUsed(String token) {
		// TODO Auto-generated method stub
		if(PasswordRestTokenRepo.checkUserToken(token) != null) {
			return true ;
		}else { 
			return false;
		}
	}

}
