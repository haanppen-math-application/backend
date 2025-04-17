package com.hpmath.academyapi.media.storage;

import com.hpmath.academyapi.aspect.log.ErrorLoggable;
import com.hpmath.academyapi.media.dto.MediaDto;
import com.hpmath.academyapi.media.storage.uploadfile.UploadFile;
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
