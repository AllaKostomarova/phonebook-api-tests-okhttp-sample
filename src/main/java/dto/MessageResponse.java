package dto;

//{
//        "message": "string"
//        }

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)

public class MessageResponse {
    String message;
}
