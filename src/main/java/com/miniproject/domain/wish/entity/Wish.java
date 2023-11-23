package com.miniproject.domain.wish.entity;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long id;

    // 숙소가 가진 좋아요 수를 확인하기 위해서 양방향 해야 하나?
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    // 멤버가 가진 숙소를 확인하기 위해 양방향 해야 하나?
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    protected Wish(Accommodation accommodation, Member member) {
        this.accommodation = accommodation;
        this.member = member;
    }
}
