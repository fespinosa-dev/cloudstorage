package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.File;
import dev.fespinosa.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/file")
public class FileController {

    private final String FILE_UPLOAD_MSG = "File was successfully uploaded!";
    private final String FILE_ERROR_MSG = "\"There was an error uploading the file!\"";
    private final String FILE_DELETED_MSG = "\"File was successfully deleted!\"";
    private final String FILE_NO_SELECTED_MSG = "\"Please select a multipartFile to upload\"";
    private final String FILE_ALREADY_EXISTS_MSG = "You already uploaded this file.";


    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> singleFileUpload(Principal principal,
                                                   @RequestParam("fileUpload") MultipartFile multipartFile) {
        var response = new ResponseEntity<>(FILE_UPLOAD_MSG, HttpStatus.OK);
        if (multipartFile.isEmpty()) {
            response = new ResponseEntity<>(FILE_NO_SELECTED_MSG, HttpStatus.BAD_REQUEST);
        }
        try {
            File file = File.from(multipartFile);
            fileService.saveFile(file);
        } catch (FileAlreadyExistsException e) {
            response = new ResponseEntity<>(FILE_ALREADY_EXISTS_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            response = new ResponseEntity<>(FILE_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }

    @RequestMapping("/download/{fileName:.+}")
    public void downloadPDFResource(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable("fileName") String fileName) {
        Optional<File> fileOpt = fileService.getFileByName(fileName);

        Optional<Path> fileStoragePathOpt = fileOpt
                .map(File::getName)
                .map(fileService::getFileStoragePath);

        if (fileStoragePathOpt.isPresent()) {
            try {
                response.setContentType(fileOpt.get().getContentType());
                response.addHeader("Content-Disposition", "attachment; filename=" + fileOpt.get().getName());
                Files.copy(fileStoragePathOpt.get(), response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteNote(@RequestBody File file) {
        fileService.deleteFile(file);
        return new ResponseEntity<>(FILE_DELETED_MSG, HttpStatus.OK);
    }

    @GetMapping("/list")
    public String getNoteList(Principal principal, Model model) {
        List<File> filesByUsername = fileService.getAllFilesByUsername(principal.getName());
        model.addAttribute("files", filesByUsername);
        return "home::file_list";
    }
}
