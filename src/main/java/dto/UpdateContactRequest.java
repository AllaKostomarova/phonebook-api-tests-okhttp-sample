package dto;

//{
//        "id": "string",
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

public class UpdateContactRequest {
    int id;
    String name;
    String lastName;
    String email;
    String phone;
    String address;
    String description;
}
