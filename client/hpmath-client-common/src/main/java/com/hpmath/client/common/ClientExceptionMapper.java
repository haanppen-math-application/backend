package com.hpmath.client.common;

import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

@Component
@Slf4j
public final class ClientExceptionMapper {
    public <T> T mapException(final Supplier<T> supplier) {
        try {
            return supplier.get();

        } catch (final HttpStatusCodeException ex) {
            log.error(ex.getMessage(), ex);
            if (ex.getStatusCode().is4xxClientError()) {
                throw new ClientBadRequestException(ex.getResponseBodyAsString());
            } else if (ex.getStatusCode().is5xxServerError()) {
                throw new ClientServerException(ex.getResponseBodyAsString());
            } else {
                throw ex;
            }

        } catch (final ResourceAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new ClientRequestTimeoutException(ex.getMessage(), ex);
        }
    }
}
