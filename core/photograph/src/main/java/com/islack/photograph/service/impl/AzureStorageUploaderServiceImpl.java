package com.islack.photograph.service.impl;

import com.islack.photograph.domain.dto.StockPhoto;
import com.islack.photograph.service.AzureStorageUploaderService;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.*;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Helper class that provides function to upload image to Azure storage.
 */
@Component
public class AzureStorageUploaderServiceImpl implements AzureStorageUploaderService {
    private static final Logger logger = LoggerFactory.getLogger(AzureStorageUploaderServiceImpl.class);

    private final String originalImageContainer;
    private final String thumbnailImageContainer;

    private String azureStorageBaseUri;

    /**
     * Constructor that accepts settings from property file.
     *
     * @param originalImageContainer  storage container name for original images
     * @param thumbnailImageContainer storage container name for thumbnail images
     */
    public AzureStorageUploaderServiceImpl(@Value("${islack.storage.originalImageContainer}") String originalImageContainer,
                                @Value("${islack.storage.thumbnailImageContainer}") String thumbnailImageContainer) {
        logger.debug(originalImageContainer);
        logger.debug(thumbnailImageContainer);

        this.originalImageContainer = (originalImageContainer == null || originalImageContainer.isEmpty())
                ? "images-original" : originalImageContainer;
        this.thumbnailImageContainer = (thumbnailImageContainer == null || thumbnailImageContainer.isEmpty())
                ? "images-thumbnail" : thumbnailImageContainer;
    }

    /**
     * Get container name of original images.
     *
     * @return container name
     */
    @Override
    public String getOriginalImageContainer() {
        return originalImageContainer;
    }

    /**
     * Get container name of thumbnail images.
     *
     * @return container name
     */
    @Override
    public String getThumbnailImageContainer() {
        return thumbnailImageContainer;
    }

    /**
     * Get the base URI of Azure storage.
     *
     * @return base URI string
     */
    @Override
    public String getAzureStorageBaseUri(ApplicationContext applicationContext) {
        if (azureStorageBaseUri == null) {
            CloudStorageAccount storageAccount =
                    (CloudStorageAccount) applicationContext.getBean("cloudStorageAccount");
            azureStorageBaseUri = "https://" + storageAccount.createCloudBlobClient().getEndpoint().getHost();
        }

        return azureStorageBaseUri;
    }

    /**
     * Upload image file to Azure storage with specified name.
     *
     * @param file     image file object
     * @param fileName specified file name
     * @return relative path of the created image blob
     */
    @Override
    public String uploadToAzureStorage(ApplicationContext applicationContext, MultipartFile file, String fileName) {
        String uri = null;

        try {
            CloudStorageAccount storageAccount =
                    (CloudStorageAccount) applicationContext.getBean("cloudStorageAccount");
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            CloudBlobContainer thumbnailImageContainer = setupContainer(blobClient, this.thumbnailImageContainer);
            CloudBlobContainer originalImageContainer = setupContainer(blobClient, this.originalImageContainer);

            if(thumbnailImageContainer != null) {

                CloudBlockBlob blob = thumbnailImageContainer.getBlockBlobReference(fileName);
                blob.getProperties().setContentType(file.getContentType());
                BlobOutputStream os = blob.openOutputStream();
                Thumbnails.of(file.getInputStream())
                        .size(200, 200)
                        .toOutputStream(os);
                os.close();
                System.out.println("Finish thumb");
            }

            if (originalImageContainer != null) {
                CloudBlockBlob blob = originalImageContainer.getBlockBlobReference(fileName);
                blob.getProperties().setContentType(file.getContentType());
                blob.upload(file.getInputStream(), file.getSize());
                uri = blob.getUri().getPath();
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error uploading image: " + e.getMessage());
        }

        return uri;
    }

    private CloudBlobContainer setupContainer(CloudBlobClient blobClient, String containerName) {
        try {
            CloudBlobContainer container = blobClient.getContainerReference(containerName);
            if (!container.exists()) {
                container.createIfNotExists();
                BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
                containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
                container.uploadPermissions(containerPermissions);
            }

            return container;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error setting up container: " + e.getMessage());
            return null;
        }
    }


    @Override
    public String uploadFromStockToAzure(ApplicationContext applicationContext, StockPhoto photo, String fileName) {
        String uri = null;


        try {
            URL url = new URL(photo.getLargeImageURL());
            URL urlThumb = new URL(photo.getPreviewURL());
            CloudStorageAccount storageAccount =
                    (CloudStorageAccount) applicationContext.getBean("cloudStorageAccount");
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            CloudBlobContainer thumbnailImageContainer = setupContainer(blobClient, this.thumbnailImageContainer);
            CloudBlobContainer originalImageContainer = setupContainer(blobClient, this.originalImageContainer);

            if(thumbnailImageContainer != null) {

                CloudBlockBlob blob = thumbnailImageContainer.getBlockBlobReference(fileName);
                blob.getProperties().setContentType("image/jpeg");
                blob.upload(urlThumb.openStream(), getFileSize(urlThumb));
            }

            if (originalImageContainer != null) {
                CloudBlockBlob blob = originalImageContainer.getBlockBlobReference(fileName);
                blob.getProperties().setContentType("image/jpeg");
                blob.upload(url.openStream(), getFileSize(url));
                uri = blob.getUri().getPath();
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error uploading image: " + e.getMessage());
        }

        System.out.println("----------- URI = " + uri + " --------------------");
        return uri;
    }


    private int getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }
}
