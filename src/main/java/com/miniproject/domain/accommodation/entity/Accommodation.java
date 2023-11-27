package com.miniproject.domain.accommodation.entity;

import com.miniproject.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @Column //
    private String name;
    @Column //
    @Enumerated(EnumType.STRING)
    private AccommodationType type;
    @Column //
    private String address;
    @Column //
    private String phoneNumber;
    @Column //
    private String homepage;
    @Column(length = 1000) //
    private String infoDetail;
    @Column
    private String thumbnailUrl;
    @Column //
    private boolean categoryParking;
    @Column //
    private boolean categoryCooking;
    @Column //
    private boolean categoryPickup;
    @Column //
    private String categoryAmenities;
    @Column //
    private String categoryDiningArea;
    @Column //
    private LocalTime checkIn;
    @Column //
    private LocalTime checkOut;
    @Column
    private int wishCount;
    @Column
    private int viewCount;

    public void plusViewCount() {
        this.viewCount = this.viewCount + 1;
    }


}
