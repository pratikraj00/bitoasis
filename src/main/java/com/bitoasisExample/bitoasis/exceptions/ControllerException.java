package com.bitoasisExample.bitoasis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ControllerException extends Exception{

    private static final long serialVersionUID =1L;

    private String errorCode;
    private String errorMessage;
}
