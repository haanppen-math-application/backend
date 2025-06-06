package com.hpmath.domain.media.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.hpmath.domain.media.dto.MediaDto;
import com.hpmath.domain.media.storage.uploadfile.UploadFile;
import java.net.URLConnection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@Profile("deploy")
@Primary
public class AwsStorage implements MediaStorage {

    private final AmazonS3 amazonS3Client;
    private final String bucketName;

    public AwsStorage(
            final AmazonS3 amazonS3Client,
            @Value("${cloud.aws.s3.bucket}") final String bucketName
    ) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

    @Override
    public void store(UploadFile uploadFile) {

        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadFile.getExtension());
//        objectMetadata.setContentLength(byteArrayOutputStream.size());

        amazonS3Client.putObject(
                bucketName,
                uploadFile.getUniqueFileName(),
                uploadFile.getInputStream(),
                objectMetadata
        );
    }

    @Override
    public void remove(String fileName) {
        amazonS3Client.deleteObject(bucketName, fileName);
    }

    @Override
    public MediaDto loadFile(String fileName) {
        final S3Object s3Object = amazonS3Client.getObject(bucketName, fileName);
        final MediaDto mediaDto = new MediaDto(s3Object.getObjectContent(), MediaType.parseMediaType(URLConnection.guessContentTypeFromName(fileName)),
                s3Object.getObjectMetadata().getContentLength());
        return mediaDto;
    }

    @Override
    public Set<String> loadAllFileNames() {
        return amazonS3Client.listObjects(bucketName).getObjectSummaries()
                .stream()
                .map(s3ObjectSummary -> s3ObjectSummary.getKey())
                .collect(Collectors.toSet());
    }
}
