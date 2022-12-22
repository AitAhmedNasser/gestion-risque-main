package  it.gestionRisque.app.auth.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponsePasswordReset {

    private String message;
    private String error;
    
}
