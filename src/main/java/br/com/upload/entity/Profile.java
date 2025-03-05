package br.com.upload.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;

    private String KeyName;

    private String title;

    private String category;

    private String mimetype;

    private Date created_at;

    private Long filesize;

    public Long getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getKeyName() {
        return KeyName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getMimetype() {
        return mimetype;
    }

    public Long getFilesize() {
        return filesize;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setKeyName(String keyName) {
        KeyName = keyName;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
