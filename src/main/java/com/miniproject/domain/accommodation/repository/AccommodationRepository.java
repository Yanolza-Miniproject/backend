package com.miniproject.domain.accommodation.repository;

import com.miniproject.domain.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends
        JpaRepository<Accommodation,Long>,
        AccommodationRepositoryCustom
{

}
