package com.miniproject.domain.orders.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = -1768507752L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.miniproject.domain.member.entity.QMember member;

    public final DateTimePath<java.time.LocalDateTime> orderAt = createDateTime("orderAt", java.time.LocalDateTime.class);

    public final ListPath<com.miniproject.domain.room.entity.RoomInOrders, com.miniproject.domain.room.entity.QRoomInOrders> roomInOrders = this.<com.miniproject.domain.room.entity.RoomInOrders, com.miniproject.domain.room.entity.QRoomInOrders>createList("roomInOrders", com.miniproject.domain.room.entity.RoomInOrders.class, com.miniproject.domain.room.entity.QRoomInOrders.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalCount = createNumber("totalCount", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.miniproject.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

