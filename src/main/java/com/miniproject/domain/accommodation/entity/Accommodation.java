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

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY)
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
    private int wishCount;
    @Column
    private int viewCount;
    @Column
    private boolean isWish;

    public void plusViewCount() {
        this.viewCount = this.viewCount + 1;
    }

    public void plusWishCount() {
        this.wishCount = this.wishCount + 1;
    }

    public void minusWishCount() {
        this.wishCount = this.wishCount - 1;
    }


}
