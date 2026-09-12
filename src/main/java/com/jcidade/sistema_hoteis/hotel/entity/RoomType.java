package com.jcidade.sistema_hoteis.hotel.entity;

import com.jcidade.sistema_hoteis.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_type")
public class RoomType extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "base_daily_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseDailyRate;
}