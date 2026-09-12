package com.jcidade.sistema_hoteis.hotel.entity;

import com.jcidade.sistema_hoteis.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_category")
public class HotelCategory extends BaseEntity {

    @Column(name = "category_name", nullable = false, length = 150, unique = true)
    private String name;

    @Column(name = "description", nullable = false, length = 2000, unique = false)
    private String description;
}
