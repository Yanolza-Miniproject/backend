package com.miniproject.domain.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomInOrders is a Querydsl query type for RoomInOrders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomInOrders extends EntityPathBase<RoomInOrders> {

    private static final long serialVersionUID = 847693582L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomInOrders roomInOrders = new QRoomInOrders("roomInOrders");

    public final DateTimePath<java.time.LocalDateTime> checkInAt = createDateTime("checkInAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> checkOutAt = createDateTime("checkOutAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.miniproject.domain.member.entity.QMember member;

    public final NumberPath<Integer> numberOfGuests = createNumber("numberOfGuests", Integer.class);

    public final com.miniproject.domain.orders.entity.QOrders orders;

    public final QRoom room;

    public final QRoomInBasket roomInBasket;

    public QRoomInOrders(String variable) {
        this(RoomInOrders.class, forVariable(variable), INITS);
    }

    public QRoomInOrders(Path<? extends RoomInOrders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomInOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomInOrders(PathMetadata metadata, PathInits inits) {
        this(RoomInOrders.class, metadata, inits);
    }

    public QRoomInOrders(Class<? extends RoomInOrders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.miniproject.domain.member.entity.QMember(forProperty("member")) : null;
        this.orders = inits.isInitialized("orders") ? new com.miniproject.domain.orders.entity.QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.roomInBasket = inits.isInitialized("roomInBasket") ? new QRoomInBasket(forProperty("roomInBasket"), inits.get("roomInBasket")) : null;
    }

}

