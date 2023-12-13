<h1>[4조] 미니 프로젝트 : 숙박 예약 서비스</h1>

## 목차
1. [조원](#조원)
2. [프로젝트 설명](#프로젝트-설명)
3. [개발 환경](#개발-환경)
4. [EER (논리적 모델링)](#EER-(논리적-모델링))
5. [패키지 구조](#패키지-구조)
6. [DB 구조 설명](#DB-구조-설명)
7. [인프라 구조](#인프라-구조)
8. [스프링 프로젝트 실행 화면](#스프링-프로젝트-실행-화면)
9. [API 설계](#API-설계)
10. [API 문서 URL](#API-문서-URL)
11. [느낀점](#느낀점)
12. [프로젝트 보완 내용](#프로젝트-보완-내용)


<h2>조원</h2>


| 이름 | 역할 |
| --- | --- |
| 권주환 | 조장, 장바구니, 주문, 결제 기능 개발 및 배포 담당 |
| 박준모 | 숙소 및 객실 기능 개발, Open API 개발 담당 |
| 김동민 | 좋아요 기능 개발 및 전체 도메인 담당 |
| 이민균 | 로그인 및 회원가입 Spring Security 담당 |

<h2>프로젝트 설명</h2>

- 진행 기간 : 2023.11.20 ~ 2023.12.01 (14일간)
- OpenApi를 활용하여 숙박, 객실 정보를 받아와 AWS RDS에 저장 및 조회할 수 있는 프로젝트
- Spring Security, JWT로 회원가입, 로그인, 로그아웃 기능 구현
- `MiniProjectApplication` 클래스의 main메서드를 실행시키면 어플리케이션이 구동됩니다.
- AWS EC2에 스프링 서버를 배포하였습니다.


<h2>개발 환경</h2>

<img src="https://img.shields.io/badge/java-007396?style=flat&logo=Java&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/mysql-007396?style=flat&logo=mysql&logoColor=white">

<img src="https://img.shields.io/badge/Lombok-02569B?style=flat&logo=Lombok&logoColor=white"> <img src="https://img.shields.io/badge/gson-007396?style=flat&logo=gson&logoColor=white">

<img src="https://img.shields.io/badge/Junit5-25A162?style=flat&logo=Junit5&logoColor=white"> <img src="https://img.shields.io/badge/Spring-25A162?style=flat&logo=Spring&logoColor=white">  <img src="https://img.shields.io/badge/H2-FF5722?style=flat&logo=H2"> <img src="https://img.shields.io/badge/Spring_Boot-25A162?style=flat&logo=SpringBoot&logoColor=white">

<img src="https://img.shields.io/badge/SpringSecurity-0073?style=flat&logo=SpringSecurity&logoColor=white">
<img src="https://img.shields.io/badge/Postman-344?style=flat&logo=Postman&logoColor=white">

<img src="https://img.shields.io/badge/RDS-527FFF?style=flat&logo=amazonrds&logoColor=white">
<img src="https://img.shields.io/badge/AWS-232F3E?style=flat&logo=amazonaws&logoColor=#232F3E">
<img src="https://img.shields.io/badge/git-F05032?style=flat&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white">

의존성(자세한 내용은 [build.gradle](https://www.notion.so/build.gradle)을 참고해주세요!)
  - `data-JPA`
  - `validation`
  - `security`
  - `lombok`
  - `jjwt`
  - `querydsl`
  - `webflux`

<h2>EER (논리적 모델링)</h2>

![Screen Shot 2023-11-30 at 4 25 33 PM](https://github.com/CodeLab4/lab4_algorithm_java/assets/85631282/71fc9645-65e1-42c7-8a80-55dfccc4050b)


<h2>패키지 구조</h2>

```
**miniproject**
├── domian
│   ├── accommodation
│   │   ├── controller
│   │   ├── dto
│   │   ├── entity
│   │   ├── repository
│   │   └── service
│   ├── basket
│   │   ├── controller
│   │   ├── dto
│   │   ├── entity
│   │   ├── exception
│   │   ├── repository
│   │   └── service
│   ├── member
│   │   ├── controller
│   │   ├── entity
│   │   ├── request
│   │   ├── exception
│   │   ├── repository
│   │   └── service
│   ├── openapi
│   │   ├── controller
│   │   ├── dto
│   │   └── service
│   ├── order
│   │   ├── controller
│   │   ├── exception
│   │   ├── entity
│   │   ├── repository
│   │   └── service
│   ├── orders
│   │   ├── controller
│   │   ├── dto
│   │   ├── exception
│   │   ├── entity
│   │   ├── repository
│   │   └── service
│   ├── payment
│   │   ├── controller
│   │   ├── dto
│   │   ├── exception
│   │   ├── entity
│   │   ├── repository
│   │   └── service
│   ├── refresh
│   │   ├── controller
│   │   ├── exception
│   │   ├── entity
│   │   └── repository
│   ├── room
│   │   ├── controller
│   │   ├── entity
│   │   ├── dto
│   │   ├── exception
│   │   ├── repository
│   │   └── service
│   ├── wish
│   │   ├── controller
│   │   ├── entity
│   │   ├── dto
│   │   ├── exception
│   │   ├── repository
│   │   └── service
├── global
│   ├── config
│   ├── exception
│   ├── jwt
│   │   ├── api
│   │   ├── exception
│   │   ├── repository
│   │   └── service
│   ├── security
│   │   ├── jwt
│   │   └── login
└───└── util
```

<h2>DB 구조 설명</h2>

- `Acommodation`과 `Room` 릴레이션은 1:n 관계를 가집니다.
- `Member`과 `Wish` 릴레이션은 1:n 관계를 가집니다.
- `Accommodation`과 `Wish` 릴레이션은 1:n 관계를 가집니다.
- `Room`과 `Basket` 릴레이션은 다대다 관계이기 때문에 `Room-Basket` 릴레이션을 두어 1:n, n:1 관계로 만들었습니다.
- `Itinerary` 릴레이션은 `Movement`, `Visit`, `Accomodation` 릴레이션과 1:1 관계를 가집니다.
- `Basket`과 `Member`는 1:1 관계를 가집니다.
- `Room`과 `Orders` 사이에 `Room-Orders` 릴레이션을 배치했습니다.
- `Orders`와 `Payment` 릴레이션은 1:1 관계를 가집니다.


<h2>인프라 구조</h2>

![Screen Shot 2023-11-30 at 3 09 27 PM](https://github.com/meena2003/inflearn_spring/assets/85631282/2240d899-8b14-4b7a-a14e-e2156feb2189)

<h2>스프링 프로젝트 실행 화면</h2>

![프로젝트 시작](https://github.com/meena2003/inflearn_spring/assets/85631282/0b526da6-c477-419b-bcab-2238734cef1e)

<h2>API 설계</h2>

![Screen Shot 2023-11-30 at 3 53 21 PM](https://github.com/meena2003/inflearn_spring/assets/85631282/8d407f4b-a30a-4648-8aef-e8240cca41fc)

<h2>API 문서 URL</h2>

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/26706438/2s9YeG6XLU#intro)

<h2>느낀점</h2>

- **권주환**: 백엔드와 프론트 협업은 역시 늘 어렵고 힘들다고 느낀다. 서로의 알고 있는 지식이 다르고, 서로의 이해 관계가 다른 것으로부터 시작되기 때문에, 트러블이 발생하지 않는 협업 개발을 하는 것은 불가능하다는 것을 느꼈다. 특히, 배포를 하고 난 후, 실제 API 테스트로 넘어왔을 때, CORS 문제, 로컬에서는 터지지 않던 에러들을 확인하는데 많은 에너지가 쓰이는 것 같다.  다음에 협업할 땐, 개발 단계와 테스트 단계를 나누어서 테스트 단계를 더 넉넉히 시간 조율을 해야함을 느꼈다.
- **박준모**: 본인 개발에 치여서 협업에 중요한 API 문서 작성등에 상대적으로 소홀히하게 되었다. swagger와 같은 자동 문서화 툴들의 필요성을 느낄 수 있었다.
- **이민균**: 개인의 코드 개발 실력이 부족해서 코드 생산 시간 및 퀄리티가 전체적인 계획에 따라가기 버거웠다. 부족한 부분을 좀 더 공부를 하고 기본에 충실한 코드 작성 연습을 해야겠다고 느꼈다.
프론트와 함께하는 경험은 어디가서도 쉽게 접할 수 없는 좋은 기회임을 느꼈다. 백엔드 개발자로서의 역할은 코드 개발도 중요하지만 자신의 생각을 타인에게 원활하고 쉽게 잘 풀어서 전달할 수 있는 능력이 필요하다고 느꼈다. 
- **김동민**: 프론트와 처음 협업한 프로젝트라서 걱정이 많았지만, 팀원들과 모르는 점을 함께 논의하며 잘 해결할 수 있었던 것 같습니다. 좋은 경험이 되었습니다!
  
---

<h1>프로젝트 보완 내용</h1>
<h2>에러 해결 방법</h2>
<details>
<summary>박준모</summary>

**OpenAPI servicekey 오류**

<img width="319" alt="Untitled" src="https://github.com/Yanolza-Miniproject/backend/assets/85631282/dda67405-c3c1-46c4-ba32-da8c00349bd4">
    
webclient가 자동 인코딩 기능 때문에 보안 상 나중에 `queryParam` 으로 추가하는 servicekey가 공공데이터 서버에서 인식안되는 상황이 발생 
    
<img width="367" alt="Untitled 1" src="https://github.com/Yanolza-Miniproject/backend/assets/85631282/29e53f64-7b5c-48ff-9450-4747b7456ed6">

이를 `EncodingMode` 를 *`NONE`* 으로 설정하여 인코딩이 설정을 꺼둔 뒤 요청을 보내 해결하였다.

</details>
<details>
<summary>권주환</summary>
상황

- 주문 취소를 위해, 주문 객체를 삭제하려고 했을 때, 참조 무결성으로 인해, 삭제 실패

![Untitled 2](https://github.com/Yanolza-Miniproject/backend/assets/85631282/a40bbd62-3a6f-4789-9568-8d6356aa3012)

원인

- 주문한 객실이 주문Id를 foreign key를 포함하고있어, 주문이 먼저 삭제되었을 때, 주문한 객실이 참조할 주문이 없어지기 때문에, 참조 무결성으로 인해, 삭제를 하지 못한다.

해결방법

- 주문한 객실 객체를 먼저 삭제 후, 주문 객체를 삭제

</details>
<details>
<summary>이민균</summary>


- 기간이 만료된 Refresh 토큰을 RDB에서 제거하는 기능 추가


![Untitled 3](https://github.com/Yanolza-Miniproject/backend/assets/85631282/a3bbe8a0-b8c0-41e6-b999-ed369546163b)


JPA의 **'Query Creation from Method Name'** 으로 매개변수로 LocalDateTime을 받고 해당 시간을 기준으로 만료기간이 이전인 모든 RefreshToken을 삭제하는 메서드를 추가했다.

- 테스트 과정에서의 오류

아래 코드는 검증을 위한 테스트 코드이다. 임의로 저장한 기간 만료 토큰이 잘 삭제되었나 검증하는 과정이다.

```java
@DisplayName("refreshToken을 재발급할 때 기간이 만료된 refreshToken들은 삭제된다.")
        @Test
        void reissuingRefreshToken_deletesExpiredRefreshTokens() {

            // given
            JwtPayload jwtPayload = new JwtPayload("test@email.com", new Date());
            TokenPair tokenPair = jwtService.createTokenPair(jwtPayload);

            var expiredTokenId = refreshTokenRepository.save(
                    RefreshToken.builder()
                            .memberEmail("test2@email.com")
                            .token("기간이만료된 토큰입니다.")
                            .expiryDate(LocalDateTime.MIN)
                            .build()).getId();

            // when then
            jwtService.refreshAccessToken(new RefreshTokenRequest(tokenPair.refreshToken()));
            refreshTokenRepository.deleteRefreshTokensByExpiryDateBefore(LocalDateTime.now());

            assertThrows(NoSuchElementException.class, () -> refreshTokenRepository.findById(expiredTokenId).get());
        }
```
![Untitled 4](https://github.com/Yanolza-Miniproject/backend/assets/85631282/96f736d0-a3be-484e-8aab-d0965242e42c)


위와 같이 쿼리문이 잘 날아가긴 하지만 테스트는 실패했다. 이유는 LocalDateTime.MIN 이다.

LocalDateTime.MIN을 출력해보면 아래와 같다. 

![Untitled 5](https://github.com/Yanolza-Miniproject/backend/assets/85631282/c98af210-d658-4cf5-a88c-77f51037e07d)


하지만 실제 DB에 저장되는 LocalDateTime은 다음과 같다.

![Untitled 6](https://github.com/Yanolza-Miniproject/backend/assets/85631282/7094fc81-9463-4942-bc86-88ed4dc8983a)


개인적으로 테스트의 상황을 **극단적으로** 설정해서 진행하는 경우가 있었다.

만료 기간을 극단적인 과거로 설정해서 테스트를 하려는게 문제가 되었다. 

대부분의 데이터베이스 시스템에서는 연도 범위를 기원전 4712년 ~ 기원후 9999년 사이로 제한한다. 극단적인 LocalDateTime.MIN의 범위는 지원하지 않으므로 실제로 이상한 값이 저장되는 이유였다. 

```java
.expiryDate(LocalDateTime.of(2000, 12, 12, 12, 12))
```

위와 같이 테스트 과정에서 토큰을 저장할 때, 적당한 만료 기간을 설정하여 해결했다.

</details>
<details>
<summary>김동민</summary>

`**Wish**` 엔티티와 숙박(`Accommodation`), 사용자(`Member`) 엔티티와의 연관관계를 맺고 테스트를 돌려보면 실패가 뜬다.

![Screen_Shot_2023-12-12_at_11 25 27_AM](https://github.com/Yanolza-Miniproject/backend/assets/85631282/c3673237-cdcf-488c-9414-170e26989fc0)


`InvalidDataAccessApiUsageException`이 발생한다. 

![Screen_Shot_2023-12-12_at_11 27 38_AM](https://github.com/Yanolza-Miniproject/backend/assets/85631282/806ff33c-ac1a-417f-9450-4448296b3c69)


예외가 터진 곳을 살펴보면 `findMyMemberAndAccommodation()` 테스트 메소드에서 멤버와 숙박 정보를 이용해서 좋아요를 찾는 곳이다.

![Screen_Shot_2023-12-12_at_11 29 25_AM](https://github.com/Yanolza-Miniproject/backend/assets/85631282/e10674d1-b385-4ef3-9bea-23ec8c71ea48)


원인을 분석하면, Hibernate에서 영속성 컨텍스트에서 관리되지 않는 엔티티를 참조해서 발생하는 것이다. 

이를 해결하기 위해선 연관관계의 속성에 `cascade` 옵션을 추가하여 부모 엔티티가 저장될 때 자식 엔티티도 함께 저장되도록 한다.

![Screen_Shot_2023-12-12_at_11 32 23_AM](https://github.com/Yanolza-Miniproject/backend/assets/85631282/fa60086f-1e66-4b92-b1ba-46fcdd41d5a9)


cascade 옵션 값을 `CascadeType.PERSIST`라고 하면 부모 엔티티가 영속 상태로 전활될 떄 자식 엔티티도 함꼐 영속 상태로 전환된다. 즉, **`Wish`** 엔티티가 저장될 때 관련된 **`Accommodation`** 및 **`Member`** 엔티티도 함께 저장된다.

다시 테스트를 돌려보면 모두 해결됐다.

![Screen_Shot_2023-12-12_at_11 36 08_AM](https://github.com/Yanolza-Miniproject/backend/assets/85631282/2fb09088-6f73-45d6-8bb1-b842c89d539c)


</details>


<h2>개인 역량회고</h2>
<details>
<summary>박준모</summary>

- 경험과 지식이 모자른 부분을 구현할 때는 여지없이 시간이 너무 소모됨
- Dto, Entity 등 프로젝트 구조를 구현하는데 있어서 철학, 개념이 부족하여, 의미없는 코드가 있거나 각 요소들의 장점을 퇴색시키는 형태로 구현한 부분들이 있음.
- 앞서 말한 실력 부족으로 인한 시간 부족으로 인해 자신의 코드 리뷰는 물론 팀원들의 코드를 리뷰를 제대로 하지 못했다. 그렇다보니 당연한 부분에서의 실수와, 비 효율적인 코드들이 후에 확인되었다.

</details>
<details>
<summary>권주환</summary>

- 팀장으로서 해야 할 일들을 모두 하지 못한 것 같다.
    - 문서화 작업이나 팀원들의 진행도 파악, 팀원들을 도와주는 팀장으로서 할 일을 수행해내지 못한 것 같다.
- 팀원들의 코드 리뷰를 진행하지 못했다
    - 팀원들간의 코드 리뷰도 성장을 위해, 매우 중요한 일임을 알면서도, 뒤로 미루게 되고 일단 기능 완성 후 배포가 목표이다보니 그 외의 것들에 대해 신경을 쓰지 못했다.

</details>
<details>
<summary>이민균</summary>

- 개인 코드 작성에 벅차 팀원들의 코드 진행 상황을 확인하지 못했다. 멘토님께서  개인으로 코딩하는 시간과 팀원의 코드를 리뷰 하는 시간의 비중은 7:3, 6:4가 가장 이상적이라고 하셨다. 그 이유를 조금은 알 수 있는 경험이었다.
- 개인적인 개발 진행 단계를 세분화 하지 못했다. 하나의 목표를 여러 branch와 pull request로 쪼개는 연습이 필요할 것 같다.

</details>
<details>
<summary>김동민</summary>

- 팀원들의 코드를 리뷰하지 못한 게 아쉽다. 시간적인 여유도 원인이지만 다른 사람의 코드를 리뷰할만큼의 지식과 확신이 없었다. 내가 리뷰하는 부분에 대해 정말 더 좋은 코드인지, 방법인지 잘 몰라서 리뷰에 더 신경을 쓰지 않은 점도 있다.
- 내가 모르는 부분에 관심이 적었다. 내가 구현한 부분 이외의 코드에 대해 알기 위해서 상대적으로 시간을 쏟지 않았다. 프로젝트는 내 코드 이외에도 전체적인 흐름을 이해해야 하는데 그런 부분이 아쉽다.
- 테스트 코드 작성이 정말 어려웠다. 좋아요 기능이라는 작은 부분임에도 테스트 코드를 작성하는데 굉장히 어려움을 겪었다. 특히 각 계층마다 무엇을 테스트 해야 하는지가 명확히 이해되지 않았다. 테스트 코드를 작성하는 기술적인 부분보다 어떤 것을 테스트 해야 할지 스스로도 애매해서 작성이 어려웠다.
- DTO 설계를 어떻게 해야 할지 고민이 된다. 어떤 구조가 더 효율적인지 고민을 해봐야겠다.

</details>

<h2>보완 기능</h2>
<details>
<summary>박준모</summary>

### **숙소 열람 기능에 잘못된 숙소 열람 시 예외처리 추가**

**이전 코드**

<img width="370" alt="Untitled 7" src="https://github.com/Yanolza-Miniproject/backend/assets/85631282/c16ea02f-8d73-4af7-beb1-ad50a0daa6fe">


기존에는 조회 시 적용되는 필터링 내용을 일일이 `@RequestParam` 을 이용하여 받아내었다. 
그리고 page 관련 정보도 불필요하게 따로 받아내었다.

이 불필요한 코드를 수정하였다.

**개선 코드**

<img width="148" alt="Untitled 8" src="https://github.com/Yanolza-Miniproject/backend/assets/85631282/1b1b8a06-1cae-4802-9e92-8c8ea3708a0e">


먼저 Request DTO를 생성하여 `@RequestParam` 을 통해 일일이 받아냈던 속성들을 한 번에 받아내고

<img width="363" alt="Untitled 9" src="https://github.com/Yanolza-Miniproject/backend/assets/85631282/c688ae61-5418-4963-904e-8191f965b5be">


`@ModelAttribute` 를 통해서 DTO 객체를 통해 `@RequestParam` 내용을 받아낸다.

또한 page 만 따로 받지 않고, Pageable 객체로 관련 정보를 한 번에 받아온다. Page객체에 대한 특별한 설정이 없는 이상 이렇게 하는 것이 깔끔하다.

</details>
<details>
<summary>권주환</summary>

### 장바구니 가져오는 메소드 변경

- **변경 전**

![Untitled 10](https://github.com/Yanolza-Miniproject/backend/assets/85631282/84ba9945-2283-4109-bb1d-c899532c9526)


멤버당 하나의 장바구니만을 가져오는 메소드: getActivateBasket()에서 DB 동시 접근으로 인한, 장바구니 중복 생성에 대해서 예외를 던지고 끝났었다.

문제: 예외를 던지고 끝나면, DB 동시성 오류가 생긴 멤버는 장바구니에 접근을 할 수 없다 → 두개 이상 장바구니가 만들어졌을 때, 하나의 장바구니만을 만들어주는 로직 추가 필요

- **변경 후**

![Untitled 11](https://github.com/Yanolza-Miniproject/backend/assets/85631282/b03c622a-4a75-4517-8aac-a0ccfc9798a8)


예외를 던지는 것이 아닌 deleteBasket() 메소드를 하나 더 만들어서, 두 개 이상 생성된 장바구니에 담긴 객실들을 모두 꺼내와서 새롭게 하나의 장바구니를 만들고, 새로운 장바구니에 담겨있던 객실들을 옮겨주고, 기존 생성된 장바구니를 모두 삭제함 → 장바구니에 담은 객실들도 모두 유지하면서 장바구니가 두 개 이상 생성될 때의 오류를 해결하였다.

### **CI / CD 구축**

**변경 전**

 ec2 서버에 jar 파일을 올린 뒤 nohup 명령어를 통해, 백그라운드 프로젝트 실행을 하였습니다.

**변경 후**

docker + git actions를 이용한 CI / CD를 구축하였습니다.

build가 성공적으로 되면, docker를 이용해, ec2 서버로 배포되도록 구축하였습니다.

**gradle-actions.yml 파일**

```
name: Java CI with Gradle

on:
	push:
		branches: [ "develop" ]
	pull_request:
		branches: [ "develop" ]

permissions:
	contents: read

jobs:
	build:

runs-on: ubuntu-latest

steps:
- uses: actions/checkout@v3
- name: Set up JDK 17
  uses: actions/setup-java@v3
  with:
    java-version: '17'
    distribution: 'temurin'
- name: Gradle Caching
  uses: actions/cache@v3
  with:
    path: |
      ~/.gradle/caches
      ~/.gradle/wrapper
    key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
    restore-keys: |
      ${{ runner.os }}-gradle-
- name: Build with Gradle
  uses: gradle/gradle-build-action@bd5760595778326ba7f1441bcf7e88b49de61a25 # v2.6.0
  with:
    arguments: build
 ## create application-prod.properties
- name: make application-prod.properties
  if: contains(github.ref, 'develop') # branch가 main 일 때, 나머지는 위와 동일
  run: |
      cd ./src/main/resources
      touch ./application-prod.properties
      echo "${{ secrets.PROPERTIES_PROD }}" > ./application-prod.properties
  shell: bash
   ## gradle build
- name: Build with Gradle
  run: ./gradlew build -x test -x ktlintCheck -x ktlintTestSourceSetCheck -x ktlintMainSourceSetCheck -x ktlintKotlinScriptCheck
## docker build & push to develop
- name: Docker build & push to dev
  if: contains(github.ref, 'develop')
  run: |
      docker login -u ${{ secrets.DOCKER_USERNAME }} -p ${{ secrets.DOCKER_PASSWORD }}
      docker build -f Dockerfile-dev -t ${{ secrets.DOCKER_REPO }} .
      docker push ${{ secrets.DOCKER_REPO }}
 ## deploy to production
- name: Deploy to prod
  uses: appleboy/ssh-action@master
  id: deploy-prod
  if: contains(github.ref, 'develop')
  with:
      host: ${{ secrets.HOST_PROD }}
      username: ec2-user
      key: ${{ secrets.PRIVATE_KEY }}
      envs: GITHUB_SHA
      script: |
          sudo docker rm -f $(docker ps -qa)
          sudo docker pull ${{ secrets.DOCKER_REPO }}
          docker-compose up -d
          docker image prune -f

```

</details>
<details>
<summary>이민균</summary>

RefreshToken을 통해 AccessToken을 재발급 받을 때, RDB에 기간이 만료된 RefreshToken을 제거하는 기능 추가

![Untitled 3](https://github.com/Yanolza-Miniproject/backend/assets/85631282/3dfffa60-14b5-4e2f-a7d1-ab5c5eac0162)

```java
/* JwtService */
public class JwtService {

public void deleteExpiredTokens() {
        refreshTokenRepository.deleteRefreshTokensByExpiryDateBefore(LocalDateTime.now());
    }
}

/* RefreshTokenRepository  */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteRefreshTokensByExpiryDateBefore(LocalDateTime expiryDate_date);
}
```

- 이번 미니프로젝트에서는 Refresh 토큰을 RDB에 저장하여 관리하였다. 보통 Refresh 토큰을 저장할 때 Redis에 저장한다. 각 방법의 특징은 아래와 같다.
    
    
    - Redis :
        - 메모리 기반의 데이터 저장소이기에 RDB 보다 빠른 응답 속도를 제공
        - 만료 시간을 설정할 수 있어 토큰 유효 기간 관리가 용이함
        - Key-Value 형태를 가지므로 토큰 관리에 적합함
    - RDB :
        - 데이터 영속성이 보장됨
        - 복잡한 쿼리를 지원함, 다양한 정보를 관리하고 다룰 수 있음
        - 데이터 무결성 보장, 응답속도 느림, 만료 시간을 직접 관리해야함

 

위 특징을 보면 Refresh 토큰은 다른 도메인과 비교했을 때, 중요한 정보가 아니므로 영속성이 필요하지 않다. 그렇기에 휘발성인 Redis에 저장하는 것이 좋을 수 있다. 

반대로 RDB에서 RefreshToken을 관리하면 토큰을 관리하고 분석할 수 있다. 언제 어떤 사용자에게 발급되고 사용되었는지, 사용자의 패턴을 분석하여 비정상적인 토큰 사용을 감지할 수 있을 것 같다. 

RDB에서는 만료 시간을 직접 관리해야 하기에 위와 같은 기능을 추가했다. 현재는 AccessToken을 재발급 받을 때 동작하고 있지만, 추가적으로 더 나아간다면 Spring Batch Job을 통해 주기적으로 관리할 수 있을 것 같다.

</details>
<details>
<summary>김동민</summary>

    
### 좋아요 기능에서 중복되는 코드를 별도의 메소드로 분리하기
    
- *이전 코드*
        
좋아요 기능에 대한 서비스 계층에서 해당 좋아요가 이미 DB에 저장되어 있는지, 멤버 데이터가 존재하는지 등 검증하는 기능에 대해 중복되는 코드들이 존재했다.

![1](https://github.com/Yanolza-Miniproject/backend/assets/85631282/b8639c7e-d87a-4681-8a08-4ddc59038e5c)

![2](https://github.com/Yanolza-Miniproject/backend/assets/85631282/648c6b17-279b-414d-aadd-9f3b5235360e)


        
해당 기능들을 하나의 메소드로 통합시켜서 해당 메소드를 호출함으로서 기능을 사용할 수 있도록 리팩토링 했다.
        
- *리팩토링 이후*
        
![3](https://github.com/Yanolza-Miniproject/backend/assets/85631282/0b1a894f-a10e-4bfb-9837-4d99d2849c20)

![4](https://github.com/Yanolza-Miniproject/backend/assets/85631282/bf5ef4c9-f345-432b-a912-cba748a35c62)

</details>
