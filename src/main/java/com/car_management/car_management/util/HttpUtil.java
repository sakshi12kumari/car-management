package com.car_management.car_management.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class HttpUtil {

    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            if (attr != null) {
                return attr.getRequest();
            }
        } catch (IllegalStateException e) {
            log.trace("Method is not called from withing a web request.");
        }
        return null;
    }

    public static String getHeader(String header) {
        HttpServletRequest request = HttpUtil.getRequest();
        if (request == null)
            return null;
        return request.getHeader(header);
    }
}
