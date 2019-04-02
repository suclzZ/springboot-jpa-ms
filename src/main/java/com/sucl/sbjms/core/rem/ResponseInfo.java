package com.sucl.sbjms.core.rem;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Getter
@Setter
public class ResponseInfo {
    private HttpStatus status;
    private Object result;
}
