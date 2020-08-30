package dev.fespinosa.cloudstorage.services;

import dev.fespinosa.cloudstorage.mappers.FileMapper;
import dev.fespinosa.cloudstorage.model.File;
import dev.fespinosa.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

@Service
public class FileService {

    private final String UPLOADED_FOLDER = System.getProperty("user.home");

    private FileMapper fileMapper;
    private UserService userService;

    @Autowired
    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public int saveFile(File file) throws IOException {
        Principal principal = getPrincipal();
        Optional<User> userByUsernameOpt = userService.findUserByUsername(principal.getName());
        userByUsernameOpt.ifPresent(u -> file.setUserId(u.getUserId()));
        Path fileStoragePath = getFileStoragePath(file.getName());
        createDirectoriesIfNotExist(fileStoragePath);
        Files.write(fileStoragePath, file.getContent());
        return fileMapper.insert(file);

    }

    private Path getFileStoragePath(String fileName) {
        Principal principal = getPrincipal();
        return Paths.get(UPLOADED_FOLDER
                + java.io.File.separator + "cloud-storage" +
                java.io.File.separator + principal.getName() +
                java.io.File.separator + fileName);

    }

    private Principal getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private void createDirectoriesIfNotExist(Path fileStoragePath) throws IOException {
        Path parentDir = fileStoragePath.getParent();
        if (!Files.exists(parentDir))
            Files.createDirectories(parentDir);
    }
}
