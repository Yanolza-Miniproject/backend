<h1>[4조] 미니 프로젝트 : 숙박 예약 서비스</h1>

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

![Screen Shot 2023-11-30 at 4 12 08 PM](https://github.com/meena2003/inflearn_spring/assets/85631282/d2a9ca52-ba52-479a-bc4c-b05c1f66e02e)

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

- **권주환**: 
- **박준모**:
- **이민균**: 
- **김동민**: 프론트와 처음 협업한 프로젝트라서 걱정이 많았지만, 팀원들과 모르는 점을 함께 논의하며 잘 해결할 수 있었던 것 같습니다. 좋은 경험이 되었습니다!
