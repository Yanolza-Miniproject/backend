package com.miniproject.domain.accommodation.repository;

import com.miniproject.domain.accommodation.dto.request.AccommodationRequest;
import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.accommodation.entity.QAccommodation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class AccommodationRepositoryImpl extends QuerydslRepositorySupport implements AccommodationRepositoryCustom{
    public AccommodationRepositoryImpl() {
        super(Accommodation.class);
    }

    @Override
    public Page<Accommodation> findByCategory(Pageable pageable,
                                              AccommodationRequest request,
                                              String region) {

        QAccommodation accommodation = QAccommodation.accommodation;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (request.categoryParking() != null && request.categoryParking() == 1) {
            booleanBuilder.and(accommodation.categoryParking.isTrue());
        }
        if (request.categoryCooking() != null && request.categoryCooking() == 1) {
            booleanBuilder.and(accommodation.categoryCooking.isTrue());
        }
        if (request.categoryPickup() != null && request.categoryPickup() == 1) {
            booleanBuilder.and(accommodation.categoryPickup.isTrue());
        }
        if (request.type() != null) {
            AccommodationType accommodationType = AccommodationType.fromIndex(request.type());
            booleanBuilder.and(accommodation.type.eq(accommodationType));
        }
        if (request.wishCount() != null && request.wishCount() >= 0) {
            booleanBuilder.and(accommodation.wishCount.goe(request.wishCount()));
        }
        if (region != null && !region.isEmpty()) {
            booleanBuilder.and(accommodation.address.contains(region));
        }

        QueryResults<Accommodation> result = from(accommodation)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());

    }

}
