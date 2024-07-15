package view;

import util.ScanUtil;

public class Print {
	public void var() {
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
	}
	public void var1() {
		System.out.println("▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰");
	}
	public void var1PrintLn() {
		printLn(1);
		var1();
	}
	public void printLn(int num) {
		for(int i = 0; i < num; i++)System.out.println();
	}
	public void mainPrint() {
		System.out.println("영화관 도트");
		printLn(1);
		var1();
		System.out.println("		1. 로그인");
		System.out.println("		2. 회원가입");
		printLn(1);
	}
	public void loginPrint() {
		printLn(1);
		var1();
		System.out.println("		로그인 하십시오");
		printLn(1);
	}
	public void loginIssue() {
		printLn(1);
		var1();
		System.out.println("		ID 혹은 PW를 잘못 입력 하셨습니다.");
		printLn(1);
		System.out.println("		1. 재로그인");
		System.out.println("		2. 회원가입");
		System.out.println("		3. 초기 화면으로");
		printLn(1);
	}
	public void adminPrint(String memNick) {
		printLn(1);
		var1();
		System.out.println("			환영합니다! " + memNick + "님		");
		System.out.println();
		System.out.println("		1. 예매화면으로");
		System.out.println("		2. 마이페이지");
		System.out.println("		3. 로그아웃");
		printLn(1);
	}
	public void signUpPrint() {
		printLn(1);
		var1();
		System.out.println("회원가입 화면입니다");
		System.out.println("개인 정보를 입력해주세요");
		printLn(1);
	}
	public void movieListPirnt() {
		printLn(1);
		var1();
		System.out.println("	번호\t영화제목\t\t\t장르\t\t관람가\t     상영관");
		var();
	}
	public void movieDetailPrint() {
		printLn(1);
		System.out.println("1. 영화스케쥴 조회");
		System.out.println("2. 영화리스트");
		printLn(1);
	}
	public void scheduleDetailPrint() {
		printLn(1);
		var1();
		System.out.println("	좌석페이지");
		printLn(1);
	}
	public void bookingPrint(String movieName, String genre, String rateType, String seat) {
		printLn(1);
		var1();
		System.out.println("	영화 제목 : " + movieName + "\n	장르 : " + genre + "\n	관람가 : " + rateType);
		System.out.println("	좌석번호 : " + seat);
		printLn(1);
		System.out.println("1. 결제하기");
		System.out.println("2. 메인으로 돌아가기");
		printLn(1);
	}
	public void ticketBuyPrint() {
		printLn(1);
		var1();
		printLn(1);
		System.out.println("결제하실 방법을 선택하세요");
		System.out.println("1. 카드");
		System.out.println("2. 현금");
		System.out.println("3. 문화상품권");
		System.out.println("4. 예매취소");
		printLn(1);
	}
	public void memberDetailPrint(String memName, String memId, String memPass, String memBir, String memNick) {
		printLn(1);
		var1();
		System.out.println("		마이 페이지");
		System.out.println("내 정보\n이름 : " + memName + "\n닉네임 : " + memNick + "\nID : " + memId + "\nPW : " + memPass
				+ "\n생년월일 : " + memBir);
		printLn(1);
		System.out.println("1. 개인정보 수정");
		System.out.println("2. 예매 화면으로");
		System.out.println("3. 예매내역");
		System.out.println("4. 로그아웃");
		System.out.println("5. 회원탈퇴");
		printLn(1);
	}
	public void memberUpdatePrint() {
		printLn(1);
		var1();
		System.out.println("		개인정보 변경 페이지");
		printLn(1);
		System.out.println("	변경할 정보를 선택해주세요");
		System.out.println();
		System.out.println("1. 닉네임");
		System.out.println("2. 비밀번호");
		System.out.println("3. 전체");
		System.out.println("4. 마이페이지로");
		printLn(1);
	}
	public void ticketCheckPrint() {
		printLn(1);
		var1();
		System.out.println("1. 예매내역 확인");
		System.out.println("2. 마이페이지로 돌아가기");
		printLn(1);
	}
	public void ticketEmptyPrint() {
		printLn(1);
		var1();
		System.out.println("예매내역이 없습니다.");
		System.out.println("마이페이지로 돌아가겠습니다.");
		printLn(1);
	}
	public void ticketDetail(String movieName, String runnigTime, String rowNum, int seatNum, String theName, String partType) {
		System.out.println("\t  ╭─────────────────────╮");
		printLn(1);
		System.out.println("\t\t"+movieName+"\t");
		System.out.println("\t\t"+theName+", "+partType+"\t");
		System.out.println("\t\t"+runnigTime+"\t");
		System.out.println("\t\t"+rowNum+"열 "+seatNum+"번\t");
		printLn(1);
		System.out.println("\t  ╰─────────────────────╯");
	}
}
