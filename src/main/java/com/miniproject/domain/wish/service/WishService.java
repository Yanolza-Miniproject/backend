//package com.miniproject.domain.wish.service;
//
//import com.miniproject.domain.accommodation.entity.Accommodation;
//import com.miniproject.domain.accommodation.repository.AccommodationRepository;
//import com.miniproject.domain.member.entity.Member;
//import com.miniproject.domain.wish.dto.WishResponses;
//import com.miniproject.domain.wish.entity.Wish;
//import com.miniproject.domain.wish.exception.AlreadyWishException;
//import com.miniproject.domain.wish.exception.WishNotFoundException;
//import com.miniproject.domain.wish.repository.WishRepository;
//import com.miniproject.global.exception.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.miniproject.domain.wish.dto.WishResponses.*;
//
//@RequiredArgsConstructor
//@Transactional
//@Service
//public class WishService {
//
//    private final WishRepository wishRepository;
//    private final AccommodationRepository accommodationRepository;
//
//    public void saveWish(Long accommodationId, Member loginMember) {
//        Accommodation accommodation = accommodationRepository.findById(accommodationId)
//                .orElseThrow(() -> new AccommodationNotFoundException("해당 숙소가 존재하지 않습니다 : " + accommodationId));
//
//        if (isAlreadyWish(accommodation, loginMember)) {
//            throw new AlreadyWishException();
//        }
//
//        Wish wish = Wish.builder()
//                .accommodation(accommodation)
//                .member(loginMember).build();
//
//        wishRepository.save(wish);
//        // 좋아요 개수를 늘리는 로직이 필요한가?
////        accommodation.
//    }
//
//    private boolean isAlreadyWish(Accommodation accommodation, Member member) {
//        return wishRepository.findByMemberAndAccommodation(member, accommodation).isPresent();
//    }
//
//    public void deleteWish(Long accommodationId, Member member) {
//        Accommodation accommodation = accommodationRepository.findById(accommodationId)
//                .orElseThrow(() -> new AccommodationNotFoundException("해당 숙소가 존재하지 않습니다 : " + accommodationId));
//
//        Wish wish = wishRepository.findByMemberAndAccommodation(member, accommodation)
//                .orElseThrow(WishNotFoundException::new);
//
//        wishRepository.delete(wish);
//        // 좋아요 개수를 줄이는 로직이 필요한가?
//    }
//
//    public List<Long> getWishesOnlyAccommodationId(Member member) {
//        return wishRepository.findAllByMember(member)
//                .orElseThrow(NotFoundMemberException::new);
//    }
//
//    public List<AccommodationWishResDto> getWishes(Member member) {
//        List<Wish> wishes = wishRepository.findAllByMember(member)
//                .orElseThrow(NotFoundMemberException::new);
//
//        return wishes.stream()
//                .map(AccommodationWishResDto::fromEntity)
//                .collect(Collectors.toList());
//    }
//}
//
