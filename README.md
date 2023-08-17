# Logbook: 사내 릴리즈 노트 공유 시스템
Logbook은 기술 문서를 자동으로 요약하여 릴리즈 노트를 작성해주는 사내 릴리즈 노트 공유 시스템입니다.
기술 문서를 요약해서 릴리즈 노트를 자동으로 작성할 수 있습니다.
릴리즈 노트 작성 규칙에 따라 문장을 자동 완성해주는 차별점을 가지고 있습니다.


## 문서
- Logbook [API 명세서](https://logbook5.docs.apiary.io/#)
- Logbook [Google Drive](https://drive.google.com/drive/folders/1_o-brVhdnHVWNCXrYy9JFwxZykRHrk8S?usp=drive_link)
- Logbook [Notion](https://www.notion.so/seobinlee00/4b04cde519094eda98f4dd37e9859894?v=be10c461e965420790b67272803c7f5d&pvs=4)
- K-Kaos [Slack](https://app.slack.com/huddle/T056ESEE3K6/C05GTA3RFSM)
- K-Kaos [Jira](https://leeseobin.atlassian.net/jira/software/projects/LB/boards/1)


## 기능

- 기술 문서에서 주요 내용을 추출하여 릴리즈 노트 작성
- 릴리즈 노트 작성 규칙에 따라 문장을 자동 완성
- 릴리즈 노트 공유 및 관리를 통한 쉬운 협업 가능


## 백엔드 기술 스택
![Static Badge](https://img.shields.io/badge/IntelliJ-%23000000?logo=intellijidea&logoColor=white&link=https%3A%2F%2Fwww.jetbrains.com%2Fidea%2Fdocumentation%2F)


![Static Badge](https://img.shields.io/badge/OpenJDK_11-%23FFFFFF?logo=openjdk&logoColor=black&link=https%3A%2F%2Fopenjdk.org%2Fprojects%2Fjdk%2F11%2F)
![Static Badge](https://img.shields.io/badge/Spring_Boot-%236DB33F?logo=springboot&logoColor=white&link=https%3A%2F%2Fdocs.spring.io%2Fspring-boot%2Fdocs%2F2.x%2Freference%2Fhtml%2F)
![Static Badge](https://img.shields.io/badge/Spring_Security-%236DB33F?logo=springsecurity&logoColor=white&link=https%3A%2F%2Fdocs.spring.io%2Fspring-security%2Fsite%2Fdocs%2Fcurrent%2Freference%2Fhtml5%2F)
![Static Badge](https://img.shields.io/badge/JPA-%236DB33F?link=https%3A%2F%2Fdocs.spring.io%2Fspring-data%2Fjpa%2Fdocs%2Fcurrent%2Freference%2Fhtml%2F)


![Static Badge](https://img.shields.io/badge/JUnit5-%2325A162?logo=junit5&logoColor=white&link=https%3A%2F%2Fjunit.org%2Fjunit5%2Fdocs%2Fcurrent%2Fuser-guide%2F)


![Static Badge](https://img.shields.io/badge/MySQL-%234479A1?logo=mysql&logoColor=white&link=https%3A%2F%2Fdev.mysql.com%2Fdoc%2Frefman%2F8.0%2Fen%2F)


## Environment
```dotenv
# 데이터베이스 연결 정보
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=

# JWT 암호키
JWT_ACCESS_SECRET=
JWT_REFRESH_SECRET=

# 소셜로그인 API
KAKAO_CLIENT_ID=
KAKAO_SECRET=
GOOGLE_CLIENT_ID=
GOOGLE_SECRET=
NAVER_CLIENT_ID=
NAVER_SECRET=

# CORS Origin 설정
CORS_ORIGIN_DEVELOPMENT=
CORS_ORIGIN_PRODUCTION=
```

## 사용법 및 설정
이 프로젝트를 직접 사용하려면 다음 단계를 따라주세요.
- 필요한 소프트웨어 설치: Java JDK-11, MySQL 8.0, IntelliJ IDEA
- 환경변수에서 데이터베이스 연결 정보를 설정합니다.
- 환경변수에서 JWT 암호키 정보를 설정합니다.
- 환경변수에서 소셜로그인 API 정보를 설정합니다. 
- 프로젝트를 빌드한 후, Spring Boot 애플리케이션을 실행하여 백엔드 서버를 시작합니다.
- IntelliJ IDEA를 사용하여 테스트 코드를 작성하고 코드의 정확성을 확인할 수 있습니다.
