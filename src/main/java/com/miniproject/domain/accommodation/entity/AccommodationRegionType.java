package com.miniproject.domain.accommodation.entity;

import lombok.Getter;

@Getter
public enum AccommodationRegionType {

    SEOUL(0, "서울"),
    GYEONGGI(1, "경기"),
    GANGWON(2, "강원"),
    CHUNGCHEONG(3, "충청"),
    JEOLLA(4, "전라"),
    GYEONGSANG(5, "경상"),
    JEJU(6, "제주");

    private int value;
    private String description;

    AccommodationRegionType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static AccommodationRegionType findByValue(int value) {
        for (AccommodationRegionType accommodationRegionType : AccommodationRegionType.values()) {
            if (accommodationRegionType.getValue() == value) {
                return accommodationRegionType;
            }
        }
        return null;
    }
}
