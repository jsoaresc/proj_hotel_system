package com.jcidade.sistema_hoteis.booking.service;

import com.jcidade.sistema_hoteis.booking.dto.*;
import com.jcidade.sistema_hoteis.booking.entity.*;
import com.jcidade.sistema_hoteis.booking.repository.*;
import com.jcidade.sistema_hoteis.core.exception.BusinessLogicException;
import com.jcidade.sistema_hoteis.core.exception.ResourceNotFoundException;
import com.jcidade.sistema_hoteis.hotel.entity.Hotel;
import com.jcidade.sistema_hoteis.hotel.entity.Room;
import com.jcidade.sistema_hoteis.hotel.entity.RoomStatusEnum;
import com.jcidade.sistema_hoteis.hotel.repository.HotelRepository;
import com.jcidade.sistema_hoteis.hotel.repository.RoomRepository;
import com.jcidade.sistema_hoteis.person.entity.Guest;
import com.jcidade.sistema_hoteis.person.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationItemRepository itemRepository;
    private final GuestRepository guestRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationMapper reservationMapper;

    public ReservationResponse create(ReservationRequest request) {
        validateDates(request);

        Guest booker = guestRepository.findById(request.bookerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hóspede não encontrado com id: " + request.bookerId()));

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel não encontrado com id: " + request.hotelId()));

        long nights = ChronoUnit.DAYS.between(
                request.expectedCheckinDate(), request.expectedCheckoutDate());

        Reservation reservation = new Reservation();
        reservation.setBooker(booker);
        reservation.setHotel(hotel);
        reservation.setExpectedCheckinDate(request.expectedCheckinDate());
        reservation.setExpectedCheckoutDate(request.expectedCheckoutDate());
        reservation.setStatus(ReservationStatusEnum.PENDING);
        reservation.setItems(new ArrayList<>());
        reservation.setTotalAmount(BigDecimal.ZERO);

        BigDecimal total = BigDecimal.ZERO;

        for (ReservationItemRequest itemReq : request.items()) {
            ReservationItem item = buildItem(itemReq, reservation, hotel, nights);
            reservation.getItems().add(item);
            total = total.add(itemReq.appliedDailyRate()
                    .multiply(BigDecimal.valueOf(nights)));
        }
        reservation.setTotalAmount(total);

        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toResponse(saved);
    }

    private ReservationItem buildItem(ReservationItemRequest itemReq,
                                      Reservation reservation,
                                      Hotel hotel,
                                      long nights) {
        Room room = roomRepository.findById(itemReq.roomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Quarto não encontrado com id: " + itemReq.roomId()));

        if (!room.getHotel().getId().equals(hotel.getId())) {
            throw new BusinessLogicException(
                    "O quarto " + room.getNumber() + " não pertence ao hotel informado.");
        }
        if (room.getMaintenanceStatus() != RoomStatusEnum.OPERATIONAL) {
            throw new BusinessLogicException(
                    "O quarto " + room.getNumber() + " não está operacional.");
        }
        boolean overlapping = itemRepository.existsOverlappingReservation(
                room.getId(),
                reservation.getExpectedCheckinDate(),
                reservation.getExpectedCheckoutDate());
        if (overlapping) {
            throw new BusinessLogicException(
                    "O quarto " + room.getNumber()
                            + " já possui reserva no período informado.");
        }

        long responsibleCount = itemReq.occupants().stream()
                .filter(OccupancyRequest::isRoomResponsible)
                .count();
        if (responsibleCount != 1) {
            throw new BusinessLogicException(
                    "Cada quarto deve ter exatamente um ocupante responsável.");
        }

        ReservationItem item = new ReservationItem();
        item.setReservation(reservation);
        item.setRoom(room);
        item.setAppliedDailyRate(itemReq.appliedDailyRate());
        item.setOccupants(new ArrayList<>());

        for (OccupancyRequest occReq : itemReq.occupants()) {
            Guest guest = guestRepository.findById(occReq.guestId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Hóspede não encontrado com id: " + occReq.guestId()));
            Occupancy occ = new Occupancy();
            occ.setReservationItem(item);
            occ.setGuest(guest);
            occ.setIsRoomResponsible(occReq.isRoomResponsible());
            item.getOccupants().add(occ);
        }
        return item;
    }

    private void validateDates(ReservationRequest request) {
        if (!request.expectedCheckoutDate().isAfter(request.expectedCheckinDate())) {
            throw new BusinessLogicException(
                    "A data de check-out deve ser posterior à data de check-in.");
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationMapper.toResponseList(reservationRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ReservationResponse findById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reserva não encontrada com id: " + id));
    }

    public ReservationResponse updateStatus(Long id, ReservationStatusUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reserva não encontrada com id: " + id));

        ReservationStatusEnum current = reservation.getStatus();
        ReservationStatusEnum target = request.status();

        if (!isTransitionAllowed(current, target)) {
            throw new BusinessLogicException(
                    "Transição de status inválida: " + current + " → " + target);
        }
        reservation.setStatus(target);
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    private boolean isTransitionAllowed(ReservationStatusEnum from, ReservationStatusEnum to) {
        return switch (from) {
            case PENDING -> to == ReservationStatusEnum.CONFIRMED
                    || to == ReservationStatusEnum.CANCELLED;
            case CONFIRMED -> to == ReservationStatusEnum.IN_PROGRESS
                    || to == ReservationStatusEnum.CANCELLED;
            case IN_PROGRESS -> to == ReservationStatusEnum.COMPLETED;
            case COMPLETED, CANCELLED -> false;
        };
    }

    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reserva não encontrada com id: " + id));
        if (reservation.getStatus() == ReservationStatusEnum.IN_PROGRESS
                || reservation.getStatus() == ReservationStatusEnum.COMPLETED) {
            throw new BusinessLogicException(
                    "Não é possível excluir reservas em andamento ou concluídas. Cancele primeiro.");
        }
        reservationRepository.delete(reservation);
    }
}