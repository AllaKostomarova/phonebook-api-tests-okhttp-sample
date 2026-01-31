package dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)

public class ContactsResponse {
    @Singular("contact")
    List<UpdateContactRequest> contacts;
}
