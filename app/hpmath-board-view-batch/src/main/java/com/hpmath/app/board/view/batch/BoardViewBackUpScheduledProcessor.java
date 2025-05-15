package com.hpmath.app.board.view.batch;

import com.hpmath.domain.board.view.BoardViewBackUpProcessor;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardViewBackUpScheduledProcessor {
    private final BoardViewBackUpProcessor boardViewBackUpProcessor;
    @Scheduled(scheduler = "threadPoolExecutor", fixedDelay = 30L, initialDelay = 30L, timeUnit = TimeUnit.SECONDS)
    public void backUp() {
        log.info("Backing up board views");

        boardViewBackUpProcessor.backUpAllEntries();

        log.info("Finished backing up board views");
    }
}
