# STEP 01.

## 헤로쿠 링크

[first-qna heroku](https://first-qna.herokuapp.com/)

## 요구사항

- 회원 가입 기능 구현
- 회원 목록 보기 구현
- 회원 프로필 보기 구현
- 질문하기 구현
- 질문 목록 보기 구현
- 회원정보 수정 구현(선택사항)

## 회원 가입 기능

### 구현한 것

[✓]  폼에 입력 받은 회원 정보를 받아와 User클래스의 인스턴스를 생성한다.

[✓]  생성한 인스턴스를 유저 리스트(ArrayList)에 저장한다.

[✓]  저장을 완료한 후 사용자 목록 페이지 (/users)로 redirect 한다.

### 미해결

[X]  아이디, 이름, 이메일, 비밀번호가 각각의 길이와 양식에 맞게 전달 되지 않았을 경우 예외 처리 필요

## 회원 목록 보기

### 구현한 것

[✓]  Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.

[✓]  user/list.html 에서 사용자 목록을 출력한다.

### 미해결

[X]  user 인스턴스의 id 값의 자료형은 long형이 맞는 듯 한데,  처리하는 과정에서 int로의 형변환이 필요했다. 무언가 좀 아닌 듯(?) 데이터베이스를 사용 해봐야 할거 같다.

[X]  또한 id 값은 한번 할당되면 불변해야 하는 상수 값이 되어야 할 듯 한데, setter를 사용해서 할당 하였다. 더 고민 해봐야겠다.

## 회원 프로필 보기

### 구현한 것

[✓]  사용자 목록에서 이름을 클릭하면 해당 프로필 페이지로 이동 한다.

[✓]  프로필 페이지에서 해당 사용자의 이름, 이메일 주소가 출력 된다.

[✓]  URL은 "/users/{userId}"와 같이 매핑한다.

### 미해결

[X]  회원 가입 → 확인 → 코드 수정 → 회원 가입 → 확인 → ... 테스트 방법에 대한 학습 필요

## 질문하기

### 구현한 것

[✓]  사용자가 전달한 값을 Question 클래스를 생성해 저장한다.

[✓]  질문 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Question 인스턴스를 ArrayList에 저장한다.

[✓]  질문 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

### 미해결

[X]  미입력 폼이 전달 되었을 때의 예외처리

[X]  아이디는 수정될 수 없도록 처리

## 질문 목록 보기

### 구현한 것

[✓]  조회한 질문 목록을(ArrayList) Model에 저장한 후 View에 전달한다.

[✓]  View에서 Model을 통해 전달한 질문 목록을 출력한다.

[✓]  질문 목록의 제목을 클릭 했을 때 질문 상세 페이지에 접속할 수 있다.

[✓]  질문 상세 페이지 접근 URL은 "/questions/{index}" 로 구현한다.

[✓]  질문 객체에 id 인스턴스 변수를 추가하고 ArrayList에 질문 객체를 추가할 때 ArrayList.size() + 1을 질문 객체의 id로 사용한다.

[✓]  Controller에 상세 페이지 접근 method를 추가하고 URL은 /questions/{index}로 매핑한다.

[✓]  ArrayList에서 index - 1 해당하는 데이터를 조회한 후 Model에 저장해 View에 전달한다.

### 미해결

[X]  Question의 id 값 또한 한번 할당되면 고정되어야 할 값인 듯 하다. 상수로 할당 할 방법을 잘 모르겠는데 DB를 연동한 뒤 해결 해봐야겠다.

# STEP 02.

## 피드백 반영 사항

- 날짜 처리는 LocalDateTime 사용
- Id 값은 Long을 사용 : null 값을 담을 수 있기 때문.
- printUsers → listUsers 메서드 이름 개선

## 요구사항

- 사용자 데이터를 DB에 저장하기
- 회원 정보 수정하기
- 질문 데이터를 DB에 저장하기

## 사용자 데이터를 DB에 저장하기

### 해결한 것

[✓]  H2 데이터베이스에 대한 의존관계 설정 및 설정 : maven repository에서 h2의 따끈한 최신 버전인 "1.4.200" 버전을 의존성에 추가 하였다. 그러나 계속 빌드 오류가 나서 (Connection 에러, ddl-auto 속성 설정 오류) 6시간 동안 해결 방법을 찾다가 시간을 허비했다. 앞으로 무엇이던 최신버전은 '절대' 안 쓸 생각이다.

[✓]  폼에서 입력 받은 User 정보 데이터베이스에 저장

[✓]  id 값으로 User 조회

### 미해결점 및 개선해야 할 사항

[X]  findOne, findById 차이점 이해

[X]  null일 경우 .orElseThrow로 예외 처리를 하는 것이 맞는 방법일까?

[X]  예외 처리 구현에 대해 학습하고 고민 해보기

[X]  사용자 정보 수정시 기존 비밀번호와 일치 할 경우에만 회원의 정보를 수정, 비밀번호 자체 변경 기능 미구현. (둘을 동시에 처리 할 방법을 모르겠다.)

## 질문 데이터를 DB에 저장하기

### 해결한 것

[✓]  질문글을 DB에 저장

[✓]  DB에서 데이터를 꺼내와 게시글 목록 출력

[✓]  질문 상세보기 기능 구현

### 미해결점 및 개선해야 할 사항

[X]  게시글은 어떠한 예외 처리가 필요할까?

# STEP 03.

## 피드백 반영 사항

- 아직 피드백을 받지 못했다.

## 요구사항

- 로그인 기능

- 로그인 상태에 따른 메뉴 처리

- 로그아웃

- 개인 정보 수정 (사용자 본인의 정보만을 수정 가능 하도록)

- 질문글 수정

- 질문글 삭제

  

## 로그인 기능

### 해결한 것

[✓]  로그인이 가능하다.

[✓]  로그인시에 세션 정보가 잘 저장 된다.

[✓]  현재 상태가 로그인 상태이면 상단 메뉴가 “로그아웃”, “개인정보수정”이 나타나야 하며, 로그아웃 상태이면 상단 메뉴가 “로그인”, “회원가입”이 나타난다.

### 미해결점 및 의문점

- handlebars의 {{#unless}} 로 if-else 분기를 시도 했으나 이 경우 session 객체에서 값을 꺼내오지 못함. 
  어쩔 수 없이 {{^session}} {{#session}} 다시 사용

  

## 개인정보 수정 기능

### 해결한 것

[✓]  로그인한 사용자 본인만 해당 개인정보를 수정 가능하다.

[✓]  비밀번호가 일치할 경우에만 수정 된다.

### 미해결점 및 의문점

- 비밀번호 수정 기능 미구현 (못구현)



## 게시글 작성 기능

### 해결한 것

[✓]  로그인한 사용자만 게시글 작성이 가능하다.

[✓]  폼의 글쓴이 필드에 자동으로 로그인한 사용자 이름이 자동 입력 된다.



## 게시글 수정 기능

### 해결한 것

[✓]  폼의 글쓴이 필드에 로그인한 사용자 이름과 제목, 글의 내용이 자동으로 입력 된다.

[✓]  로그인한 사용자가 아니거나 자신의 글이 아닌 사용자가 수정을 시도할 경우 로그인 화면으로 리다이렉트

[✓]  컨트롤러에서 PutMapping 으로 구현

### 미해결점 및 의문점

- 로그인한 사용자가 아니거나 본인의 게시글이 아닌 경우 에러 페이지 출력 기능
- 수정하는 과정이 무언가 매끄럽지 않다. 폼에 입력된 게시글 수정 정보를 그대로 가져와서 questionRepository에 저장하면 게시글이 새로 생성된다. id값 까지 가져와서 저장하는데도 기존 데이터가 갱신 되는 것이 아니라 아예 새로운 데이터가 생성이 되는 듯 하다.  어쩔 수 없이 id 값으로 해당 question 객체를 찾아와서 메소드를 호출하여 수정 내용을 객체에 반영한 뒤 DB에서 갱신을 하도록 했다. 더 좋은 방법이 있을듯 하다.



## 게시글 삭제 기능

### 해결한 것

[✓]  로그인한 사용자가 아니거나 자신의 글이 아닌 사용자가 삭제를 시도할 경우 로그인 화면으로 리다이렉트

[✓]  컨트롤러에서 DeleteMapping 으로 구현



### 

















