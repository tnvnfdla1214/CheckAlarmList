# MemoLists
## 1. App</br>
#### 본인의 메모를 추가하여 매일 알람 팝업을 받아 볼 수 있는 체크리스트</br>

## 2. Explanation</br>
### 1) 메인</br>
<img src="https://user-images.githubusercontent.com/54509842/83601971-9c180800-a5ac-11ea-9af6-8f80ee99909c.jpg" width="25%"></img>
#### BottombarNavigation으로 하단바를 구성하였고, 리스트는 RecyclerView로 구현하였다. </br>왼쪽 화면은 CheckList 화면, 오른쪽 화면은 관리화면이다. </br>
### 2) 메모 추가</br>
<img src="https://user-images.githubusercontent.com/54509842/83602751-21e88300-a5ae-11ea-8f80-a53a4f5cb4fd.jpg" width="25%"></img>
<img src="https://user-images.githubusercontent.com/54509842/83602756-23b24680-a5ae-11ea-85b2-9b93e24a638f.jpg" width="25%"></img>
#### 메인에서 추가버튼을 누르면, 메모를 추가하는 화면으로 이동한다. </br>메모를 입력한 후 추가를 누른다. 더 추가할 메모가 있다면 추가하고, 그렇지 않다면, 메인으로 이동한다.</br>
### 3) 메모 수정</br>
<img src="https://user-images.githubusercontent.com/54509842/83602759-244add00-a5ae-11ea-8468-6d1e902108e5.jpg" width="25%"></img>
<img src="https://user-images.githubusercontent.com/54509842/83602761-244add00-a5ae-11ea-81c5-dbcec9c17f1e.jpg" width="25%"></img>
<img src="https://user-images.githubusercontent.com/54509842/83602762-24e37380-a5ae-11ea-9ffe-4735258ef016.jpg" width="25%"></img>
#### 메인 화면에서 오른쪽으로 스와이핑하면, 수정버튼이 생성된다.</br>누르면 다이얼로그가 생성되고, 수정할 데이터 삽입 후 수정버튼을 누르면, CheckList 화면에 반영된다.  </br>
### 4) 메모 확인</br>
<img src="https://user-images.githubusercontent.com/54509842/83602763-257c0a00-a5ae-11ea-897d-6187c20481af.jpg" width="25%"></img>
<img src="https://user-images.githubusercontent.com/54509842/83602766-26ad3700-a5ae-11ea-94d7-8eed2e2f3ff5.jpg" width="25%"></img>
#### 메인 화면에서 왼쪽으로 스와이핑하면, 확인버튼이 생성된다. 누르면 메인 화면에서 사라지고, 알람시간에 다시 나타난다.  </br>
### 5) 메모 삭제</br>
<img src="https://user-images.githubusercontent.com/54509842/83602768-26ad3700-a5ae-11ea-9703-f81292c66c71.jpg" width="25%"></img>
<img src="https://user-images.githubusercontent.com/54509842/83602770-2745cd80-a5ae-11ea-89b6-333020a57125.jpg" width="25%"></img>
#### 관리 화면에서 왼쪽으로 스와이핑하면, 확인버튼이 생성된다. 누르면 CheckList 화면에서 사라지고, 메모는 완전히 삭제가 된것이다.  </br>
### 6) 팝업 및 리셋</br>
<img src="https://user-images.githubusercontent.com/54509842/83602772-27de6400-a5ae-11ea-89e1-c71d9637078b.jpg" width="25%"></img>
<img src="https://user-images.githubusercontent.com/54509842/83602775-27de6400-a5ae-11ea-8241-56bb42763788.jpg" width="25%"></img>
#### 모든 메모를 확인 하였다. 알람시간이 되면 팝업이 생성된다. 누르면 CheckList 화면으로 Intent되고, 이전에 확인한 메모는 다시 나타난다.  </br>

## 3. KeyWord</br>
### 1) NavigationBar</br>
#### Main의 하단바 구성으로 사용하였다. </br>(Main.java : 67~83) </br>
### 2) Fragment</br>
#### Main에서 선언하고, List와 추가버튼등이 동적으로 추가되거나 하나의 독립된 채로 실행된다. </br>(Main.java : 60~63 / Fragment1.java, Fragment2.java) </br>
### 3) AlarmReceiver</br>
#### Main에서 diaryNotification 메서드로 소통하여, 시간의 비교로 팝업을 띄우는 역할을 한다.</br>(Main.java :85~108 / AlarmReceiver.java) </br>
### 4) Room</br>
#### 데이터베이스의 역할을 한다. </br> 기존의 Sql문을 쓰기 번거로웠던, 단점을 극복하고 간편하게 만들었으며, Sql을 직접 쓸 수 있는 등 강력한 기능을 지원하는 개념이다.</br>(MemoDatalist.java (테이블 구성) / MemoDataDataBase.java / MemoDao.java (Sql문) ) </br>
### 5) Service</br>
#### 24시 정각이 지날시 앱이 종료되어도 해당 저장되었던 리스트 정보가 다시 생성된다. onbind의 메소드 진행순서로 진행된다.</br>
(MyService.java (서비스 구성)) </br>

## 4. FeedBack </br>
#### 피드백이나 기능의 개선사항에 대한 의견은 likppi100@naver.com 혹은 tnvnfdla12@gmail.com 으로 보내주시면 감사하겠습니다.</br>
