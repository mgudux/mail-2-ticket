package com.mgudux.mail2ticket.services;

import java.util.UUID;

public interface StorageService {
    String uploadRawEmail(byte[] emlBytes, UUID emlFileId);
    String uploadExcel(byte[] xlsxBytes);
    String generatePresignedUrl(String urlKey);


}
