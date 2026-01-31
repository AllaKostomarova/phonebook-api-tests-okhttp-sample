package api;

import dto.AuthRequest;
import data.DataApiLocal;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class UserApi {

    private final BaseApi baseApi = new BaseApi();

    /**
     * Builds an AuthRequest DTO.
     */
    public AuthRequest buildAuthDTO(String username, String password) {
        return AuthRequest.builder()
                .username(username)
                .password(password)
                .build();
    }

    /**
     * Builds and sends a POST request (login/registration) with an AuthRequest body.
     * Returns the response (the caller must close it).
     */
    public Response authRequest(String endpoint, String username, String password) throws IOException {
        //1 build the DTO
        AuthRequest userDto = buildAuthDTO(username, password);
        //2 build the request body
        RequestBody requestBody = baseApi.buildRequestBody(userDto);
        //3 build the full URL
        String fullUrl = baseApi.buildFullUrl(endpoint);
        //4 build the POST request
        Request postReq = baseApi.buildPost(fullUrl, requestBody, null);
        //5 send the request and return the response
        return baseApi.sendRequest(postReq);
    }

    /**
     * Builds and sends a login request with an AuthRequest body.
     * Returns the response.
     * The caller must close the response using try-with-resources.
     */
    public Response loginPostRequest(String username, String password) throws IOException {
        return authRequest(DataApiLocal.loginEndpoint, username, password);
    }

    /**
     * Builds and sends a registration request with an AuthRequest body.
     * Returns the response.
     * The caller must close the response using try-with-resources.
     */
    public Response registrationPostRequest(String username, String password) throws IOException {
        return authRequest(DataApiLocal.registrationEndpoint, username, password);
    }

}
