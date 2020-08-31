package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.File;
import dev.fespinosa.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String singleFileUpload(Principal principal,
                                   @RequestParam("fileUpload") MultipartFile multipartFile,
                                   RedirectAttributes redirectAttributes) {
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a multipartFile to upload");
            return "redirect:/home";
        }
        try {
            File file = File.from(multipartFile);
            fileService.saveFile(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getName() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/home";

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
}
