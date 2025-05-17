# 한편의 수학 백 엔드 API
이 프로젝트는 한편의 수학 학원에 다니는 학생, 선생님 그리고 원장님간에 교류를 원활하게 하고, 학습자료를 더 편리하게 제공하기 위한 서비스를 제공하는 데 목적이 있습니다.

## 서비스 아키텍쳐 
![hpmath-infra](https://github.com/user-attachments/assets/9a50673d-7603-46fe-88b4-ff052163a438)

## 실행 명령어

### 모놀리식 실행 명령어 
```
./gradlew app:hpmath-monolith:bootRun

```
### MSA 실행 명령어 
```
./gradlew app:api:hpmath-banner:bootRun
./gradlew app:api:hpmath-board:bootRun
./gradlew app:api:hpmath-board-view:bootRun
./gradlew app:api:hpmath-course:bootRun
./gradlew app:api:hpmath-directory:bootRun
./gradlew app:api:hpmath-media:bootRun
./gradlew app:api:hpmath-member:bootRun
./gradlew app:api:hpmath-online:bootRun

./gradlew app:batch:hpmath-board-view:bootRun
./gradlew app:batch:hpmath-member:bootRun

./gradlew app:consumer:hpmath-board:bootRun
./gradlew app:consumer:hpmath-course:bootRun
./gradlew app:consumer:hpmath-directory:bootRun
```


## 참여 인원
프론트엔드 [coririri](https://github.com/coririri)
백엔드 [@huhdy32](https://github.com/huhdy32)

### 🛠 사용 기슬
- SpringBoot 3.2.2
----- 
### 🫙 Infrastructure
- H2 DataBase
- Mysql 8
- Redis
- Apache Kafka
-----
### ✔️ 테스트 도구
- Junit
-----
### 🛠️ 빌드 / 배포 도구
- Gradle
