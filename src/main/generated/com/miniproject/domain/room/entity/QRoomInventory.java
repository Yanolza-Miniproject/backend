package com.miniproject.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomInventory is a Querydsl query type for RoomInventory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomInventory extends EntityPathBase<RoomInventory> {

    private static final long serialVersionUID = 399102776L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomInventory roomInventory = new QRoomInventory("roomInventory");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> inventory = createNumber("inventory", Integer.class);

    public final QRoom room;

    public QRoomInventory(String variable) {
        this(RoomInventory.class, forVariable(variable), INITS);
    }

    public QRoomInventory(Path<? extends RoomInventory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomInventory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomInventory(PathMetadata metadata, PathInits inits) {
        this(RoomInventory.class, metadata, inits);
    }

    public QRoomInventory(Class<? extends RoomInventory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

