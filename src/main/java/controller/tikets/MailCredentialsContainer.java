package controller.tikets;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.utils.FileUtils;
import org.apache.log4j.Logger;

import javax.inject.Inject;

public class MailCredentialsContainer {

    private final ObjectMapper objectMapper;
    private final FileUtils fileUtils;
    private final MailCredentials mailCredentials;

    final private static Logger logger = Logger.getLogger(MailCredentialsContainer.class);

    @Inject
    public MailCredentialsContainer(ObjectMapper objectMapper, FileUtils fileUtils) {
        this.objectMapper = objectMapper;
        this.fileUtils = fileUtils;
        this.mailCredentials = this.loadCredentials();
    }

    private MailCredentials loadCredentials() {
        MailCredentials mailCredentials;
        try {
            mailCredentials = objectMapper.readValue(this.fileUtils.getFiles("mail-credentials.json"), MailCredentials.class);

        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e);
        }
        return mailCredentials;
    }

    public MailCredentials getMailCredentials() {
        return mailCredentials;
    }
}
