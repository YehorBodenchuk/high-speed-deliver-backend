package org.tpr.parcel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tpr.parcel.modal.Parcel;
import org.tpr.parcel.controller.dto.CreateParcelDto;
import org.tpr.parcel.modal.enums.ParcelStatus;
import org.tpr.parcel.service.ParcelService;

import java.util.List;

@RestController
@RequestMapping("/api/parcel")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping("/")
    public ResponseEntity<List<Parcel>> getAll() {
        return ResponseEntity.ok(parcelService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parcel> getById(@PathVariable String id) {
        return ResponseEntity.ok(parcelService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parcel> create(@RequestBody CreateParcelDto createParcelDto) {
        return ResponseEntity.ok(parcelService.createParcel(createParcelDto));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<Parcel> updateStatus(@PathVariable String id, @PathVariable ParcelStatus status) {
        return ResponseEntity.ok(parcelService.updateParcelStatus(status, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Parcel> delete(@PathVariable String id) {
        return ResponseEntity.ok(parcelService.deleteParcel(id));
    }
}
