package com.miniproject.domain.accommodation.repository;

import com.miniproject.domain.accommodation.dto.request.AccommodationRequest;
import com.miniproject.domain.accommodation.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccommodationRepositoryCustom {

    Page<Accommodation> findByCategory(Pageable pageable,
                                       AccommodationRequest request,
                                       String region);

}
