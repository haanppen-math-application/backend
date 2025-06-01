package com.hpmath.common;

import org.springframework.core.Ordered;

public interface ProxyOrder {
    int ASYNC = Ordered.LOWEST_PRECEDENCE - 2;
    int REQUEST_COLLAPSE_CACHE = Ordered.LOWEST_PRECEDENCE - 1;
    int CACHE = Ordered.LOWEST_PRECEDENCE - 1;
}
