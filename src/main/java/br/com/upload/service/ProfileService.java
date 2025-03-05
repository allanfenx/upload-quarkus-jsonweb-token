package br.com.upload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import br.com.upload.entity.FormData;
import br.com.upload.entity.Profile;
import br.com.upload.repository.ProfileRepository;

@ApplicationScoped
public class ProfileService {

    String directory = System.getProperty("user.dir") + "/src/main/resources/META-INF/resources/uploads/";

    @Inject
    ProfileRepository repository;

    public List<Profile> listUploads() {
        return repository.listAll();
    }

    public Profile findOne(Long id) {

        Optional<Profile> profileOp = repository.findByIdOptional(id);

        if (profileOp.isEmpty()) {
            throw new RuntimeException("File not found");
        }

        Profile profile = profileOp.get();

        return profile;
    }

    @Transactional
    public Profile sendUpload(FormData data) throws IOException {

        List<String> mimetype = Arrays.asList("image/jpg", "image/jpeg", "image/gif", "image/png");

        if (!mimetype.contains(data.getFile().contentType())) {
            throw new IOException("File not suported");
        }

        if (data.getFile().size() > 1024 * 1024 * 4) {
            throw new IOException("File much large");
        }

        Profile profile = new Profile();

        String fileName = UUID.randomUUID() + "-" + data.getFile().fileName();

        profile.setOriginalName(data.getFile().fileName());

        profile.setTitle(data.getTitle());

        profile.setCategory(data.getCategory());

        profile.setKeyName(fileName);

        profile.setMimetype(data.getFile().contentType());

        profile.setFilesize(data.getFile().size());

        profile.setCreated_at(new Date());

        repository.persist(profile);

        Files.copy(data.getFile().filePath(), Paths.get(directory + fileName));

        return profile;
    }

    @Transactional
    public void removeUpload(Long id) throws IOException {

        Optional<Profile> profileOp = repository.findByIdOptional(id);

        if (profileOp.isEmpty()) {
            throw new IOException("File not found");
        }

        Profile profile = profileOp.get();

        repository.delete(profile);

        Files.deleteIfExists(Paths.get(directory + profile.getKeyName()));
    }
}
