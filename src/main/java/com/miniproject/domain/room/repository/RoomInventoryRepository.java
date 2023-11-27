package com.miniproject.domain.room.repository;

import com.miniproject.domain.room.entity.RoomInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInventoryRepository extends JpaRepository<RoomInventory, Long> {
}
