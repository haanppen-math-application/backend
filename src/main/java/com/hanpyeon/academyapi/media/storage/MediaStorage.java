package com.hanpyeon.academyapi.media.storage;

import com.hanpyeon.academyapi.media.dto.MediaDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 외부 저장소에 이미지, 영상 데이터를 저장하고 가져오는 역할을 수행합니다.
 */
public interface MediaStorage {
    String store(MultipartFile multipartFile, String newFileName);
    void remove(String fileName);

    MediaDto loadFile(String fileName);
}
