package dto;
//{
//        "username": "string",
//        "password": "KYY$j-[Uc(W(r)7Z+IhOm_[11Udun7h4wk~\\7\"n_e&qV/`+yWdN5g|`u158.5hf3ByI.NH"
//        }

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)

public class AuthRequest {
    private String username;
    private String password;

}
