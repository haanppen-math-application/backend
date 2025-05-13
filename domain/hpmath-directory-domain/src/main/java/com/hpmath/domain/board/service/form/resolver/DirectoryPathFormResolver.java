package com.hpmath.domain.board.service.form.resolver;

public interface DirectoryPathFormResolver {
    String resolveToAbsolutePath(final String dirPath, final String dirName);
    String resolveToAbsolutePath(final String dirPath);
}
