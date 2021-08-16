package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailPostRequestBody {
    @JsonProperty("to")
    private List<String> to = new ArrayList<>();

    @JsonProperty("cc")
    private List<String> cc = null;

    @JsonProperty("bcc")
    private List<String> bcc = null;

    @JsonProperty("from")
    private  String from;

    @JsonProperty("body")
    private  String body;

    @JsonProperty("subject")
    private  String subject;

    @JsonProperty("important")
    private  Boolean important = false;

    @JsonProperty("attachments")
    private List<Attachment> attachments = null;

    @JsonProperty("additions")
    private Map<String, String> additions = null;

}
