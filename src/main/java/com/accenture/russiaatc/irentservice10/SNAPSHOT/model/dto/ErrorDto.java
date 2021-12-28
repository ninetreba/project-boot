package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private String ErrorCode;
    private String message;
}
