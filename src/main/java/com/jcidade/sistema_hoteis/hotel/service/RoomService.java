package com.jcidade.sistema_hoteis.hotel.service;

import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.hotel.dto.RoomMapper;
import com.jcidade.sistema_hoteis.hotel.dto.RoomRequest;
import com.jcidade.sistema_hoteis.hotel.dto.RoomResponse;
import com.jcidade.sistema_hoteis.hotel.entity.Hotel;
import com.jcidade.sistema_hoteis.hotel.entity.Room;
import com.jcidade.sistema_hoteis.hotel.entity.RoomType;
import com.jcidade.sistema_hoteis.hotel.repository.HotelRepository;
import com.jcidade.sistema_hoteis.hotel.repository.RoomRepository;
import com.jcidade.sistema_hoteis.hotel.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository repository;
    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomMapper mapper;

    public RoomResponse create(RoomRequest request) {
        if (repository.existsByNumberAndHotelId(request.number(), request.hotelId())) {
            throw new BusinessLogicException("Já existe um quarto com esse número neste hotel.");
        }
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com id: " + request.hotelId()));
        RoomType type = roomTypeRepository.findById(request.typeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de quarto não encontrado com id: " + request.typeId()));

        Room entity = mapper.toEntity(request);
        entity.setHotel(hotel);
        entity.setType(type);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findByHotel(Long hotelId) {
        return mapper.toResponseList(repository.findByHotelId(hotelId));
    }

    @Transactional(readOnly = true)
    public RoomResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado com id: " + id));
    }

    public RoomResponse update(Long id, RoomRequest request) {
        Room entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado com id: " + id));

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com id: " + request.hotelId()));
        RoomType type = roomTypeRepository.findById(request.typeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de quarto não encontrado com id: " + request.typeId()));

        boolean changedNumberOrHotel = !entity.getNumber().equals(request.number())
                || !entity.getHotel().getId().equals(request.hotelId());
        if (changedNumberOrHotel && repository.existsByNumberAndHotelId(request.number(), request.hotelId())) {
            throw new BusinessLogicException("Já existe um quarto com esse número neste hotel.");
        }

        mapper.updateEntityFromRequest(request, entity);
        entity.setHotel(hotel);
        entity.setType(type);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Quarto não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}