package com.miniproject.domain.accommodation.repository;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.accommodation.entity.QAccommodation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.function.Function;

import static com.miniproject.domain.accommodation.entity.QAccommodation.accommodation;

public class AccommodationRepositoryImpl extends QuerydslRepositorySupport implements AccommodationRepositoryCustom{
    public AccommodationRepositoryImpl() {
        super(Accommodation.class);
    }

    @Override
    public Page<Accommodation> findByCategory(Pageable pageable,
                                              Integer categoryParking,
                                              Integer categoryCooking,
                                              Integer categoryPickup,
                                              Integer type,
                                              Integer wishCount,
                                              String region) {

        QAccommodation accommodation = QAccommodation.accommodation;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (categoryParking != null && categoryParking == 1) {
            booleanBuilder.and(accommodation.categoryParking.isTrue());
        }
        if (categoryCooking != null && categoryCooking == 1) {
            booleanBuilder.and(accommodation.categoryCooking.isTrue());
        }
        if (categoryPickup != null && categoryPickup == 1) {
            booleanBuilder.and(accommodation.categoryPickup.isTrue());
        }
        if (type != null) {
            AccommodationType accommodationType = AccommodationType.fromIndex(type);
            booleanBuilder.and(accommodation.type.eq(accommodationType));
        }
        if (wishCount != null && wishCount >= 0) {
            booleanBuilder.and(accommodation.wishCount.goe(wishCount));
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

    private void addCategory(BooleanBuilder booleanBuilder,
                             Integer value,
                             Function<QAccommodation, BooleanExpression> category) {
        if (value != null && value == 1) {
            booleanBuilder.and(category.apply(accommodation));
        }
    }

}
