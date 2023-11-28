package com.miniproject.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomInBasket is a Querydsl query type for RoomInBasket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomInBasket extends EntityPathBase<RoomInBasket> {

    private static final long serialVersionUID = 460266991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomInBasket roomInBasket = new QRoomInBasket("roomInBasket");

    public final com.miniproject.domain.basket.entity.QBasket basket;

    public final DateTimePath<java.time.LocalDateTime> checkInAt = createDateTime("checkInAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> checkOutAt = createDateTime("checkOutAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.miniproject.domain.member.entity.QMember member;

    public final NumberPath<Integer> numberOfGuests = createNumber("numberOfGuests", Integer.class);

    public final QRoom room;

    public QRoomInBasket(String variable) {
        this(RoomInBasket.class, forVariable(variable), INITS);
    }

    public QRoomInBasket(Path<? extends RoomInBasket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomInBasket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomInBasket(PathMetadata metadata, PathInits inits) {
        this(RoomInBasket.class, metadata, inits);
    }

    public QRoomInBasket(Class<? extends RoomInBasket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.basket = inits.isInitialized("basket") ? new com.miniproject.domain.basket.entity.QBasket(forProperty("basket"), inits.get("basket")) : null;
        this.member = inits.isInitialized("member") ? new com.miniproject.domain.member.entity.QMember(forProperty("member")) : null;
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

