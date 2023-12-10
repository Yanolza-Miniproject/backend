package com.miniproject.domain.room.repository;

import com.miniproject.domain.room.dto.request.RoomRequest;
import com.miniproject.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepositoryCustom {

    Page<Room> findByAccommodationIdAndCategory(Long accommodationId,
                                                Pageable pageable,
                                                RoomRequest request);

}
