# 한편의 수학 백 엔드 API
이 프로젝트는 한편의 수학 학원에 다니는 학생, 선생님 그리고 원장님간에 교류를 원활하게 하고, 학습자료를 더 편리하게 제공하기 위한 서비스를 제공하는 데 목적이 있습니다.

## Service Architecture
![hpmath-infra](https://github.com/user-attachments/assets/2f369dd5-f8ed-4ef1-9b9e-e20718303d9b)

## Commands

### Command to run the application as a monolith
```
./gradlew app:hpmath-monolith:bootRun

```
### Commands to run each service
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


## 👬 Participants
Frontend: [coririri](https://github.com/coririri)
Backend: [@huhdy32](https://github.com/huhdy32)

### 🛠 Tools and technologies
- SpringBoot
----- 
- Mysql 8
- Redis
- Docker
- Apache Kafka
-----
- Junit
-----
- Gradle
