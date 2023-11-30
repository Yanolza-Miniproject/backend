package com.miniproject.domain.openapi.controller;

import com.miniproject.domain.openapi.dto.AccommodationCommon;
import com.miniproject.domain.openapi.dto.AccommodationDetailInfo;
import com.miniproject.domain.openapi.dto.AccommodationIntro;
import com.miniproject.domain.openapi.dto.OpenApiPostDTO;
import com.miniproject.domain.openapi.service.ApiSaveService;
import com.miniproject.domain.openapi.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/openapi")
@RestController
public class OpenApiController {

    private final UrlService urlService;
    private final ApiSaveService apiSaveService;

    @PostMapping()
    public ResponseEntity<String> createData(@RequestBody OpenApiPostDTO openApiPostDTO) {

        for(int i = openApiPostDTO.numOfStartPage(); i < openApiPostDTO.numOfEndPage(); i++) {

            System.out.println( i+1 + " 번째 페이지 작업 중");

            List<String> contentIdList = urlService.getContentId(openApiPostDTO.numOfRows(), i).block();

            List<AccommodationCommon> accommodationCommons;
            List<AccommodationIntro> accommodationIntros;
            List<AccommodationDetailInfo> accommodationDetailInfos;

            if(contentIdList != null) {
                accommodationCommons = urlService.getAccommodationCommon(contentIdList).block();
                System.out.println("25% 완료 : 숙소id 추출완료");
                accommodationIntros = urlService.getAccommodationIntro(contentIdList).block();
                System.out.println("50% 완료 : 숙소세부정보 추출완료");
                accommodationDetailInfos = urlService.getAccommodationDetailInfo(contentIdList).flatMap(Flux::fromIterable).collectList().block();
                System.out.println("75% 완료 : 객실정보 추출완료");
                apiSaveService.saveAccommodations(accommodationCommons, accommodationIntros, accommodationDetailInfos);
                System.out.println("100% 완료 : " + i+1 + "번째 페이지 데이터베이스 저장완료");
            }
        }
        return ResponseEntity.ok("데이터 저장 완료");
    }

}
