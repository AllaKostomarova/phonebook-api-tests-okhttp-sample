package api;

import com.google.gson.Gson;
import data.DataApi;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;

import static api.ApiDebugUtil.saveToFile;

/**
 * Base helper for API requests:
 * - object serialization to JSON request bodies,
 * - building simple GET/POST/PUT/DELETE requests,
 * - sending requests,
 * - parsing JSON responses into DTOs.
 * <p>
 * IMPORTANT: Close the response in the calling code using try-with-resources.
 */

public class BaseApi {

    /**
     * Media type for JSON payloads.
     */
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    /**
     * JSON <-> object converter (Gson).
     */
    private static final Gson GSON = new Gson();
    /**
     * HTTP client (OkHttp).
     */
    private static final OkHttpClient CLIENT = new OkHttpClient();
    /**
     * Base URL for the current environment.
     */
    private static final String BASE_URL = DataApi.BASEURL;

    /**
     * Concatenates the base URL with an endpoint path (e.g., "/endpoint").
     * Note: This method does not normalize slashes.
     */
    public String buildFullUrl(String endpoint) {
        return BASE_URL + endpoint;
    }


    /**
     * Serializes an object to JSON and wraps it in a RequestBody.
     */
    public RequestBody buildRequestBody(Object object) {
        return RequestBody.create(GSON.toJson(object), JSON);
    }


    /**
     * Sends an HTTP request and returns the response.
     * The caller must close the response: try (Response r = sendRequest(req)) { ... }
     */
    public Response sendRequest(Request request) throws IOException {
        return CLIENT.newCall(request).execute();
    }

    /**
     * Reads the response body.
     * IMPORTANT: The body can be read only once.
     *
     * @return the response body as a JSON string.
     * @throws IOException              if an I/O error occurs while reading the body.
     * @throws IllegalArgumentException if the response is null.
     * @throws IllegalStateException    if the response body is null/empty, not JSON,
     *                                  or if the response is a server error (5xx).
     */
    public String getResponseBody(Response res) throws IOException {

        if (res == null)
            throw new IllegalArgumentException("Response is null");

        ResponseBody body = res.body();

        if (body == null)
            throw new IllegalStateException("Response body is null (expected JSON)");

        String bodyStr = body.string();

        // If the response is a server error (5xx), dump the body and throw an exception.
        if (res.code() >= 500) {
            String filePath = saveToFile("server_error_" + res.code(), bodyStr);
            throw new IllegalStateException("Server error " + res.code() + ". Body saved to: " + filePath);
        }

        if (bodyStr.isBlank())
            throw new IllegalStateException("Response body is empty (expected JSON)");

        // Validate that the body looks like JSON (object or array).
        String str = bodyStr.strip();
        if (!(str.startsWith("{") || str.startsWith("["))) {
            throw new IllegalStateException("Response body is not JSON (code " + res.code() + "). Body: " + bodyStr);
        }

        return bodyStr;
    }

    /**
     * Parses a JSON string into the specified class (non-generic types).
     */
    public <T> T parseResponseBody(String json, Class<T> cls) {
        if (json == null || json.isBlank())
            throw new IllegalArgumentException("JSON is null or empty");
        return GSON.fromJson(json, cls);
    }

    /**
     * Parses a JSON string into a generic type (e.g., List<Contact> etc.).
     * Use new TypeToken<...>(){}.getType().
     */
    public <T> T parseResponseBody(String json, Type type) {
        if (json == null || json.isBlank())
            throw new IllegalArgumentException("JSON is null or empty");
        return GSON.fromJson(json, type);
    }


    /**
     * Builds a POST request.
     *
     * @param token the Authorization header value, or null if the header should not be added.
     */
    public Request buildPost(String url, RequestBody body, @Nullable String token) {
        Request.Builder b = new Request.Builder()
                .url(url)
                .post(body);
        if (token != null)
            b.addHeader("Authorization", token);
        return b.build();
    }

    /**
     * Builds a GET request.
     *
     * @param token the Authorization header value, or null if the header should not be added.
     */
    public Request buildGet(String url, @Nullable String token) {
        Request.Builder b = new Request.Builder()
                .url(url)
                .get();
        if (token != null)
            b.addHeader("Authorization", token);
        return b.build();
    }

    /**
     * Builds a PUT request.
     *
     * @param token the required Authorization header value.
     */
    public Request buildPut(String url, RequestBody body, String token) {
        Request.Builder b = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Authorization", token);
        return b.build();
    }


    /**
     * Builds a DELETE request.
     *
     * @param token the required Authorization header value.
     */
    public Request buildDelete(String url, String token) {
        Request.Builder b = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("Authorization", token);
        return b.build();
    }

}
