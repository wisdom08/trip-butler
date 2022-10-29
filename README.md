<div align="center">
  <img src="https://user-images.githubusercontent.com/111177236/198817418-955543ae-d9a9-48f2-a19a-2787fc0c17b7.png" width="600" height="400"/>
</div>

# 당신의 신문 - 프로젝트 소개

## 뉴스 검색 및 의견 공유 서비스

Python 크롤러를 통해 뉴스 데이터를 수집한 후, Elasticsearch와 SpringBoot를 사용해 뉴스 검색 및 의견을 공유할 수 있는 서비스를 만들었습니다.
  
### 주요 기능

- 회원가입 기능
- 로그인, 로그아웃 기능
- 뉴스 검색
  - 상세 필터 검색 지원
    - 기간별: 원하는 기간을 설정해 검색 가능(기본: 전체기간 검색)
    - 분야별: 정치, 경제, 사회, 문화, 국제, IT과학 6개 분야별로 검색 가능
    - 신문사별: 중앙일보, 한국일보, 동아일보, 조선일보, 한겨레, 매일경제, 기타 7개 신문사별로 검색 가능
   - 영어로 검색 시 한글 검색 결과 반환 (ex.kakao -> 카카오)
   - 한글을 영타로 검색 시 한글 검색 결과 반환(ex.zkzkdh -> 카카오)
- 의견 공유
  - 기사별 의견 공유 버튼 눌러 사용자의 의견 공유 가능
  - 다른 사람들의 의견 보기 버튼 눌러 다른 사용자들의 의견 확인 가능
    - 기사 링크를 눌러 원본 기사 확인 가능


### 🗓 프로젝트 기간
2022.08.26 ~ 2022.10.06 (6주 프로젝트)


## 📹 시연 영상
[![시연영상]( https://img.youtube.com/vi/IFjFKhobEBU/0.jpg)](https://youtu.be/IFjFKhobEBU)
 
 
## 아키텍쳐
![Architecture](https://i.imgur.com/iwBrr0a.png)


## 개발 환경
- Intellij IDEA Ultimate
- Spring Boot 2.7.2
- Java 17
- Gradle 7.5


## 팀원소개

| Name                 | GitHub Address                                        | Position    |
|----------------------|-------------------------------------------------------|-------------|
| 변지혜                  | https://github.com/wisdom08                          | BE / Spring |
| 노정민                  | https://github.com/lumill925                         | BE / Spring |
| 이동욱                  | https://github.com/Moveuk                            | BE / Spring |
| 장혜진                  | https://github.com/jeanniejang                       | BE / Spring |


### 🌟프로젝트와 관련하여 진행 히스토리, 트러블슈팅, 자세한 기술 구현 등 추가적인 내용은 [프로젝트 위키](https://github.com/your-news/service-repo/wiki)의 해당항목을 참고해주세요.
