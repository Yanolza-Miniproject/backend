package com.miniproject.domain.accommodation.repository;

import com.miniproject.domain.accommodation.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccommodationRepositoryCustom {

    Page<Accommodation> findByCategory(Pageable pageable,
                                       Integer categoryParking,
                                       Integer categoryCooking,
                                       Integer categoryPickup,
                                       Integer type,
                                       Integer wishCount,
                                       String region01);

}
