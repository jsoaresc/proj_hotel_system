package com.jcidade.sistema_hoteis.hotel.dto;

import com.jcidade.sistema_hoteis.hotel.entity.RoomStatusEnum;

public record RoomResponse(
        Long id,
        String number,
        RoomStatusEnum maintenanceStatus,
        Long typeId,
        String typeName,
        Long hotelId,
        String hotelName
) {
}