package com.sucl.sbjms.core.method.support;

import com.sucl.sbjms.core.method.annotation.QueryOrder;
import com.sucl.sbjms.core.orm.jpa.JpaOrder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;

/**
 * @author sucl
 * @date 2019/4/3
 */
public class OrderHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String ORDER_PROPERTY = "order:property";
    public static final String ORDER_DIRECTION = "order:direction";// ASC DESC

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(QueryOrder.class) &&
                Collection.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String property = nativeWebRequest.getParameter(ORDER_PROPERTY);
        String direction = nativeWebRequest.getParameter(ORDER_DIRECTION);
        return new JpaOrder(property,direction);
    }
}
