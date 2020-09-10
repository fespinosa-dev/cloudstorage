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

    private final String CREDENTIAL_ADDED_MSG = "\"Credential successfully added!\"";
    private final String CREDENTIAL_DELETED_MSG = "\"Credential successfully deleted!\"";
    private final String CREDENTIAL_UPDATED_MSG = "\"Credential successfully updated!\"";
    private final String CREDENTIAL_ERROR_MSG = "\"There was an error adding the credential!\"";


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
    public ResponseEntity<String> addCredential(Principal principal, @RequestBody Credentials credentials) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(CREDENTIAL_ADDED_MSG, HttpStatus.OK);
        Optional<User> userOpt = userService.findUserByUsername(principal.getName());
        userOpt.ifPresent(u -> credentials.setUserId(u.getUserId()));
        int credentialsCreated = credentialService.createCredentials(credentials);
        if (credentialsCreated <= 0) {
            responseEntity = new ResponseEntity<>(CREDENTIAL_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteCredentials(@RequestBody Credentials credentials) {
        credentialService.deleteCredentials(credentials);
        return new ResponseEntity<>(CREDENTIAL_DELETED_MSG, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCredentials(@RequestBody Credentials credentials) {
        Optional<Credentials> credentialsOpt =
                credentialService.findCredentialById(credentials.getId());
        credentialsOpt.ifPresent(c -> credentials.setKey(c.getKey()));
        credentialService.updateCredentials(credentials);
        return new ResponseEntity<>(CREDENTIAL_UPDATED_MSG, HttpStatus.OK);
    }


    @GetMapping("/list")
    public String getCredentialsList(Principal principal, Model model) {
        List<Credentials> credentialsByUsername =
                credentialService.getAllCredentialsByUsername(principal.getName());
        model.addAttribute("credentials", credentialsByUsername);
        return "home::credential_list";
    }


    @PostMapping("/decrypt")
    public ResponseEntity<String> getDecryptedPassword(@RequestBody Credentials credentials) {
        String decryptedPassword = "";
        var response = new ResponseEntity<>(decryptedPassword, HttpStatus.INTERNAL_SERVER_ERROR);
        Optional<Credentials> credentialByIdOpt =
                credentialService.findCredentialById(credentials.getId());
        if (credentialByIdOpt.isPresent()) {
            String key = credentialByIdOpt.get().getKey();
            decryptedPassword = encryptionService.decryptValue(credentials.getPassword(),
                    credentialByIdOpt.get().getKey());

        }
        return new ResponseEntity<>("\"" + decryptedPassword + "\"", HttpStatus.OK);
    }

}
