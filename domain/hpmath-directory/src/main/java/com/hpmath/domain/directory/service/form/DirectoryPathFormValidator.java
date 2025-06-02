package com.hpmath.domain.directory.service.form;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.directory.exception.DirectoryException;
import org.springframework.stereotype.Component;

@Component
public class DirectoryPathFormValidator {
    public void validateDirPath(String dirPath) {
        if (dirPath.isBlank()) {
            throw new DirectoryException(dirPath + " : 는 빌 수 없습니다", ErrorCode.ILLEGAL_PATH);
        }
        if (dirPath.contains("//")) {
            throw new DirectoryException(dirPath + " : 중복된 // 존재", ErrorCode.ILLEGAL_PATH);
        }
    }
}
