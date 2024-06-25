# Challenge

이 프로젝트는 같은 목표를 가진 사람들과 함께 목표를 달성할 수 있는 동기부여 앱입니다.
자유롭게 달성하고 싶은 목표를 올리거나 달성하고 싶은 목표에 참가하여 함께 목표를 이룰 수 있습니다.

## 🛠️ Tech Stack
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- JPA (Java Persistence API)
- MySQL

## 🔖 Features
<details>
  
- 사용자 인증 기능
  - 회원가입
  - 로그인
  - 로그아웃

- 프로필 관리
  - 비밀번호 수정
  - 프로필 수정
    
- 게시물 CRUD 기능
  - 조회
  - 작성
  - 수정
  - 삭제

- 댓글 CRUD 기능
  - 작성
  - 수정
  - 삭제

- 백오피스 기능
  - 사용자 권한 관리자로 승격
  - 사용자 권한 관리
  - 전체 사용자 조회
  - 사용자 삭제
</details>

## API

[Postman 명세서로 이동](https://documenter.getpostman.com/view/34878744/2sA3XY5xPn)

- 모든 `API`에 대한 반환은 `Content-Type: application/json; charset=utf-8`를 기본으로 합니다.
- 인증은 Bearer Token을 통해 진행합니다.

<details>

- **Response**
    - `Post`
    
    ```json
    {
        "data": {
            "id": 7,
            "username": "test1234",
            "title": "post test",
            "content": "content test",
            "createdAt": "2024-06-21T03:17:04.1310677",
            "updatedAt": "2024-06-21T03:17:04.1310677"
        }
    }
    ```
    
    - `Post (page)`
    
    ```json
    {
        "status": 200,
        "message": "The request has been successfully processed.",
        "data": {
            "totalPages": 1,
            "totalElements": 5,
            "first": true,
            "last": true,
            "size": 5,
            "content": [
                {
                    "id": 13,
                    "username": "test1234",
                    "title": "post test7!",
                    "content": "content test7!",
                    "createdAt": "2024-06-21T12:55:19.795803",
                    "updatedAt": "2024-06-21T12:55:19.795803"
                },
                {
                    "id": 12,
                    "username": "test1234",
                    "title": "post test6!",
                    "content": "content test6!",
                    "createdAt": "2024-06-21T12:54:57.901893",
                    "updatedAt": "2024-06-21T12:54:57.901893"
                },
                {
                    "id": 11,
                    "username": "test1234",
                    "title": "점심 뭐 먹지",
                    "content": "배고픔",
                    "createdAt": "2024-06-21T12:51:47.155775",
                    "updatedAt": "2024-06-21T12:52:10.500887"
                },
                ...
            ],
            "number": 0,
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            },
            "numberOfElements": 5,
            "pageable": {
                "pageNumber": 0,
                "pageSize": 5,
                "sort": {
                    "empty": false,
                    "sorted": true,
                    "unsorted": false
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
            },
            "empty": false
        }
    }
    ```
    
    - `User`
    
    ```json
    {
        "data": {
            "id": 7,
            "username": "test1234",
            "title": "post test",
            "content": "content test",
            "createdAt": "2024-06-21T03:17:04.1310677",
            "updatedAt": "2024-06-21T03:17:04.1310677"
        }
    }
    ```
    
    ```json
    {
        "data": {
            "username": "test1234",
            "nickname": "Lee",
            "introduce": "hi!",
            "email": "test@example.com"
        }
    }
    ```
    
    - UserList
    
    ```json
    {
        "data": [
            {
                "username": "admin",
                "nickname": "admin",
                "introduce": "admin account",
                "email": "admin@example.com"
            },
            {
                "username": "test1234",
                "nickname": "Lee",
                "introduce": "hi!",
                "email": "test@example.com"
            },
            {
                "username": "test1235",
                "nickname": "Lee",
                "introduce": "hi!",
                "email": "test@example.com"
            },
            {
                "username": "test1236",
                "nickname": "Lee",
                "introduce": "hi!",
                "email": "test@example.com"
            },
            ...
        ],
        "message": "The request has been successfully processed.",
        "status": 200
    }
    ```
    
    - `Comment`
    
    ```json
    {
        "data": {
            "id": 3,
            "content": "테스트1",
            "username": "test1234",
            "createdAt": "2024-06-21T10:06:02.7847837",
            "updatedAt": "2024-06-21T10:06:02.7847837"
        },
        "message": "Success",
        "status": 200
    }
    ```
    
    - `Comments`
    
    ```json
    {
        "data": [
            {
                "id": 1,
                "content": "테스트1",
                "username": "test1234",
                "createdAt": "2024-06-21T03:45:58.049485",
                "updatedAt": "2024-06-21T03:45:58.049485"
            },
            {
                "id": 2,
                "content": "테스트1",
                "username": "test1234",
                "createdAt": "2024-06-21T10:03:26.974083",
                "updatedAt": "2024-06-21T10:03:26.974083"
            },
            {
                "id": 3,
                "content": "테스트1",
                "username": "test1234",
                "createdAt": "2024-06-21T10:06:02.784784",
                "updatedAt": "2024-06-21T10:06:02.784784"
            }
        ],
        "message": "Success",
        "status": 200
    }
    ```
    
    - `Default Success Code`
    
    ```json
    {
        "status": 200,
        "message": "The request has been successfully processed.",
        "data": null
    }
    ```
    
    - `204 No conent`
    
    - 대표적인 에러 코드
        - `401 for Unauthorized requests`
        - `400 for Bad requests`
        - `404 for Not found requests`
        - `409 for Confict error requests`
        - `403 forbidden`
</details>

- 인증
  
| Title | HTTP Method | URL | Request | Response | Auth |
|:---:|:---:|:---:|:---:|:---:|:---:|
| `로그인` |`POST`|`/api/auth/login`|`{"username": "test1235","password": "Test1234!@"}`|`Default Success Code`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`No`|
| `로그아웃` |`DELETE`|`/api/auth/logout`| |`Default Success Code`<br><br>`401 Unauthorized`|`Yes`|

- 유저

| Title | HTTP Method | URL | Request | Response | Auth |
|:---:|:---:|:---:|:---:|:---:|:---:|
| `회원가입` |`POST`|`/api/users`| `{ "username":"test1235", "password":"Test1234!@", "nickname": "Lee", "introduce": "hi!", "email":"test@example.com" }` |`Default Success Code`|`No`|
| `회원탈퇴` |`DELETE`|`/api/users`|| `Default Success Code`<br><br>`401 Unauthorized` |`Yes`|
| `회원정보 조회` |`GET`|`/api/users`|| `Default Success Code` `User` |`Yes`|
| `회원정보 수정` |`PUT`|`/api/users`|`{ "nickname": "Kim", "introduce" : "bye!"}` |`Default Success Code` `UserInfo`|`Yes`|
| `비밀번호 변경` |`PUT`|`/api/users/password`|`{ "currentPassword" : "Test1234!", "password" : "Test5678!" }`|`Default Success Code`<br><br>`400 Bad Requset`<br><br>`401 Unauthorized`|`Yes`|

- 게시물

| Title | HTTP Method | URL | Request | Response | Auth |
|:---:|:---:|:---:|:---:|:---:|:---:|
| `게시글 등록` |`POST`|`/api/posts`|`{"title" : "post test", "content" : "test content"}`|`Default Success Code`|`Yes`|
| `선택한 게시글 조회` |`GET`|`/api/posts/{postId}`||`Default Success Code` `Post`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`No`|
| `선택한 게시글 수정` |`PUT`|`/api/posts/{postId}`|`{ "title" : "edit post test", "content" : "edit content"}`|`Default Success Code` `Post`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`Yes`|
| `선택한 게시글 삭제` |`DELETE`|`/api/posts/{postId}`||`204 No content`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`Yes`|
| `전체 게시글 조회` |`GET`|`/api/posts?page=0&size=5&sort=createdDate,desc`||`Post (page)`|`No`|

- 댓글

| Title | HTTP Method | URL | Request | Response | Auth |
|:---:|:---:|:---:|:---:|:---:|:---:|
|`댓글 작성`|`POST`|`/api/posts/{postId}/comments`|`{"content" : "write a comment"}`|`Default Success Code` `Comment`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`Yes`|
|`댓글 수정`|`PUT`|`/api/posts/{postId}/comments/{commentId}`|`{"content" : "edit comment."}`|`Default Success Code` `Comment`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`Yes`|
|`댓글 삭제`|`DELETE`|`/api/posts/{postId}/comments/{commentId}`||`204 No content`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`Yes`|
|`댓글 조회`|`GET`|`/api/posts/{postId}/comments`||`Default Success Code` `Comment`<br><br>`401 Unauthorized`<br><br>`404 Not found`|`Yes`|

- 관리자

| Title | HTTP Method | URL | Request | Response | Auth |
|:---:|:---:|:---:|:---:|:---:|:---:|
|`사용자 전체 목록 조회`|`GET`|`/api/admin/users`||`Default Success Code` `UserList`<br><br>`403 Forbidden`|`Yes`|
|`사용자 권한 수정`|`PUT`|`/api/admin/users/{userId}/role`||`Default Success Code`<br><br>`403 Forbidden`|`Yes`|
|`사용자 관리자로 승격`|`PUT`|`/api/admin/users/{userId}/promote`||`Default Success Code`<br><br>`403 Forbidden`|`Yes`|
|`사용자 삭제`|`DELETE`|`/api/admin/users/{userId}`||`204 No content`<br><br>`403 Forbidden`|`Yes`|
|`게시글 전체 목록 조회`|`GET`|`/api/admin/posts`||`Default Success Code` `PostList`<br><br>`403 Forbidden`|`Yes`|
|`게시글 수정`|`PUT`|`/api/admin/posts/{postId}`||`Default Success Code` `Post`<br><br>`403 Forbidden`|`Yes`|
|`게시글 삭제`|`DELETE`|`/api/admin/posts/{postId}`||`204 No content`<br><br>`403 Forbidden`|`Yes`|
|`댓글 전체 목록 조회`|`GET`|`/api/admin/comments`||`Default Success Code` `Comments`<br><br>`403 Forbidden`|`Yes`|
|`댓글 수정`|`PUT`|`/api/admin/comments/{commentId}`||`Default Success Code` `Comment`<br><br>`403 Forbidden`|`Yes`|
|`댓글 삭제`|`DELETE`|`/api/admin/comments/{commentId}`||`204 No content`<br><br>`403 Forbidden`|`Yes`|


## 🧱 ERD
![drawSQL-image-export-2024-06-24](https://github.com/lis0517/challenge-app/assets/43354156/db9f0c53-2ede-4020-9e87-9220f55dd992)


