package com.sucl.sbjms.core.web;

import com.sucl.sbjms.core.rem.ExceptionUtils;
import com.sucl.sbjms.core.rem.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author sucl
 * @date 2019/4/10
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Message handler(Exception ex){

        return ExceptionUtils.getMessage(ex,messageSource,LocaleContextHolder.getLocale());
    }
}
