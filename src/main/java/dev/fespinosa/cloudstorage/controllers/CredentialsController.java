package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.Credentials;
import dev.fespinosa.cloudstorage.model.User;
import dev.fespinosa.cloudstorage.services.CredentialService;
import dev.fespinosa.cloudstorage.services.EncryptionService;
import dev.fespinosa.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/credential")
public class CredentialsController {

    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;


    @Autowired
    public CredentialsController(UserService userService,
                                 CredentialService credentialService,
                                 EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }


    @PostMapping("/add")
    public ResponseEntity<Credentials> addCredential(Principal principal, @RequestBody Credentials credentials) {
        ResponseEntity<Credentials> responseEntity = new ResponseEntity<>(credentials, HttpStatus.OK);
        Optional<User> userOpt = userService.findUserByUsername(principal.getName());
        userOpt.ifPresent(u -> credentials.setUserId(u.getUserId()));
        int credentialsCreated = credentialService.createCredentials(credentials);
        if (credentialsCreated <= 0) {
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/delete")
    public ResponseEntity<Credentials> deleteCredentials(@RequestBody Credentials credentials) {
        credentialService.deleteCredentials(credentials);
        return new ResponseEntity<>(credentials, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Credentials> updateCredentials(@RequestBody Credentials credentials) {
        credentialService.updateCredentials(credentials);
        return new ResponseEntity<>(credentials, HttpStatus.OK);
    }


    @GetMapping("/list")
    public String getCredentialsList(Principal principal, Model model) {
        List<Credentials> credentialsByUsername =
                credentialService.getAllCredentialsByUsername(principal.getName());
        model.addAttribute("credentials", credentialsByUsername);
        return "home::credential_list";
    }

}
