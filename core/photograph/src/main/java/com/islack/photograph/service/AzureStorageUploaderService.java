package com.islack.photograph.service;

import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

public interface AzureStorageUploaderService {
    String getOriginalImageContainer();

    String getThumbnailImageContainer();

    String getAzureStorageBaseUri(ApplicationContext applicationContext);

    String uploadToAzureStorage(ApplicationContext applicationContext, MultipartFile file, String fileName);
}
