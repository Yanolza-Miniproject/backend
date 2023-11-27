package com.miniproject.domain.room.entity;

import com.miniproject.domain.accommodation.entity.Accommodation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomImage> roomImages;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomInventory> roomInventories;

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

    public void updateRoomInventory(List<RoomInventory> roomInventories) {
        this.roomInventories = roomInventories;
    }

    public boolean isAvailable(LocalDate checkinDay, LocalDate checkoutDay) {
        List<RoomInventory> inventories = getInventories(checkinDay, checkoutDay);
        for (RoomInventory inventory : inventories) {
            if (inventory.getInventory() <= 0) {
                return false;
            }
        }
        return true;
    }

    public List<RoomInventory> getInventories(LocalDate checkinDay, LocalDate checkoutDay) {
        List<RoomInventory> inventories = new ArrayList<>();
        for (RoomInventory inventory : roomInventories) {
            if ((inventory.getDate().isEqual(checkinDay) || inventory.getDate().isAfter(checkinDay)) && inventory.getDate().isBefore(checkoutDay)) {
                inventories.add(inventory);
            }
        }
        return inventories;
    }


}
