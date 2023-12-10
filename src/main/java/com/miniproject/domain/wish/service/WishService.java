package com.miniproject.domain.wish.service;

import com.miniproject.domain.accommodation.entity.Accommodation;
import com.miniproject.domain.accommodation.exception.AccommodationNotFoundException;
import com.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.miniproject.domain.member.entity.Member;
import com.miniproject.domain.member.exception.MemberNotFoundException;
import com.miniproject.domain.member.service.MemberService;
import com.miniproject.domain.wish.entity.Wish;
import com.miniproject.domain.wish.exception.AlreadyWishException;
import com.miniproject.domain.wish.exception.WishNotFoundException;
import com.miniproject.domain.wish.repository.WishRepository;
import com.miniproject.global.resolver.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.miniproject.domain.wish.dto.WishResponses.AccommodationWishResDto;

@RequiredArgsConstructor
@Transactional
@Service
public class WishService {

    private final WishRepository wishRepository;
    private final AccommodationRepository accommodationRepository;
    private final MemberService memberService;

    public Long saveWish(Long accommodationId, LoginInfo loginInfo) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(AccommodationNotFoundException::new);

        Member member = memberService.getMemberByLoginInfo(loginInfo);

        if (isAlreadyWish(accommodation, member)) {
            throw new AlreadyWishException();
        }

        Wish wish = Wish.builder()
                .accommodation(accommodation)
                .member(member).build();

        Wish saved = wishRepository.save(wish);
        accommodation.plusWishCount();
        return saved.getId();
    }

    private boolean isAlreadyWish(Accommodation accommodation, Member member) {
        return wishRepository.findByMemberAndAccommodation(member, accommodation).isPresent();
    }

    public void deleteWish(Long accommodationId, LoginInfo loginInfo) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(AccommodationNotFoundException::new);

        Member member = memberService.getMemberByLoginInfo(loginInfo);

        Wish wish = wishRepository.findByMemberAndAccommodation(member, accommodation)
                .orElseThrow(WishNotFoundException::new);

        wishRepository.delete(wish);
        accommodation.minusWishCount();
    }

    public List<Long> getWishesOnlyAccommodationId(LoginInfo loginInfo) {

        Member member = memberService.getMemberByLoginInfo(loginInfo);

        List<Wish> wishes = wishRepository.findAllByMember(member)
                .orElseThrow(MemberNotFoundException::new);

        return wishes.stream()
                .map(Wish::getAccommodation)
                .map(Accommodation::getId)
                .collect(Collectors.toList());
    }

    public List<AccommodationWishResDto> getWishes(LoginInfo loginInfo) {

        Member member = memberService.getMemberByLoginInfo(loginInfo);

        List<Wish> wishes = wishRepository.findAllByMember(member)
                .orElseThrow(MemberNotFoundException::new);

        return wishes.stream()
                .map(AccommodationWishResDto::fromEntity)
                .collect(Collectors.toList());
    }
}
