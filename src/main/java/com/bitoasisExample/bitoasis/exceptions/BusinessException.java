package com.bitoasisExample.bitoasis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends Exception{

    private static final long serialVersionUID =1L;

    private String errorCode;
    private String errorMessage;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessException that = (BusinessException) o;
        return Objects.equals(errorCode, that.errorCode) && Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorMessage);
    }
}
