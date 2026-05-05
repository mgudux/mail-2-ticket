package com.mgudux.mail2ticket.controller;

import com.mgudux.mail2ticket.domain.dto.EmlFileDto;
import com.mgudux.mail2ticket.services.EmlFileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/emails")
public class EmlFileController {

    private final EmlFileService emlFileService;

    public EmlFileController(EmlFileService emlFileService) {
        this.emlFileService = emlFileService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmlFile(@PathVariable UUID id) {
        emlFileService.deleteEmlFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<EmlFileDto.Summary> listEmlFile() {
        return emlFileService.listEmlFile();
    }

    @GetMapping(path = "/{id}")
    public EmlFileDto.Detail getEmlFile(@PathVariable UUID id) {
        return emlFileService.getEmlFile(id);
    }

    @PutMapping(path = "/{id}")
    public EmlFileDto.Detail updateEmlFile(@PathVariable UUID id, @Valid @RequestBody EmlFileDto.Update update) {
        return emlFileService.updateEmlFile(id, update);
    }
}
