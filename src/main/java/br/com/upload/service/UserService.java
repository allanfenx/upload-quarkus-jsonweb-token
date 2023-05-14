package br.com.upload.service;

import br.com.upload.dto.AuthDto;
import br.com.upload.dto.UserDto;
import br.com.upload.entity.User;
import br.com.upload.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Transactional
    public  String register(UserDto dto){

        User user = new User();

        user.setName(dto.getName());

        user.setEmail(dto.getEmail());

        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));

        repository.persist(user);

        return  "User created success";
    }

    public String auth(AuthDto dto){

       User user = repository.find("email", dto.getEmail()).firstResult();

       if (user == null){
           throw new RuntimeException("Credentials invalid");
       }

        if (BCrypt.checkpw(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("Credentials invalid");
        }

        return  Jwt.issuer(issuer)
                .upn(dto.getEmail())
                .groups("User")
                .sign();
    }
}
