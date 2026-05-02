package com.mgudux.mail2ticket.services;

import java.util.UUID;


// potential future update, wont be included in the first version
public interface StorageService {
    String uploadRawEmail(byte[] emlBytes, UUID emlFileId);
    String uploadExcel(byte[] xlsxBytes);
    String generatePresignedUrl(String urlKey);


}
