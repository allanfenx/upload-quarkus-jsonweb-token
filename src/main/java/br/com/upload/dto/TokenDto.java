package br.com.upload.dto;

public class TokenDto {

    private String token;

    public TokenDto(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
