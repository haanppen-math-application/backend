package com.hpmath.hpmathcoreapi.media.storage.uploadfile;

import java.io.InputStream;

public interface UploadFile {
    String getUniqueFileName();
    InputStream getInputStream();
    String getExtension();
}
