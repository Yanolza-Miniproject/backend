package com.miniproject.domain.accommodation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccommodation is a Querydsl query type for Accommodation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccommodation extends EntityPathBase<Accommodation> {

    private static final long serialVersionUID = -1784569292L;

    public static final QAccommodation accommodation = new QAccommodation("accommodation");

    public final StringPath address = createString("address");

    public final StringPath categoryAmenities = createString("categoryAmenities");

    public final BooleanPath categoryCooking = createBoolean("categoryCooking");

    public final StringPath categoryDiningArea = createString("categoryDiningArea");

    public final BooleanPath categoryParking = createBoolean("categoryParking");

    public final BooleanPath categoryPickup = createBoolean("categoryPickup");

    public final TimePath<java.time.LocalTime> checkIn = createTime("checkIn", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> checkOut = createTime("checkOut", java.time.LocalTime.class);

    public final StringPath homepage = createString("homepage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath infoDetail = createString("infoDetail");

    public final BooleanPath isWish = createBoolean("isWish");

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<com.miniproject.domain.room.entity.Room, com.miniproject.domain.room.entity.QRoom> rooms = this.<com.miniproject.domain.room.entity.Room, com.miniproject.domain.room.entity.QRoom>createList("rooms", com.miniproject.domain.room.entity.Room.class, com.miniproject.domain.room.entity.QRoom.class, PathInits.DIRECT2);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final EnumPath<AccommodationType> type = createEnum("type", AccommodationType.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final NumberPath<Integer> wishCount = createNumber("wishCount", Integer.class);

    public QAccommodation(String variable) {
        super(Accommodation.class, forVariable(variable));
    }

    public QAccommodation(Path<? extends Accommodation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccommodation(PathMetadata metadata) {
        super(Accommodation.class, metadata);
    }

}

