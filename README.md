# 일정 관리 서비스
Spring Boot 기반의 RESTful 일정 관리 백엔드 API 서버입니다.
유저 인증(세션), 일정 CRUD, 댓글 기능을 제공합니다.
---
## 기술 스택
| 분류              | 기술 |
|-----------------|------|
| Language        | Java 17 |
| Framework       | Spring Boot 3.5 |
| ORM             | Spring Data JPA |
| Database        | MySQL |
| Security        | BCrypt  |
| Validation      | Spring Validation |
| Template Engine | Thymeleaf |
| Build Tool      | Gradle |
| Etc             | Lombok |

---
## 프로젝트 구조
```
src/main/java/org/zerock/schedulemanagementdevelop/
├── ScheduleManagementDevelopApplication.java   # 메인 클래스
├── BaseEntity.java                             # 생성일/수정일 공통 엔티티
├── config/
│   └── PasswordEncoder.java                   # BCrypt 비밀번호 인코더
├── user/                                      # 유저 도메인
│   ├── controller/UserController.java
│   ├── service/UserService.java
│   ├── repository/UserRepository.java
│   ├── entity/User.java
│   └── dto/
├── schedule/                                  # 일정 도메인
│   ├── controller/ScheduleController.java
│   ├── service/ScheduleService.java
│   ├── repository/ScheduleRepository.java
│   ├── entity/Schedule.java
│   └── dto/
├── comment/                                   # 댓글 도메인
│   ├── controller/CommentController.java
│   ├── service/CommentService.java
│   ├── repository/CommentRepository.java
│   ├── entity/Comment.java
│   └── dto/
└── exception/                                 # 예외 처리
    ├── GlobalExceptionHandler.java
    ├── ServiceException.java
    ├── AccessDeniedException.java
    ├── DuplicateEmailException.java
    ├── InvalidPasswordException.java
    ├── ScheduleNotFoundException.java
    ├── UnauthorizedException.java
    └── UserNotFoundException.java
```

## ERD
```
users
├── id          BIGINT (PK, AUTO_INCREMENT)
├── username    VARCHAR NOT NULL
├── email       VARCHAR NOT NULL UNIQUE
├── password    VARCHAR NOT NULL
├── created_at  DATETIME
└── modified_at DATETIME

schedules
├── id          BIGINT (PK, AUTO_INCREMENT)
├── title       VARCHAR(30) NOT NULL
├── content     VARCHAR(200) NOT NULL
├── user_id     BIGINT (FK → users.id)
├── created_at  DATETIME
└── modified_at DATETIME

comments
├── id          BIGINT (PK, AUTO_INCREMENT)
├── content     VARCHAR NOT NULL
├── user_id     BIGINT (FK → users.id)
├── schedule_id BIGINT (FK → schedules.id)
├── created_at  DATETIME
└── modified_at DATETIME

```
## API명세
###  User

#### 회원가입

| Method | URL |
|--------|-----|
| POST | `/signup` |

**Request Body**
```json
{
  "username": "이름",
  "email": "이메일주소",
  "password": "비밀번호"
}
```

**Response Body** `201 Created`
```json
{
  "id": "유저 고유 ID",
  "username": "이름",
  "email": "이메일주소",
  "createdAt": "생성 시간",
  "modifiedAt": "수정 시간"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 201 | 회원가입 성공 |
| 400 | 유효성 검사 실패 |
| 409 | 이메일 중복 |

---

#### 로그인

| Method | URL |
|--------|-----|
| POST | `/login` |

**Request Body**
```json
{
  "email": "이메일주소",
  "password": "비밀번호"
}
```

**Response Body** `200 OK`

없음 (세션에 로그인 정보 저장)

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 로그인 성공 |
| 400 | 비밀번호 불일치 |
| 404 | 유저 없음 |

---

#### 단일 유저 조회

| Method | URL |
|--------|-----|
| GET | `/users/{id}` |

**Request Body**

없음

**Response Body** `200 OK`
```json
{
  "id": "유저 고유 ID",
  "username": "이름",
  "email": "이메일주소",
  "createdAt": "생성 시간",
  "modifiedAt": "수정 시간"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 조회 성공 |
| 404 | 유저 없음 |

---

#### 전체 유저 조회

| Method | URL |
|--------|-----|
| GET | `/users` |

**Request Body**

없음

**Response Body** `200 OK`
```json
[
  {
    "id": "유저 고유 ID",
    "username": "이름",
    "email": "이메일주소",
    "createdAt": "생성 시간",
    "modifiedAt": "수정 시간"
  }
]
```

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 조회 성공 |

---

#### 유저 정보 수정 

| Method | URL |
|--------|-----|
| PUT | `/users/{id}` |

**Request Body**
```json
{
  "username": "수정할 이름",
  "email": "수정할 이메일주소"
}
```

**Response Body** `200 OK`
```json
{
  "id": "유저 고유 ID",
  "username": "수정된 이름",
  "email": "수정된 이메일주소",
  "modifiedAt": "수정 시간"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 수정 성공 |
| 401 | 로그인 필요 |
| 403 | 권한 없음 |
| 404 | 유저 없음 |

---

#### 유저 삭제 

| Method | URL |
|--------|-----|
| DELETE | `/users/{id}` |

**Request Body**

없음

**Response Body** `204 No Content`

없음

| 상태 코드 | 설명 |
|-----------|------|
| 204 | 삭제 성공 |
| 401 | 로그인 필요 |
| 403 | 권한 없음 |
| 404 | 유저 없음 |

---

### Schedule

#### 일정 생성 

| Method | URL |
|--------|-----|
| POST | `/schedules` |

**Request Body**
```json
{
  "title": "일정 제목",
  "content": "일정 내용"
}
```

**Response Body** `201 Created`
```json
{
  "id": "일정 고유 ID",
  "userId": "작성자 유저 ID",
  "userName": "작성자 이름",
  "title": "일정 제목",
  "content": "일정 내용",
  "createdAt": "생성 시간",
  "modifiedAt": "수정 시간"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 201 | 생성 성공 |
| 400 | 유효성 검사 실패 |
| 401 | 로그인 필요 |
| 404 | 유저 없음 |

---

#### 단일 일정 조회

| Method | URL |
|--------|-----|
| GET | `/schedules/{id}` |

**Request Body**

없음

**Response Body** `200 OK`
```json
{
  "id": "일정 고유 ID",
  "userId": "작성자 유저 ID",
  "userName": "작성자 이름",
  "title": "일정 제목",
  "content": "일정 내용",
  "createdAt": "생성 시간",
  "modifiedAt": "수정 시간"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 조회 성공 |
| 404 | 일정 없음 |

---

#### 전체 일정 조회 (페이징)

| Method | URL |
|--------|-----|
| GET | `/schedules?page={페이지번호}&size={페이지크기}&userId={유저ID}` |

**Request Body**

없음

**Response Body** `200 OK`
```json
{
  "content": [
    {
      "id": "일정 고유 ID",
      "title": "일정 제목",
      "content": "일정 내용",
      "commentCount": "댓글 수",
      "userName": "작성자 이름",
      "createdAt": "생성 시간",
      "modifiedAt": "수정 시간"
    }
  ],
  "totalElements": "전체 일정 수",
  "totalPages": "전체 페이지 수",
  "size": "페이지당 항목 수",
  "number": "현재 페이지 번호"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 조회 성공 |

---

#### 일정 수정 

| Method | URL |
|--------|-----|
| PUT | `/schedules/{id}` |

**Request Body**
```json
{
  "title": "수정할 제목",
  "content": "수정할 내용"
}
```

**Response Body** `200 OK`
```json
{
  "id": "일정 고유 ID",
  "title": "수정된 제목",
  "content": "수정된 내용",
  "modifiedAt": "수정 시간"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 수정 성공 |
| 400 | 유효성 검사 실패 |
| 401 | 로그인 필요 |
| 403 | 권한 없음 |
| 404 | 일정 없음 |

---

#### 일정 삭제 

| Method | URL |
|--------|-----|
| DELETE | `/schedules/{id}` |

**Request Body**

없음

**Response Body** `204 No Content`

없음

| 상태 코드 | 설명 |
|-----------|------|
| 204 | 삭제 성공 |
| 401 | 로그인 필요 |
| 403 | 권한 없음 |
| 404 | 일정 없음 |

---

### Comment

#### 댓글 생성

| Method | URL |
|--------|-----|
| POST | `/schedules/{scheduleId}/comments` |

**Request Body**
```json
{
  "content": "댓글 내용"
}
```

**Response Body** `201 Created`
```json
{
  "id": "댓글 고유 ID",
  "userId": "작성자 유저 ID",
  "scheduleId": "소속 일정 ID",
  "content": "댓글 내용",
  "createdAt": "생성 시간",
  "modifiedAt": "수정 시간"
}
```

| 상태 코드 | 설명 |
|-----------|------|
| 201 | 생성 성공 |
| 400 | 유효성 검사 실패 |
| 401 | 로그인 필요 |
| 404 | 일정 또는 유저 없음 |

---

#### 댓글 전체 조회

| Method | URL |
|--------|-----|
| GET | `/schedules/{scheduleId}/comments` |

**Request Body**

없음

**Response Body** `200 OK`
```json
[
  {
    "id": "댓글 고유 ID",
    "userId": "작성자 유저 ID",
    "scheduleId": "소속 일정 ID",
    "content": "댓글 내용",
    "createdAt": "생성 시간",
    "modifiedAt": "수정 시간"
  }
]
```

| 상태 코드 | 설명 |
|-----------|------|
| 200 | 조회 성공 |

---

## 예외처리
`GlobalExceptionHandler`에서 전역 예외를 처리합니다.

| 예외 클래스 | HTTP 상태 | 설명 |
|------------|-----------|------|
| `UnauthorizedException` | 401 | 로그인 필요 |
| `AccessDeniedException` | 403 | 본인 소유 리소스가 아닌 경우 |
| `UserNotFoundException` | 404 | 유저를 찾을 수 없음 |
| `ScheduleNotFoundException` | 404 | 일정을 찾을 수 없음 |
| `InvalidPasswordException` | 400 | 비밀번호 불일치 |
| `DuplicateEmailException` | 409 | 이메일 중복 |
| `MethodArgumentNotValidException` | 400 | 유효성 검사 실패 |
---
## 실행 방법

```bash
# 1. 저장소 클론
git clone https://github.com/oHAHOo/ScheduleManagementDevelop.git
cd ScheduleManagementDevelop

# 2. application.properties 설정

# 3. 빌드 및 실행
./gradlew bootRun
```
