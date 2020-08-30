package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.File;
import dev.fespinosa.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

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
}
