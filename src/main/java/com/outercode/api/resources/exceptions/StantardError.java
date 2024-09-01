package com.outercode.api.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
@AllArgsConstructor
public class StantardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;


}
