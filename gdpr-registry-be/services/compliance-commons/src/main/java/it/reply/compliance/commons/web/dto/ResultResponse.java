package it.reply.compliance.commons.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    public static final ResultResponse Ok = new ResultResponse("Ok");

    @JsonProperty("response")
    private String result;
}
