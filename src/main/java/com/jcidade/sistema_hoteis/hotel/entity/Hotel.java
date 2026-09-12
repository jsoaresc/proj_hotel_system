package com.jcidade.sistema_hoteis.hotel.entity;

import com.jcidade.sistema_hoteis.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "tax_id", nullable = false, length = 20, unique = true)
    private String taxId;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private HotelCategory category;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();
}