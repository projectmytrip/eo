package it.reply.compliance.gdpr.util.batch.client.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Builder;
import lombok.Data;

@Data
public class MailRequest {

    private String to;
    private String cc;
    private String bcc;
    private String type;
    private String text;
    private boolean customText;
    private List<String> attachments = new ArrayList<>();
    private Map<String, Object> model;

    @JsonSetter("attachments")
    public void setAttachments(List<String> attachments) {
        if (attachments != null) {
            this.attachments = attachments;
        }
    }
}
