package com.miniproject.domain.accommodation.entity;

import com.miniproject.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "accommodation")
    private List<Room> rooms;

    @Column
    private String name;
    @Column
    private String type; // TODO: enum타입으로 변경 고려
    @Column
    private String address;
    @Column
    private String phoneNumber;
    @Column
    private String homepage;
    @Column
    private String infoDetail;
    @Column
    private String thumbnailUrl;
    @Column
    private boolean categoryParking;
    @Column
    private boolean categoryCooking;
    @Column
    private boolean categoryPickup;
    @Column
    private boolean categoryAmenities;
    @Column
    private boolean categoryDiningArea;
    @Column
    private LocalDateTime checkIn;
    @Column
    private LocalDateTime checkOut;
    @Column
    private int likeCount;
    @Column
    private int viewCount;

}
