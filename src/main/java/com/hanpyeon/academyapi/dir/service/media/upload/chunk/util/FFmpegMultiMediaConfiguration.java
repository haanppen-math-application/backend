package com.hanpyeon.academyapi.dir.service.media.upload.chunk.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
class FFmpegMultiMediaConfiguration {

    @Bean
    @Conditional(MacConfigurationCondition.class)
    FFMpegProcessLocator macFFMpegProcessLocator() {
        return new FFMpegProcessLocator() {
            @Override
            public String getExecutablePath() {
                return "/opt/homebrew/bin/ffmpeg";
            }
        };
    }

    @Bean
    @Conditional(LinuxConfigurationCondition.class)
    FFMpegProcessLocator linuxFFMpegProcessLocator() {
        return new FFMpegProcessLocator() {
            @Override
            public String getExecutablePath() {
                return "/usr/bin/ffmpeg";
            }
        };
    }
}
