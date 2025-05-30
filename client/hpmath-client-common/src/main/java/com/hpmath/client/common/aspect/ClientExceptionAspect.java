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
        log.error(throwable.getMessage(), throwable);

        if (throwable instanceof HttpStatusCodeException) {
            final HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) throwable;

            if (httpStatusCodeException.getStatusCode().is4xxClientError()) {
                throw new ClientBadRequestException(httpStatusCodeException.getResponseBodyAsString());
            } else if (httpStatusCodeException.getStatusCode().is5xxServerError()) {
                throw new ClientServerException(httpStatusCodeException.getResponseBodyAsString());
            } else {
                throw httpStatusCodeException;
            }
        }

        if (throwable instanceof ResourceAccessException) {
            throw new ClientRequestTimeoutException(throwable.getMessage(), throwable);
        }

        throw new ClientException(throwable.getMessage());
    }
}
