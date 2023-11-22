package com.miniproject.domain.room.entity;

import com.miniproject.domain.accommodation.entity.Accommodation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room")
    private List<RoomImage> roomImages = new ArrayList<>();

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


}
