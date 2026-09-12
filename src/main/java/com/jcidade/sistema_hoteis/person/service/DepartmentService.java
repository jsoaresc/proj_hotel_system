package com.jcidade.sistema_hoteis.person.service;

import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.hotel.entity.Hotel;
import com.jcidade.sistema_hoteis.hotel.repository.HotelRepository;
import com.jcidade.sistema_hoteis.person.dto.DepartmentMapper;
import com.jcidade.sistema_hoteis.person.dto.DepartmentRequest;
import com.jcidade.sistema_hoteis.person.dto.DepartmentResponse;
import com.jcidade.sistema_hoteis.person.entity.Department;
import com.jcidade.sistema_hoteis.person.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository repository;
    private final HotelRepository hotelRepository;
    private final DepartmentMapper mapper;

    public DepartmentResponse create(DepartmentRequest request) {
        if (repository.existsByNameAndHotelId(request.name(), request.hotelId())) {
            throw new BusinessLogicException(
                    "Já existe um departamento com esse nome neste hotel.");
        }
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel não encontrado com id: " + request.hotelId()));

        Department entity = mapper.toEntity(request);
        entity.setHotel(hotel);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll(Long hotelId) {
        List<Department> departments = (hotelId != null)
                ? repository.findByHotelId(hotelId)
                : repository.findAll();
        return mapper.toResponseList(departments);
    }

    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Departamento não encontrado com id: " + id));
    }

    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Departamento não encontrado com id: " + id));

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel não encontrado com id: " + request.hotelId()));

        boolean changedNameOrHotel = !entity.getName().equals(request.name())
                || !entity.getHotel().getId().equals(request.hotelId());
        if (changedNameOrHotel
                && repository.existsByNameAndHotelId(request.name(), request.hotelId())) {
            throw new BusinessLogicException(
                    "Já existe um departamento com esse nome neste hotel.");
        }

        mapper.updateEntityFromRequest(request, entity);
        entity.setHotel(hotel);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Departamento não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}