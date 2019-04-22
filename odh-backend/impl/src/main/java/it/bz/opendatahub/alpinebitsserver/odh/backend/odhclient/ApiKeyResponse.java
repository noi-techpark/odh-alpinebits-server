package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple response class to fetch the API token.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiKeyResponse {

    @JsonProperty("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "ApiKeyResponse{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}
