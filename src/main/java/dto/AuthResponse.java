package dto;
//{
//        "token": "string"
//        }

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)

public class AuthResponse {
    private String token;
}
