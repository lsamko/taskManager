package com.example.demo.gmail;

import static com.example.demo.gmail.Base64Utils.decodeBase64;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import com.example.demo.dto.Attachment;
import com.example.demo.dto.EmailPostRequestBody;
import com.example.demo.exception.EmailBadRequestException;
import com.example.demo.notification.Notification;
import com.example.demo.notification.NotificationSender;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmailService implements NotificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmailService.class);

    private static final boolean MULTIPART_MODE = true;
    private static final boolean HTML = true;
    private final JavaMailSender javaMailSender;
    private final DefaultMailSenderProperties defaultMailSenderProperties;

    @Autowired
    public DefaultEmailService(DefaultMailSenderProperties defaultMailSenderProperties, JavaMailSender javaMailSender) {
        this.defaultMailSenderProperties = defaultMailSenderProperties;
        this.javaMailSender = javaMailSender;
    }


    private void sendEmail(EmailPostRequestBody emailPostRequestBody) {
        MimeMessage mimeMessage;

        try {
            mimeMessage = convertToMimeMessage(emailPostRequestBody);
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.info("Failed to convert request body to email {}", emailPostRequestBody);
            throw new EmailBadRequestException(e);
        }

        javaMailSender.send(mimeMessage);
    }

    @Override
    public void send(String body) {
        EmailPostRequestBody emailPostRequestBody = new EmailPostRequestBody();
        emailPostRequestBody.setFrom("samkoliudmyla@gmail.com");
        emailPostRequestBody.setTo(Collections.singletonList("samkoliudmyla@gmail.com"));
        emailPostRequestBody.setBody(body);
        emailPostRequestBody.setSubject("Task reminder");

        sendEmail(emailPostRequestBody);

    }

    @Override
    public Notification getNotificationType() {
        return Notification.EMAIL;
    }


    private MimeMessage convertToMimeMessage(EmailPostRequestBody emailPostRequestBody)
        throws MessagingException, UnsupportedEncodingException {
        final MimeMailMessage mimeMailMessage = new MimeMailMessage(javaMailSender.createMimeMessage());
        final MimeMessage mimeMessage = mimeMailMessage.getMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE);

        setEmailAddresses(messageHelper::setTo, emailPostRequestBody.getTo());
        setEmailAddresses(messageHelper::setCc, emailPostRequestBody.getCc());
        setEmailAddresses(messageHelper::setBcc, emailPostRequestBody.getBcc());
        //   messageHelper.setText(decodeBase64(emailPostRequestBody.getBody()), HTML);
        messageHelper.setText(emailPostRequestBody.getBody(), HTML);
        messageHelper.setPriority(EmailPriority.getPriority(emailPostRequestBody.getImportant()));
        messageHelper.setSubject(emailPostRequestBody.getSubject());
        setFromAddress(emailPostRequestBody.getFrom(), messageHelper);

        setAttachments(messageHelper, emailPostRequestBody.getAttachments());

        return mimeMessage;
    }

    private void setFromAddress(String from, MimeMessageHelper messageHelper)
        throws MessagingException, UnsupportedEncodingException {
        if (nonNull(from)) {
            messageHelper.setFrom(from);
        } else {
            messageHelper
                .setFrom(defaultMailSenderProperties.getFromAddress(), defaultMailSenderProperties.getFromName());
        }
    }

    private void setEmailAddresses(EmailsConsumer emailsConsumer, List<String> emails) throws MessagingException {
        if (isNotEmpty(emails)) {
            emailsConsumer.acceptEmails(emails.toArray(new String[0]));
        }
    }

    private void setAttachments(MimeMessageHelper mimeMessageHelper, List<Attachment> attachments) {
        if (isNotEmpty(attachments)) {
            attachments.forEach(addAttachmentToEmail(mimeMessageHelper));
        }
    }

    private Consumer<Attachment> addAttachmentToEmail(MimeMessageHelper mimeMessageHelper) {
        return attachment -> {
            try {

                mimeMessageHelper.addAttachment(attachment.getFileName(),
                    () -> getAttachmentAsInputStream(attachment.getContent()));

            } catch (MessagingException e) {
                LOGGER.info("Failed to add file as attachment {}", attachment.getFileName());
                throw new EmailBadRequestException(e);
            }
        };
    }

    private ByteArrayInputStream getAttachmentAsInputStream(String attachmentContent) {
        final byte[] bytes = Base64.decodeBase64(attachmentContent);
        return new ByteArrayInputStream(bytes);
    }
}
