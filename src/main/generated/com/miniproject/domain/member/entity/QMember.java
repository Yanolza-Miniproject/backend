package com.miniproject.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 2054088002L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.miniproject.domain.wish.entity.Wish, com.miniproject.domain.wish.entity.QWish> likes = this.<com.miniproject.domain.wish.entity.Wish, com.miniproject.domain.wish.entity.QWish>createList("likes", com.miniproject.domain.wish.entity.Wish.class, com.miniproject.domain.wish.entity.QWish.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final ListPath<com.miniproject.domain.orders.entity.Orders, com.miniproject.domain.orders.entity.QOrders> orders = this.<com.miniproject.domain.orders.entity.Orders, com.miniproject.domain.orders.entity.QOrders>createList("orders", com.miniproject.domain.orders.entity.Orders.class, com.miniproject.domain.orders.entity.QOrders.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

