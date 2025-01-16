package dev.cameron.acpdemo.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class BlobService {

        private final Environment env;

        public BlobService(Environment env) {
            this.env = env;
        }

        public BlobClient getBlobClient(String studentId){
                var blobContainerClient = getBlobContainerClient(getBlobServiceClient());
                return blobContainerClient.getBlobClient(studentId + ".json");
        }

        private BlobContainerClient getBlobContainerClient(BlobServiceClient blobServiceClient) {
                String azureContainerName = env.getProperty("BLOB_CONTAINER_NAME");
                if (azureContainerName == null || azureContainerName.isBlank()) {
                        throw new RuntimeException("Container name is not set, please set it.");
                }

                return blobServiceClient.getBlobContainerClient(azureContainerName);
        }

        private BlobServiceClient getBlobServiceClient(){
                String azureConnectString = env.getProperty("BLOB_STORAGE_ENDPOINT");
                if (azureConnectString == null || azureConnectString.isBlank()) {
                        throw new RuntimeException("Azure connection string is not set, please set it.");
                }

                return new BlobServiceClientBuilder().connectionString(azureConnectString).buildClient();
        }
}
