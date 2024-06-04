package org.tpr.parcel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tpr.parcel.controller.dto.UpdateParcelDto;
import org.tpr.parcel.modal.Parcel;
import org.tpr.parcel.controller.dto.CreateParcelDto;
import org.tpr.parcel.service.ParcelService;
import org.tpr.parcel.service.facade.ParcelFacade;

import java.util.List;

@RestController
@RequestMapping("/api/parcel")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    private final ParcelFacade parcelFacade;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Parcel>> getAll() {
        return ResponseEntity.ok(parcelService.getAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Parcel> getById(@PathVariable String id) {
        return ResponseEntity.ok(parcelService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parcel> create(@RequestBody CreateParcelDto request) {
        return ResponseEntity.ok(parcelFacade.createParcel(request));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_DELIVERY')")
    @PutMapping("/{id}")
    public ResponseEntity<Parcel> update(
            @PathVariable String id,
            @RequestBody UpdateParcelDto request
    ) {
        return ResponseEntity.ok(parcelFacade.updateParcel(request, id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Parcel> delete(@PathVariable String id) {
        return ResponseEntity.ok(parcelService.deleteParcel(id));
    }
}
