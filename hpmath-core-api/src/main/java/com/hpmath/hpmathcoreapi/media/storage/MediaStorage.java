package com.hpmath.hpmathcoreapi.media.storage;

import com.hpmath.hpmathcoreapi.aspect.log.ErrorLoggable;
import com.hpmath.hpmathcoreapi.media.dto.MediaDto;
import com.hpmath.hpmathcoreapi.media.storage.uploadfile.UploadFile;
import java.util.Set;

/**
 * 외부 저장소에 이미지, 영상 데이터를 저장하고 가져오는 역할을 수행합니다.
 */
@ErrorLoggable
public interface MediaStorage {
    void store(UploadFile uploadFile);
    void remove(String fileName);

    MediaDto loadFile(String fileName);

    Set<String> loadAllFileNames();
}
