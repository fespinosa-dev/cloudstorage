package dev.fespinosa.cloudstorage.services;

import dev.fespinosa.cloudstorage.mappers.CredentialsMapper;
import dev.fespinosa.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    @Autowired
    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredentials(Credentials credentials) {
        String secretKey = encryptionService.generateKey();
        String encryptedPass = encryptionService.encryptValue(credentials.getPassword(), secretKey);
        credentials.setKey(secretKey);
        credentials.setPassword(encryptedPass);
        return credentialsMapper.insert(credentials);
    }

    public void deleteCredentials(Credentials credentials) {
        credentialsMapper.delete(credentials);
    }

    public void updateCredentials(Credentials credentials) {
        credentialsMapper.update(credentials);
    }

    public List<Credentials> getAllCredentialsByUsername(String username) {
        List<Credentials> credentialsByUsername = credentialsMapper.getCredentialsByUsername(username);
        credentialsByUsername.forEach(this::decryptPassword);
        return credentialsByUsername;
    }

    /**
     * Decrypts credentials encrypted password
     *
     * @param credentials object
     */
    private void decryptPassword(Credentials credentials) {
        String encryptedPass = credentials.getPassword();
        String key = credentials.getKey();
        String decryptedPass = encryptionService.decryptValue(encryptedPass, key);
        credentials.setPassword(decryptedPass);
    }
}
