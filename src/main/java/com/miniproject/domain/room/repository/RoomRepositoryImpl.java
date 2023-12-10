package com.miniproject.domain.room.repository;

import com.miniproject.domain.room.dto.request.RoomRequest;
import com.miniproject.domain.room.entity.QRoom;
import com.miniproject.domain.room.entity.Room;
import com.miniproject.domain.room.exception.RoomNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RoomRepositoryImpl extends QuerydslRepositorySupport implements RoomRepositoryCustom {

    public RoomRepositoryImpl() { super(Room.class); }

    @Override
    public Page<Room> findByAccommodationIdAndCategory(Long accommodationId,
                                                       Pageable pageable,
                                                       RoomRequest request) {

        QRoom room = QRoom.room;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(room.accommodation.id.eq(accommodationId));

        if (request.categoryTv() != null && request.categoryTv() == 1) {
            booleanBuilder.and(room.categoryTv.isTrue());
        }
        if (request.categoryPc() != null && request.categoryPc() == 1) {
            booleanBuilder.and(room.categoryPc.isTrue());
        }
        if (request.categoryInternet() != null && request.categoryInternet() == 1) {
            booleanBuilder.and(room.categoryInternet.isTrue());
        }
        if (request.categoryRefrigerator() != null && request.categoryRefrigerator() == 1) {
            booleanBuilder.and(room.categoryRefrigerator.isTrue());
        }
        if (request.categoryBathingFacilities() != null && request.categoryBathingFacilities() == 1) {
            booleanBuilder.and(room.categoryBathingFacilities.isTrue());
        }
        if (request.categoryDryer() != null && request.categoryDryer() == 1) {
            booleanBuilder.and(room.categoryDryer.isTrue());
        }

        QueryResults<Room> result = from(room)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        if(result.getResults().isEmpty()) {
            throw new RoomNotFoundException();
        }

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
