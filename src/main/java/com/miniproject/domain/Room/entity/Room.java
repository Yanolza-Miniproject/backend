package com.miniproject.domain.Room.entity;

import com.miniproject.domain.Accommodation.entity.Accommodation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int capacity;

    private int inventory;

    private boolean categoryTv;
    private boolean categoryPc;
    private boolean categoryInternet;
    private boolean categoryRefrigerator;
    private boolean categoryBathingFacilities;
    private boolean categoryDryer;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

}
