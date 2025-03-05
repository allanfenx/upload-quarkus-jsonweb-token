package br.com.upload.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.com.upload.entity.Profile;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProfileRepository implements PanacheRepository<Profile> {

}
