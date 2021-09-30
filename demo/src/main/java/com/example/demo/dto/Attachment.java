package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Attachment {

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("content")
    private String content;

    @JsonProperty("additions")
    private Map<String, String> additions = null;


}
