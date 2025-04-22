I am
| 이름 | 사진 | 역할(Backend) |  
|:--:|:--:|:---------------:|
| 박선규   |<img src="https://github.com/p4rksk/miniproject-jobala-v2-ssr/assets/153582360/cf8fd9c6-50d9-40b5-9914-09527344bf92" width="100">  |태이블설계, 프로젝트 초기 세팅 <br/>초기 더미 작업, 깃플로우 블로깅<br/><br/>관리자:책 목록보기, 상세보기, 수정, 삭제, <br/><br/>사용자: 회원가입,<br/> 메인(책히스토리,(일간,주간,누적 <br/>베스트셀러)), <br/>내서재(책목록, 위시리스트, 리뷰 관리), <br/> 랭크별 검색(카데고리별 베스트셀러)<br/>API문서<br/><br/>시연영상 촬영, PPT준비, 발표|
---
![image](https://github.com/user-attachments/assets/1603bb8a-075a-4722-9901-93b6a8aa97c4)

<br>

![image](https://github.com/user-attachments/assets/fecfab02-f63f-4000-af60-e5edabe1cbd0)



# 🚀 "SHELF" - E-book 플랫폼

<br>

<p align="center">
  <img src="https://github.com/user-attachments/assets/d2e697d9-4364-41ce-b855-889bfe3514a5"  style="width: 50%; height: auto;"/>
</p>

<br>

# 👉  E-book 플랫폼
> ### 개발기간: 2024.07.04 ~ 2024.07.24

<br>

# 👉 깃허브 주소
  
> #### 플러터 서버     : [https://github.com/chugue/shelf-flutter-project](https://github.com/chugue/shelf-flutter-project)<br>
> #### 관리자 서버     : [https://github.com/chugue/shelf-project](https://github.com/chugue/shelf-project)<br>
<br>

# 👉 개발팀 소개


<table style="width:100%; text-align:center;">
  <tr>
    <th style="text-align:center;">김성훈(팀장)</th>
    <th style="text-align:center;">김주혁</th>
    <th style="text-align:center;">장유진</th>
    <th style="text-align:center;">양승호</th>
    <th style="text-align:center;">박선규</th>
    <th style="text-align:center;">서지민</th>
  </tr>
  <tr>
    <td style="text-align:center; vertical-align:middle;"><img width="160px" src="https://avatars.githubusercontent.com/u/30003848?v=4" /></td>
    <td style="text-align:center; vertical-align:middle;"><img width="160px" src="https://avatars.githubusercontent.com/u/153582123?v=4" /></td>
    <td style="text-align:center; vertical-align:middle;"><img width="160px" src="https://avatars.githubusercontent.com/u/161176345?v=4"/></td>
    <td style="text-align:center; vertical-align:middle;"><img width="160px" src="https://avatars.githubusercontent.com/u/97007464?v=4" /></td>
    <td style="text-align:center; vertical-align:middle;"><img width="160px" src="https://avatars.githubusercontent.com/u/153582360?v=4" /></td>
    <td style="text-align:center; vertical-align:middle;"><img width="160px" src="https://avatars.githubusercontent.com/u/118310514?v=4" /></td>
  </tr>
  <tr>
    <td style="text-align:center; vertical-align:middle;"><a href="https://github.com/chugue">@chugue</a></td>
    <td style="text-align:center; vertical-align:middle;"><a href="https://github.com/kjh5848">@kjh5848</a></td>
    <td style="text-align:center; vertical-align:middle;"><a href="https://github.com/hillview0303">@hillview0303</a></td>
    <td style="text-align:center; vertical-align:middle;"><a href="https://github.com/LifeIsOne">@LifeIsOne</a></td>
    <td style="text-align:center; vertical-align:middle;"><a href="https://github.com/p4rksk">@p4rksk</a></td>
    <td style="text-align:center; vertical-align:middle;"><a href="https://github.com/minmeanmin">@minmeanmin</a></td>
  </tr>
</table>


<br>

# 👉프로젝트 소개 (핵심로직 설명)
> #### Shelf는 서재라는 의미로, 
> #### 사용자들이 구독이라는 상태로 정기결제를 하여
> #### 온라인 독서 서비스를 제공합니다.

<br>


| 시연영상 링크 ( 사진 클릭 👇👇) |
|:---------------------------:|
| [![시연02](https://img.youtube.com/vi/jXa_rS_uIPI/0.jpg)](https://www.youtube.com/watch?v=jXa_rS_uIPI) |


<br>
<br>

# 👉 프로젝트 구조

![프로젝트 구조](https://github.com/user-attachments/assets/c0af544d-8262-4eee-a8db-55da673920c1)


<br>
<br>

# 👉 SHELF PPT 발표자료
[SHELF 발표 PPT](https://www.canva.com/design/DAGLwfc8c_0/4B7VG1T4ZvANuR5U9L7ffg/view?utm_content=DAGLwfc8c_0&utm_campaign=designshare&utm_medium=link&utm_source=editor)

<br>
<br>

# 👉 Stacks

![image](https://github.com/user-attachments/assets/2f757938-0613-49d0-826b-24e7f28cf771)


### Communication

![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)

<br>

# 👉 Server Dependencies
```java
dependencies {
    implementation group: 'com.auth0', name: 'java-jwt', version: '4.3.0'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-mustache'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation group: 'com.auth0', name: 'java-jwt', version: '4.3.0'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.h2database:h2'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    implementation 'io.github.cdimascio:java-dotenv:5.2.2'
    implementation 'com.github.iamport:iamport-rest-client-java:0.2.23'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
    implementation 'org.springframework.boot:spring-boot-starter'
}
```

<br>

# 👉 테이블 설계
![image](https://github.com/user-attachments/assets/d6fca4b5-4882-4adb-afd6-b7ad38a59254)


<br>


# 👉 팀 블로깅  
[팀 블로그 링크](https://www.notion.so/stephenkiim/5b91773884db4d07a98d3007f0cf4d0f?pvs=4)


<br>
<br>

# 👉 프로젝트 주요 기능 정리
* #### 🟨 Naver OAuth 인증
* #### 🟨 Cosmus Epub을 활용한 읽기 모드
* #### 🟦 Record를 활용한 DTO 핸들링
* #### 🟦 JWT 인증 
* #### 🟦 Base64 이미지 송수신
* #### 🟦 Port One을 활용한 정기결제
* #### 🟦 ChartJS를 활용한 매출현황
* #### 🟨 RiverPod 상태관리
* #### 🟨 Dio 통신
* #### 🟦 RestDoc을 활용한 API 문서 + 통합테스트
* #### 🟦 Gitflow 브랜치 전략 활용

<br>
<br>


# 👉 핵심 시나리오 시연
### 🔹관리자 화면 

![관리자 화면](https://github.com/user-attachments/assets/4414cfaf-4057-41d7-93b6-ecdac44e7380)

<br>

### 🔹 책 CRUD

![책 CRUD](https://github.com/user-attachments/assets/abd87c0d-cd74-49d7-b553-e15fe8aa0f6e)

<br>

### 🔹 앱 화면 둘러보기

![앱 화면 둘러보기](https://github.com/user-attachments/assets/1019fbbf-d728-4d36-9304-f980f93d87b4)


<br>

### 🔹 내 서재 디자인

![내 서재 디자인](https://github.com/user-attachments/assets/288fb4e3-8282-4755-9aab-c7e4ae0fd2ad)


<br>

### 🔹 다크 모드

 ![다크 모드](https://github.com/user-attachments/assets/7414c3cd-2eb5-4f60-b594-7363210399db)



### 🔹 결제 하기

![결제하기](https://github.com/user-attachments/assets/2c6820fe-704f-4aa2-9f26-053b374e836e)

<br>

### 🔹 책 상세보기

![책 상세보기](https://github.com/user-attachments/assets/845f4bb3-296c-4f8a-8337-e31c69befbc9)

<br>

### 🔹 책 읽기

![책 읽기](https://github.com/user-attachments/assets/112c4d70-ba85-4126-bfb5-1d0db16650bf)

<br>

### 🔹 책 검색하기

![검색하기](https://github.com/user-attachments/assets/d6441907-3a90-4a5e-ac4a-866b970da946)

<br>
<br>


