package com.miniproject.domain.openapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.domain.accommodation.entity.AccommodationType;
import com.miniproject.domain.openapi.dto.AccommodationCommon;
import com.miniproject.domain.openapi.dto.AccommodationDetailInfo;
import com.miniproject.domain.openapi.dto.AccommodationIntro;
import com.miniproject.domain.openapi.dto.RoomImageUrlDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UrlService {

    ObjectMapper objectMapper = new ObjectMapper();

    String secretKey = "6NKGVwiUsALByF989ns0LeGMRJ5r%2F%2BZdKF1i6CvWsD7bW13srHcst1xDVutnXPhtZ36Xa00PMZRRfbMuXaUk2g%3D%3D";

    // 숙소 리스트
    public Mono<List<String>> getContentId() {
        String accommodationListUrl = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";

        List<String> contentIdList = new ArrayList<>();

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(accommodationListUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient1 = WebClient.builder()
                .uriBuilderFactory(factory)
                .baseUrl(accommodationListUrl)
                .build();

        return webClient1.get()
//                .uri(accommodationListUrl)
                .uri(uriBuilder -> uriBuilder
//                        .path("/areaBasedList1")
                        .queryParam("numOfRows", 10)
                        .queryParam("pageNo", 30)
                        .queryParam("MobileOS", "WIN")
                        .queryParam("MobileApp", "mini")
                        .queryParam("_type", "json")
                        .queryParam("contentTypeId", 32)
                        .queryParam("serviceKey", secretKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        JsonNode root = objectMapper.readTree(response);
                        JsonNode items = root.path("response").path("body").path("items").path("item");

                        for (JsonNode item : items) {
                            String contentid = item.get("contentid").asText();
                            contentIdList.add(contentid);
                        }
                        return Mono.just(contentIdList);
                    } catch (Exception e) {
                        if (e.getMessage().contains("SERVICE_KEY_IS_NOT_REGISTERED_ERROR")) {
                            System.err.println("Error to get content_id Retrying...");
                        }
                        return Mono.error(e);
                    }
                })
                .retryWhen(Retry.backoff(100, Duration.ofMillis(100)));
    }

    // 공통정보 (기본 정보)
    public Mono<List<AccommodationCommon>> getAccommodationCommon(List<String> contentIdList) {

        List<Mono<AccommodationCommon>> accommodationCommons = new ArrayList<>();

        for (String contentId : contentIdList) {

            String accommodationCommonUrl = "https://apis.data.go.kr/B551011/KorService1/detailCommon1";

            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(accommodationCommonUrl);
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

            WebClient webClient2 = WebClient.builder()
                    .uriBuilderFactory(factory)
                    .baseUrl(accommodationCommonUrl)
                    .build();

            Mono<AccommodationCommon> accommodationCommon = webClient2.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("MobileOS", "WIN")
                            .queryParam("MobileApp", "mini")
                            .queryParam("_type", "json")
                            .queryParam("contentId", contentId)
                            .queryParam("contentTypeId", 32)
                            .queryParam("defaultYN", "Y")
                            .queryParam("areacodeYN", "Y")
                            .queryParam("firstImageYN", "Y")
                            .queryParam("catcodeYN", "Y")
                            .queryParam("addrinfoYN", "Y")
                            .queryParam("mapinfoYN", "Y")
                            .queryParam("overviewYN", "Y")
                            .queryParam("serviceKey", secretKey)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(response -> {
                        try {
                            JsonNode root = objectMapper.readTree(response);
                            JsonNode item = root.path("response").path("body").path("items").path("item").get(0);

                            return Mono.just(
                                    AccommodationCommon.builder()
                                            .contentid(item.get("contentid").asText())
                                            .title(item.get("title").asText())
                                            .tel(item.get("tel").asText())
                                            .homepage(extractUrlFromHtml(item.get("homepage").asText()))
                                            .firstimage(item.get("firstimage").asText())
                                            .addr1(item.get("addr1").asText())
                                            .addr2(item.get("addr2").asText())
                                            .mapx(item.get("mapx").asText())
                                            .mapy(item.get("mapy").asText())
                                            .overview(item.get("overview").asText())
                                            .accommodationType(AccommodationType.fromCode(item.get("cat3").asText()))
                                            .build()
                            );
                        } catch (Exception e) {
                            if (e.getMessage().contains("SERVICE_KEY_IS_NOT_REGISTERED_ERROR")) {
                                System.err.println("Error to get Common of contentId : " + contentId + ", Retry getAccommodationCommon...");
                            } else {
                                return Mono.empty();
                            }
                            return Mono.error(e);
                        }
                    })
                    .retryWhen(Retry.backoff(100, Duration.ofMillis(1000)));

            accommodationCommons.add(accommodationCommon);
        }

        return Mono.when(accommodationCommons).then(Mono.just(accommodationCommons.stream().map(Mono::block).collect(Collectors.toList())));
    }

    // 공통정보 (기본 정보)
    public Mono<List<AccommodationIntro>> getAccommodationIntro(List<String> contentIdList) {

        List<Mono<AccommodationIntro>> accommodationIntros = new ArrayList<>();

        for (String contentId : contentIdList) {
            String accommodationIntroUrl = "https://apis.data.go.kr/B551011/KorService1/detailIntro1";

            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(accommodationIntroUrl);
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

            WebClient webClient3 = WebClient.builder()
                    .uriBuilderFactory(factory)
                    .baseUrl(accommodationIntroUrl)
                    .build();

            Mono<AccommodationIntro> accommodationIntro = webClient3.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("MobileOS", "WIN")
                            .queryParam("MobileApp", "mini")
                            .queryParam("_type", "json")
                            .queryParam("contentId", contentId)
                            .queryParam("contentTypeId", 32)
                            .queryParam("serviceKey", secretKey)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(response -> {
                        try {
                            JsonNode root = objectMapper.readTree(response);
                            JsonNode item = root.path("response").path("body").path("items").path("item").get(0);
                            return Mono.just(
                                    AccommodationIntro.builder()
                                            .contentid(item.get("contentid").asText())
                                            .roomcount(item.get("roomcount").asInt())
                                            .accomcountlodging(item.get("accomcountlodging").asInt())
                                            .scalelodging(item.get("scalelodging").asInt())
                                            .roomtype(item.get("roomtype").asText())
                                            .checkintime(LocalTime.parse(item.get("checkintime").asText()))
                                            .checkouttime(LocalTime.parse(item.get("checkouttime").asText()))
                                            .parkinglodging("가능".equals(item.get("parkinglodging").asText()))
                                            .chkcooking("가능".equals(item.get("chkcooking").asText()))
                                            .pickup("가능".equals(item.get("pickup").asText()))
                                            .foodplace(item.get("foodplace").asText())
                                            .subfacility(item.get("subfacility").asText())
                                            .build()
                            );
                        } catch (Exception e) {
                            if (e.getMessage().contains("SERVICE_KEY_IS_NOT_REGISTERED_ERROR")) {
                                System.err.println("Error to get Intro of contentId : " + contentId + ", Retry getAccommodationIntro...");
                            } else {
                                return Mono.empty();
                            }
                            return Mono.error(e);
                        }
                    })
                    .retryWhen(Retry.backoff(100, Duration.ofMillis(1000)));

            accommodationIntros.add(accommodationIntro);
        }

        return Mono.when(accommodationIntros).then(Mono.just(accommodationIntros.stream().map(Mono::block).collect(Collectors.toList())));
    }

    public Flux<List<AccommodationDetailInfo>> getAccommodationDetailInfo(List<String> contentIdList) {

//        List<AccommodationDetailInfo> accommodationDetailInfos = new ArrayList<>();
        // 해당 리스트에 대한 동시 접근에 대비한 synchronizedList를 이용한 선언
        // 지금은 저장과 순회가 별개로 이루어지지만 동시에 이루어진다면 순회 시 synchronized로 동기화 블록을 해야한다. << 나중에 더 알아보자
        List<AccommodationDetailInfo> accommodationDetailInfos = Collections.synchronizedList(new ArrayList<>());

        return Flux.fromIterable(contentIdList)
                .flatMap(contentId -> {

                    String accommodationDetailInfoUrl = "https://apis.data.go.kr/B551011/KorService1/detailInfo1";

                    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(accommodationDetailInfoUrl);
                    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

                    WebClient webClient4 = WebClient.builder()
                            .uriBuilderFactory(factory)
                            .baseUrl(accommodationDetailInfoUrl)
                            .build();

                    return webClient4.get()
                            .uri(uriBuilder -> uriBuilder
                                    .queryParam("MobileOS", "WIN")
                                    .queryParam("MobileApp", "mini")
                                    .queryParam("_type", "json")
                                    .queryParam("contentId", contentId)
                                    .queryParam("contentTypeId", 32)
                                    .queryParam("serviceKey", secretKey)
                                    .build())
                            .retrieve()
                            .bodyToMono(String.class)
                            // concatMap 만약 이거 관련 오류 발생 시
                            .flatMap(response -> {
                                try {
                                    JsonNode root = objectMapper.readTree(response);
                                    JsonNode items = root.path("response").path("body").path("items").path("item");

                                    List<RoomImageUrlDto> roomImageUrlDtos = new ArrayList<>();

                                    for (JsonNode item : items) {
                                        for (int i = 0; i < 5; i++) {
                                            JsonNode imageUrlNode = item.get("roomimg" + i);
                                            if (imageUrlNode != null && !imageUrlNode.asText().isEmpty()) {
                                                String imageUrl = imageUrlNode.asText();
                                                roomImageUrlDtos.add(new RoomImageUrlDto(imageUrl));
                                            }
                                        }
                                        accommodationDetailInfos.add(
                                                AccommodationDetailInfo.builder()
                                                        .contentid(item.get("contentid").asText())
                                                        .roomtitle(item.get("roomtitle").asText())
                                                        .roomsize1(item.get("roomsize1").asInt())
                                                        .roomcount(item.get("roomcount").asInt())
                                                        .roombasecount(item.get("roombasecount").asInt())
                                                        .roomoffseasonminfee1(item.get("roomoffseasonminfee1").asInt())
                                                        .roomoffseasonminfee2(item.get("roomoffseasonminfee2").asInt())
                                                        .roompeakseasonminfee1(item.get("roompeakseasonminfee1").asInt())
                                                        .roompeakseasonminfee2(item.get("roompeakseasonminfee2").asInt())
                                                        .roomtv("Y".equals(item.get("roomtv").asText()))
                                                        .roompc("Y".equals(item.get("roompc").asText()))
                                                        .roominternet("Y".equals(item.get("roominternet").asText()))
                                                        .roomrefrigerator("Y".equals(item.get("roomrefrigerator").asText()))
                                                        .roombathfacility("Y".equals(item.get("roombathfacility").asText()))
                                                        .roomhairdryer("Y".equals(item.get("roomhairdryer").asText()))
                                                        .roomImages(roomImageUrlDtos)
                                                        .build()
                                        );
                                    }
                                    return Mono.just(accommodationDetailInfos);
                                } catch (Exception e) {
                                    if (e.getMessage().contains("SERVICE_KEY_IS_NOT_REGISTERED_ERROR")) {
                                        System.err.println("Error to get DetailInfo of contentId : " + contentId + ", Retry getAccommodationDetailInfo...");
                                        return Mono.error(e);
                                    } else {
                                        return Mono.empty();
                                    }
                                }
                            })
                            .retryWhen(Retry.backoff(100, Duration.ofMillis(1000)));
                });
    }

    private String extractUrlFromHtml(String address) {
        if (address.startsWith("<a href=\"")) {
            Document doc = Jsoup.parse(address);
            Element link = doc.select("a").first();
            String url = link != null ? link.attr("href") : "";
            return url.startsWith("http://") ? url.substring(7) : url;
        } else {
            return address.startsWith("http://") ? address.substring(7) : address;
        }
    }


}
