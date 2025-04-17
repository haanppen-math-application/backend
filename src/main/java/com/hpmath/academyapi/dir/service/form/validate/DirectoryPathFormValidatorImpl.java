package com.hpmath.academyapi.dir.service.form.validate;

import com.hpmath.academyapi.dir.exception.DirectoryException;
import com.hpmath.academyapi.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
class DirectoryPathFormValidatorImpl implements DirectoryPathFormValidator {
    @Override
    public void validateDirPath(String dirPath) {
        if (dirPath.isBlank()) {
            throw new DirectoryException(dirPath + " : 는 빌 수 없습니다", ErrorCode.ILLEGAL_PATH);
        }
        if (dirPath.contains("//")) {
            throw new DirectoryException(dirPath + " : 중복된 // 존재", ErrorCode.ILLEGAL_PATH);
        }
    }
}
