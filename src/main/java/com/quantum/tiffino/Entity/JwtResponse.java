package com.quantum.tiffino.Entity;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor

public class JwtResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtResponse(String token) {
        this.token = token;
    }
}
