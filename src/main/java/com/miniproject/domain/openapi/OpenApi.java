package com.miniproject.domain.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniproject.domain.openapi.dto.AccommodationCommon;
import com.miniproject.domain.openapi.dto.AccommodationDetailInfo;
import com.miniproject.domain.openapi.dto.AccommodationIntro;
import com.miniproject.domain.openapi.service.ApiSaveService;
import com.miniproject.domain.openapi.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenApi {

    private final UrlService urlService;
    private final ApiSaveService apiSaveService;

    @EventListener(ApplicationReadyEvent.class)
    public void GetOpenApiData() throws JsonProcessingException {

        List<String> contentIdList = urlService.getContentId().block();

        List<AccommodationCommon> accommodationCommons;
        List<AccommodationIntro> accommodationIntros;
        List<AccommodationDetailInfo> accommodationDetailInfos;
        if(contentIdList != null) {
            accommodationCommons = urlService.getAccommodationCommon(contentIdList).block();
            System.out.println("25% 완료");
            accommodationIntros = urlService.getAccommodationIntro(contentIdList).block();
            System.out.println("50% 완료");
            accommodationDetailInfos = urlService.getAccommodationDetailInfo(contentIdList).flatMap(Flux::fromIterable).collectList().block();
            System.out.println("75% 완료");
            apiSaveService.saveAccommodations(accommodationCommons, accommodationIntros, accommodationDetailInfos);
            System.out.println("100% 완료");

        }


    }


}
