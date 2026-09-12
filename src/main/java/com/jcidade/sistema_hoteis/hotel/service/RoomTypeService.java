package com.jcidade.sistema_hoteis.hotel.service;

import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.hotel.dto.RoomTypeMapper;
import com.jcidade.sistema_hoteis.hotel.dto.RoomTypeRequest;
import com.jcidade.sistema_hoteis.hotel.dto.RoomTypeResponse;
import com.jcidade.sistema_hoteis.hotel.entity.RoomType;
import com.jcidade.sistema_hoteis.hotel.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomTypeService {

    private final RoomTypeRepository repository;
    private final RoomTypeMapper mapper;

    public RoomTypeResponse create(RoomTypeRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessLogicException("Já existe um tipo de quarto com o nome: " + request.name());
        }
        RoomType entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<RoomTypeResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public RoomTypeResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de quarto não encontrado com id: " + id));
    }

    public RoomTypeResponse update(Long id, RoomTypeRequest request) {
        RoomType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de quarto não encontrado com id: " + id));

        if (!entity.getName().equals(request.name()) && repository.existsByName(request.name())) {
            throw new BusinessLogicException("Já existe um tipo de quarto com o nome: " + request.name());
        }
        mapper.updateEntityFromRequest(request, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de quarto não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}