package com.hpmath.client.common.aspect;

import com.hpmath.client.common.ClientBadRequestException;
import com.hpmath.client.common.ClientException;
import com.hpmath.client.common.ClientRequestTimeoutException;
import com.hpmath.client.common.ClientServerException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

@Aspect
@Slf4j
@Component
public class ClientExceptionAspect {
    @AfterThrowing(value = "@within(Client)", throwing = "throwable")
    public void mapException(final Throwable throwable) {
        if (throwable instanceof HttpStatusCodeException) {
            final HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) throwable;

            log.error("HTTP Status Code: {}", httpStatusCodeException.getStatusCode(), httpStatusCodeException);
            if (httpStatusCodeException.getStatusCode().is4xxClientError()) {
                throw new ClientBadRequestException(httpStatusCodeException.getMessage(), httpStatusCodeException.getCause());
            } else if (httpStatusCodeException.getStatusCode().is5xxServerError()) {
                throw new ClientServerException(httpStatusCodeException.getMessage(), httpStatusCodeException.getCause());
            } else {
                throw httpStatusCodeException;
            }
        }

        if (throwable instanceof ResourceAccessException) {
            log.error("Resource access timeout exception: ", throwable);
            throw new ClientRequestTimeoutException(throwable.getMessage(), throwable);
        }

        throw new ClientException(throwable.getMessage());
    }
}
