package com.miniproject.domain.basket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBasket is a Querydsl query type for Basket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBasket extends EntityPathBase<Basket> {

    private static final long serialVersionUID = 1643202202L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBasket basket = new QBasket("basket");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.miniproject.domain.member.entity.QMember member;

    public final ListPath<com.miniproject.domain.room.entity.RoomInBasket, com.miniproject.domain.room.entity.QRoomInBasket> rooms = this.<com.miniproject.domain.room.entity.RoomInBasket, com.miniproject.domain.room.entity.QRoomInBasket>createList("rooms", com.miniproject.domain.room.entity.RoomInBasket.class, com.miniproject.domain.room.entity.QRoomInBasket.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalCount = createNumber("totalCount", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QBasket(String variable) {
        this(Basket.class, forVariable(variable), INITS);
    }

    public QBasket(Path<? extends Basket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBasket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBasket(PathMetadata metadata, PathInits inits) {
        this(Basket.class, metadata, inits);
    }

    public QBasket(Class<? extends Basket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.miniproject.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

