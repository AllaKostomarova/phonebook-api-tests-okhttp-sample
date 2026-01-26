package dto;

//{
//        "timestamp": "2026-01-26T19:37:22.695Z",
//        "status": 0,
//        "error": "string",
//        "message": {},
//        "path": "string"
//        }

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)

public class ErrorMessageResponse {
    private String timestamp;
    private int status;
    private String error;
    private Object message;
    private String path;
}
