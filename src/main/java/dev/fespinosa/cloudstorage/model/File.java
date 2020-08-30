package dev.fespinosa.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class File {

    private Integer id;
    private String name;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public static File from(MultipartFile multipartFile) {
        File file = new File();
        try {
            file.setName(multipartFile.getOriginalFilename());
            file.setContentType(multipartFile.getContentType());
            file.setFileSize(String.valueOf(multipartFile.getSize()));
            file.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
