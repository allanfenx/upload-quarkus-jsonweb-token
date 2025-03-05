package br.com.upload.entity;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class FormData {

    @RestForm("file")
    private FileUpload file;

    @RestForm("title")
    private String title;

    @RestForm("category")
    private String category;

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public FileUpload getFile() {
        return file;
    }
}
