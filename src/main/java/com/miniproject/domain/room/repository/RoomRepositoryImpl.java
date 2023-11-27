package com.miniproject.domain.room.repository;

import com.miniproject.domain.room.entity.QRoom;
import com.miniproject.domain.room.entity.Room;
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
                                                       Integer categoryTv,
                                                       Integer categoryPc,
                                                       Integer categoryInternet,
                                                       Integer categoryRefrigerator,
                                                       Integer categoryBathingFacilities,
                                                       Integer categoryDryer) {

        QRoom room = QRoom.room;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(room.accommodation.id.eq(accommodationId));

        if (categoryTv != null && categoryTv == 1) {
            booleanBuilder.and(room.categoryTv.isTrue());
        }
        if (categoryPc != null && categoryPc == 1) {
            booleanBuilder.and(room.categoryPc.isTrue());
        }
        if (categoryInternet != null && categoryInternet == 1) {
            booleanBuilder.and(room.categoryInternet.isTrue());
        }
        if (categoryRefrigerator != null && categoryRefrigerator == 1) {
            booleanBuilder.and(room.categoryRefrigerator.isTrue());
        }
        if (categoryBathingFacilities != null && categoryBathingFacilities == 1) {
            booleanBuilder.and(room.categoryBathingFacilities.isTrue());
        }
        if (categoryDryer != null && categoryDryer == 1) {
            booleanBuilder.and(room.categoryDryer.isTrue());
        }

        QueryResults<Room> result = from(room)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
