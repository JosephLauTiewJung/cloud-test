package org.example.springaidemo.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseModel {
    private String content;
    private HttpStatus status;
}
