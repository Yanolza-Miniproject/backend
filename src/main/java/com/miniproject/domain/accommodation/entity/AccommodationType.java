package com.miniproject.domain.accommodation.entity;

import lombok.Getter;

@Getter
public enum AccommodationType {

    HOTEL("관광호텔", 0, "B02010100"),
    CONDOMINIUM("콘도미니엄", 1, "B02010500"),
    YOUTH_HOSTEL("유스호스텔", 2, "B02010600"),
    PENSION("펜션", 3, "B02010700"),
    MOTEL("모텔", 4, "B02010900"),
    MINBAK("민박", 5, "B02011000"),
    GUEST_HOUSE("게스트하우스", 6, "B02011100"),
    HOME_STAY("홈스테이", 7, "B02011200"),
    SERVICED_RESIDENCE("서비스드레지던스", 8, "B02011300"),
    TRADITIONAL_HOUSE("한옥", 9, "B02011600");

    private final String type;
    private final int index;
    private final String code;

    AccommodationType(String type, int index, String code) {
        this.type = type;
        this.index = index;
        this.code = code;
    }

    public static AccommodationType fromCode(String code) {
        for (AccommodationType type : AccommodationType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No AccommodationType with code " + code);
    }

    public static AccommodationType fromIndex(Integer index) {
        for (AccommodationType type : AccommodationType.values()) {
            if (type.getIndex() == index) {
                return type;
            }
        }
        throw new IllegalArgumentException("No AccommodationType with code " + index);
    }

}
