4월 26일

하단 네비바 전부 화면전환 기능 구현

@카메라\
*저장기능 아직X\
*정보추가란으로 이동X\
*미리보기 사라지지 않음 (뒤로가기 누르면 다시 홈화면)

@정보추가\
*저장기능X\
*화면 이동만 구성

@사진첩\
*갤러리로 이동 구현O\
*카메라처럼 저장기능은 아직X\
*카메라처럼 미리보기 사라지지 않음 (뒤로가기 누르면 다시 홈화면)

@관심고양이\
*즐겨찾기 기능구현은X\
*화면 이동만 구성

자바 코드 분리시켜서 단순화\
*지도와 버튼구성은 아직 MainActivity에 존재(다른 .java파일로 옮겨서 intent로 동작하게 할 수 있을지 모르겠음//시도안해봄)

지도\
*부산대 6공학관으로 마킹 설정

고양이 얼굴 인식\
*python으로 고양이가 인식될 시 return True 구현완료\
*하지만 안드로이드 스튜디오가 python이 힘듦\
*(그래서 카메라로 찍으면 firebase에 저장시킨후 이미지 불러와서 처리 후 firebase에 다시 저장하는 방법 생각중)


-----------------------------------------------

4월 6일

네비게이션바 -> 고양이 사진 찍기 선택 시 \
*카메라 사진찍기 기능 구현(원본사진) \
*미리보기 생성

미리보기가 사라지지 않음...\
저장기능은 아직...

