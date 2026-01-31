package dto;

//{
//        "name": "string",
//        "lastName": "string",
//        "email": "string",
//        "phone": "67620101811540",
//        "address": "string",
//        "description": "string"
//        }

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)

public class NewContactRequest {
    String name;
    String lastName;
    String email;
    String phone;
    String address;
    String description;
}
