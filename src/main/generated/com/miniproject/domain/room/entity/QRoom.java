package com.miniproject.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = -495977852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

    public final com.miniproject.domain.accommodation.entity.QAccommodation accommodation;

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final BooleanPath categoryBathingFacilities = createBoolean("categoryBathingFacilities");

    public final BooleanPath categoryDryer = createBoolean("categoryDryer");

    public final BooleanPath categoryInternet = createBoolean("categoryInternet");

    public final BooleanPath categoryPc = createBoolean("categoryPc");

    public final BooleanPath categoryRefrigerator = createBoolean("categoryRefrigerator");

    public final BooleanPath categoryTv = createBoolean("categoryTv");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> inventory = createNumber("inventory", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<RoomImage, QRoomImage> roomImages = this.<RoomImage, QRoomImage>createList("roomImages", RoomImage.class, QRoomImage.class, PathInits.DIRECT2);

    public final ListPath<RoomInventory, QRoomInventory> roomInventories = this.<RoomInventory, QRoomInventory>createList("roomInventories", RoomInventory.class, QRoomInventory.class, PathInits.DIRECT2);

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoom(PathMetadata metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accommodation = inits.isInitialized("accommodation") ? new com.miniproject.domain.accommodation.entity.QAccommodation(forProperty("accommodation")) : null;
    }

}

