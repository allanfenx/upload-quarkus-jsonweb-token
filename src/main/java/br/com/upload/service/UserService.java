package br.com.upload.service;

import br.com.upload.dto.AuthDto;
import br.com.upload.dto.UserDto;
import br.com.upload.entity.User;
import br.com.upload.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;

import java.util.HashMap;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Transactional
    public String register(UserDto dto) {

        User user = new User();

        user.setName(dto.getName());

        user.setEmail(dto.getEmail());

        user.setPassword(BcryptUtil.bcryptHash(dto.getPassword(), 10));

        repository.persist(user);

        return "User created success";
    }

    public HashMap<String, String> auth(AuthDto dto) {

        User user = repository.find("email", dto.getEmail()).firstResult();

        if (user == null) {
            throw new RuntimeException("Credentials invalid");
        }

        if (!BcryptUtil.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credentials invalid");
        }

        String token = Jwt.issuer(issuer)
                .subject(dto.getEmail())
                .groups("User")
                .expiresIn(60 * 60 * 2)
                .sign();

        HashMap<String, String> map = new HashMap<>();

        map.put("token", token);

        return map;
    }
}
