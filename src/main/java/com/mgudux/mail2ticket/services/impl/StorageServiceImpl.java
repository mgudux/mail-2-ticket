package com.mgudux.mail2ticket.services.impl;

import com.mgudux.mail2ticket.services.StorageService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    @Override
    public String uploadRawEmail(byte[] emlBytes, UUID emlFileId) {
        return "";
    }

    @Override
    public String uploadExcel(byte[] xlsxBytes) {
        return "";
    }

    @Override
    public String generatePresignedUrl(String urlKey) {
        return "";
    }
}
