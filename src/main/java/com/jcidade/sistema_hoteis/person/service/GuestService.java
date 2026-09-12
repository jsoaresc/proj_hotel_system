package com.jcidade.sistema_hoteis.person.service;

import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.person.dto.GuestMapper;
import com.jcidade.sistema_hoteis.person.dto.GuestRequest;
import com.jcidade.sistema_hoteis.person.dto.GuestResponse;
import com.jcidade.sistema_hoteis.person.entity.Guest;
import com.jcidade.sistema_hoteis.person.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final GuestRepository repository;
    private final GuestMapper mapper;

    public GuestResponse create(GuestRequest request) {
        if (repository.existsByDocument(request.document())) {
            throw new BusinessLogicException(
                    "Já existe um hóspede com o documento: " + request.document());
        }
        Guest entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<GuestResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public GuestResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hóspede não encontrado com id: " + id));
    }

    public GuestResponse update(Long id, GuestRequest request) {
        Guest entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hóspede não encontrado com id: " + id));

        if (!entity.getDocument().equals(request.document())
                && repository.existsByDocument(request.document())) {
            throw new BusinessLogicException(
                    "Já existe um hóspede com o documento: " + request.document());
        }

        mapper.updateEntityFromRequest(request, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Hóspede não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}