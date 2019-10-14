# mobileprogramming

소프트웨어학부 20175165 서민주




## Mac OS에서 코드 작성하였습니다. 


  1. Login Activity (Relative Layout 사용)
  - 앱 접속 페이지, 회원 ID/비밀번호(EditView), 로그인/회원가입(Button)
  - 첫번째 화면 초기화시에 파일에서 개인정보 읽어 오기 
  - ID, 비밀번호 입력 시 기존에 가입한 회원 ID, 비밀번호 체크 => 오류 시 에러 메시지 출력(Toast)
  - ID, 비밀번호 입력이 정상이고 로그인 버튼 클릭 시 세번째 페이지 이동 
  
  
  - 화면 밖 터치 / 로그인버튼 클릭시 keypad 내려감
  - 아이디가 존재하지 않을 경우, 자동으로 아이디 비밀번호 비우고 다시 focus 줌
  - 비밀번호가 틀렸을 경우 비밀번호만 지우고 focus 줌
  
  
  
  
  2. Register Activity (Linear Layout 사용)
  - 회원가입 페이지, 첫번째 페이지에서 회원가입 버튼 클릭 시 출력
  - ID(EditView, 중복검사), 비밀번호(EditView, 자릿수/특수키 등 규칙 체크)
  - 이름/전화번호/주소(EditView)
  - 개인정보 사용 동의 간략 약관(TextView), 동의 여부(Radio Button, Decline/Accept)
  - 회원정보는 파일로 저장하고 첫번째 페이지로 이동
  
  
  - 개인정보 사용에 동의하지 않을시 register button 비활성화
  - 필수항목을 다 채우지 않았을 경우 / 채웠으나 형식이 틀렸을 경우 / 아이디가 중복인 경우 구분하여 에러 메세지 출력




  3. Main Activity - 간편 Calculator 기능 (Table Layout 사용)
  - 첫번째 페이지에서 ID, 비밀번호 입력 시 정상이고 로그인 버튼 클릭 시 화면 출력
  - View을 상속한 Table Layout과 Table row를 이용하여 화면을 구성
