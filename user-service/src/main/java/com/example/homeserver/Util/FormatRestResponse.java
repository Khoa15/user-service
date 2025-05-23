package com.example.homeserver.Util;

import com.example.homeserver.DTO.RestResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice{
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;// Override all response
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        if(body instanceof String) {
            return body;
        }

        RestResponse<Object> restResponse = new RestResponse<Object>();

        if(status >= 400){
//            restResponse.setError(body.toString());
//            restResponse.setError("Call API failed");
//            restResponse.setMessage(body);
            return body;
        }else{
            restResponse.setData(body);
            restResponse.setMessage("Call API successfully");
        }

        return restResponse;
    }
}
