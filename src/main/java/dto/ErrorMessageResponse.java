package dto;

//{
//        "timestamp": "2026-01-26T19:37:22.695Z",
//        "status": 0,
//        "error": "string",
//        "message": {},
//        "path": "string"
//        }

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)

public class ErrorMessageResponse {
    String timestamp;
    int status;
    String error;
    Object message;
    String path;
}
