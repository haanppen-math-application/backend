package com.hanpyeon.academyapi.dir.service.media.upload.chunk.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
class MacConfigurationCondition implements ConfigurationCondition {
    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        log.info(context.getEnvironment().getProperty("os.name").toLowerCase());
        return context.getEnvironment().getProperty("os.name").toLowerCase().contains("mac");
    }
}
