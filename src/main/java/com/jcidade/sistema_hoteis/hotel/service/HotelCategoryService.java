package com.jcidade.sistema_hoteis.hotel.service;

import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.hotel.dto.HotelCategoryMapper;
import com.jcidade.sistema_hoteis.hotel.dto.HotelCategoryRequest;
import com.jcidade.sistema_hoteis.hotel.dto.HotelCategoryResponse;
import com.jcidade.sistema_hoteis.hotel.entity.HotelCategory;
import com.jcidade.sistema_hoteis.hotel.repository.HotelCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelCategoryService {

    private final HotelCategoryRepository repository;
    private final HotelCategoryMapper mapper;

    public HotelCategoryResponse create(HotelCategoryRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessLogicException("Já existe uma categoria com o nome: " + request.name());
        }
        HotelCategory entity = mapper.toEntity(request);
        entity = repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<HotelCategoryResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public HotelCategoryResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));
    }

    public HotelCategoryResponse update(Long id, HotelCategoryRequest request) {
        HotelCategory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));

        if (!entity.getName().equals(request.name()) && repository.existsByName(request.name())) {
            throw new BusinessLogicException("Já existe uma categoria com o nome: " + request.name());
        }

        mapper.updateEntityFromRequest(request, entity);
        entity = repository.save(entity);
        return mapper.toResponse(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }
}