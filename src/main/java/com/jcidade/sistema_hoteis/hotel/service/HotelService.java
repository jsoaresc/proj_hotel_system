package com.jcidade.sistema_hoteis.hotel.service;

import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.hotel.dto.HotelMapper;
import com.jcidade.sistema_hoteis.hotel.dto.HotelRequest;
import com.jcidade.sistema_hoteis.hotel.dto.HotelResponse;
import com.jcidade.sistema_hoteis.hotel.entity.Hotel;
import com.jcidade.sistema_hoteis.hotel.entity.HotelCategory;
import com.jcidade.sistema_hoteis.hotel.repository.HotelCategoryRepository;
import com.jcidade.sistema_hoteis.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {

    private final HotelRepository repository;
    private final HotelCategoryRepository categoryRepository;
    private final HotelMapper mapper;

    public HotelResponse create(HotelRequest request) {
        if (repository.existsByTaxId(request.taxId())) {
            throw new BusinessLogicException("Já existe um hotel com o CNPJ: " + request.taxId());
        }
        HotelCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + request.categoryId()));

        Hotel entity = mapper.toEntity(request);
        entity.setCategory(category); // Seta o relacionamento manualmente
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<HotelResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public HotelResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com id: " + id));
    }

    public HotelResponse update(Long id, HotelRequest request) {
        Hotel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com id: " + id));

        if (!entity.getTaxId().equals(request.taxId()) && repository.existsByTaxId(request.taxId())) {
            throw new BusinessLogicException("Já existe um hotel com o CNPJ: " + request.taxId());
        }

        HotelCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + request.categoryId()));

        mapper.updateEntityFromRequest(request, entity);
        entity.setCategory(category);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}