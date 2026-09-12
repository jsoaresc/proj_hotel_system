package com.jcidade.sistema_hoteis.hotel.controller;

import com.jcidade.sistema_hoteis.hotel.dto.HotelCategoryRequest;
import com.jcidade.sistema_hoteis.hotel.dto.HotelCategoryResponse;
import com.jcidade.sistema_hoteis.hotel.service.HotelCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel-categories")
@RequiredArgsConstructor
public class HotelCategoryController {

    private final HotelCategoryService service;

    @PostMapping
    public ResponseEntity<HotelCategoryResponse> create(@Valid @RequestBody HotelCategoryRequest request) {
        HotelCategoryResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HotelCategoryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelCategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelCategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody HotelCategoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}