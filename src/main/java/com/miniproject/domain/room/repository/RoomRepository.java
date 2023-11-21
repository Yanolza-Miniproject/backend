package com.miniproject.domain.room.repository;

import com.miniproject.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {

}
