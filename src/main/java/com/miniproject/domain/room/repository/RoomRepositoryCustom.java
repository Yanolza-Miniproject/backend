package com.miniproject.domain.room.repository;

import com.miniproject.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepositoryCustom {

    Page<Room> findByAccommodationIdAndCategory(Long accommodationId,
                                                Pageable pageable,
                                                Integer categoryTv,
                                                Integer categoryPc,
                                                Integer categoryInternet,
                                                Integer categoryRefrigerator,
                                                Integer categoryBathingFacilities,
                                                Integer categoryDryer);

}
