package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import service.BookingService;
import service.MemberService;
import service.MovieService;
import service.ScheduleService;
import service.SeatService;
import service.TicketService;
import util.ScanUtil;
import view.Print;
import view.View;
import vo.BookingVo;
import vo.MemberVo;
import vo.MovieVo;
import vo.ScheduleVo;
import vo.SeatVo;

public class MainController extends Print {
   static public Map<String, Object> sessionStorage = new HashMap<>();
   // 프리서비스랑 연결
   MovieService movieService = MovieService.getInstance();
   ScheduleService scheduleService = ScheduleService.getInstance();
   SeatService seatService = SeatService.getInstance();
   MemberService memberService = MemberService.getInstance();
   BookingService bookingService = BookingService.getInstance();
   TicketService ticketService = TicketService.getInstance();

   public static void main(String[] args) {
      new MainController().start(); // 시작
   }

   private void start() {
      View view = View.MAIN; // MAIN 1
      while (true) {
         switch (view) {
            case MAIN:
               view = main();
               break;
            case LOGIN:
               view = login();
               break;
            case SIGN_UP:
               view = signUp();
               break;
            case ADMIN:
               view = admin();
               break;
            case MOVIE_LIST:
               view = movieList();
               break;
            case MOVIE_DETAIL:
               view = movieDetail();
               break;
            case SCHEDULE:
               view = schedule();
               break;
            case SCHEDULE_DETAIL:
               view = scheduleDetail();
               break;
            case SEAT:
               view = seat();
               break;
            case BOOKING:
               view = booking();
               break;
            case TICKET_BUY:
               view = ticketBuy();
               break;
            case MEMBER_DETAIL:
               view = memberDetail();
               break;
            case MEMBER_UPDATE:
               view = memberUpdate();
               break;
            case MEMBER_DELETE:
               view = memberDelete();
               break;
            case TICKET_CHECK:
               view = ticketCheck();
               break;

            default:
               break;
         }
      }
   }

   // 예매내역확인
   private View ticketCheck() {
      var();
      System.out.println("1. 예매내역 확인");
      System.out.println("2. 마이페이지로 돌아가기");
      int sel = ScanUtil.menu();
      switch (sel) {
         case 1:

            List<Object> param = new ArrayList<>();
            String id = (String) sessionStorage.get("id");
            param.add(id);
            List<MemberVo> memberVos = ticketService.ticketCheck(param);
            if (memberVos.isEmpty()) {
               System.out.println("예매내역이 없습니다.");
               System.out.println("마이페이지로 돌아가겠습니다.");
               return View.MEMBER_DETAIL;
            }

            // 영화 코드를 중복없이 저장하기위한 Set
            Set<String> movieCodes = new HashSet<>();
            for (MemberVo memberVo : memberVos) {
               movieCodes.add(memberVo.getMovie_code());
            }

            // 각 영화코드 상세정보 도출
            List<MemberVo> ticketResult = new ArrayList<>();
            for (String movieCode : movieCodes) {
               param.clear();
               param.add(id);
               param.add(movieCode);
               List<MemberVo> result = ticketService.ticketCheckList(param);
               ticketResult.addAll(result); // 전체 출력
            }

            List<Object> infoList = new ArrayList<>();

            String memNick = null;
            int totalPrice = 0;
            for (MemberVo memberVo : ticketResult) {
               memNick = memberVo.getMem_nick();
               String movieName = memberVo.getMovie_name();
               String genre = memberVo.getGenre();
               String partType = memberVo.getPart_type();
               String runningTime = memberVo.getRunning_time();
               String rowNum = memberVo.getRow_num();
               int seatNum = memberVo.getSeat_num();
               int price = memberVo.getPrice();

               System.out.println("티켓내역 [" + memNick + "님의 예매내역 -" + " 영화이름 = " + movieName + "|" + " 장르 = " + genre
                     + " |" + " 관람가 = " + partType + " |" + " 러닝타임 = " + runningTime + " |" + " 좌석번호 = " + rowNum
                     + seatNum + " |" + " 가격 = " + price + "]");
               totalPrice += price;
               System.out.println(); // 줄바꿈 구분
            }
            System.out.println(memNick + "님의 총 결제금액 내역 : " + totalPrice + "원 입니다");
            System.out.println();

         case 2:
            return View.ADMIN;

         default:
            System.out.println("잘못된 입력값입니다 다시 입력해주십시오");
            return View.TICKET_CHECK;
      }
   }

   private View memberDelete() {
      MemberVo member = (MemberVo) sessionStorage.get("member");
      int memNo = member.getMem_no();
      List<Object> param = new ArrayList<>();
      param.add(memNo);
      System.out.println("정말 탈퇴하시겠습니까?");
      System.out.println("1. 네");
      System.out.println("2. 아니오");
      int sel = ScanUtil.menu();

      if (sel > 2 || sel < 1) {
         System.out.println("잘못된 입력값입니다 다시 입력해주십시오");
         return View.MEMBER_DELETE;
      }
      if (sel == 1) {
         memberService.memberDelete(param);
         System.out.println("탈퇴하였습니다 감사합니다");
         System.out.println();
         return View.MAIN;
      }
      if (sel == 2) {
         System.out.println("마이페이지로 이동합니다");
         System.out.println();
         return View.MEMBER_DETAIL;
      }
      return View.MEMBER_DELETE;
   }

   private View memberUpdate() {
      MemberVo member = (MemberVo) sessionStorage.get("member");
      int memNo = member.getMem_no();
      System.out.println("개인정보 변경 페이지입니다");
      System.out.println("바꾸실 정보를 입력해주세요");
      System.out.println();
      System.out.println("1. 닉네임");
      System.out.println("2. 비밀번호");
      System.out.println("3. 전체");
      System.out.println("4. 마이페이지로");
      int sel = ScanUtil.menu();
      if (sel > 4 || sel < 1) {
         System.out.println("잘못된 입력값입니다 다시 입력해주십시오");
         return View.MEMBER_UPDATE;
      }
      if (sel == 4) {
         return View.MEMBER_DETAIL;
      }
      List<Object> param = new ArrayList<>();
       if (sel == 1 || sel == 3) {
         String nickname = ScanUtil.nextLine("수정할 닉네임 : ");
         param.add(nickname);
      }
      if (sel == 2 || sel == 3) {
         String password = ScanUtil.nextLine("수정할 비밀번호 : ");
         param.add(password);
      }
      param.add(memNo);

      memberService.memberUpdate(param, sel);
      MainController.sessionStorage.clear();
      sessionStorage.put("view", View.MEMBER_DETAIL);
      System.out.println("개인정보가 수정되었습니다. 다시 로그인해주십시오");
      System.out.println();
      return View.MAIN;
   }

   private View memberDetail() {
      var();
      System.out.println("마이 페이지입니다.");
      MemberVo member = (MemberVo) sessionStorage.get("member");

      String memName = member.getMem_name();
      String memId = member.getMem_id();
      String memPass = member.getMem_pass();
      String memBir = member.getMem_bir();
      String memNick = member.getMem_nick();
      System.out.println("내 정보\n이름 : " + memName + "\n닉네임 : " + memNick + "\nID : " + memId + "\nPW : " + memPass
            + "\n생년월일 : " + memBir);

      System.out.println();
      System.out.println("1. 개인정보 수정");
      System.out.println("2. 예매 화면으로");
      System.out.println("3. 예매내역");
      System.out.println("4. 로그아웃");
      System.out.println("5. 회원탈퇴");
      int sel = ScanUtil.menu();
      switch (sel) {
         case 1:
            return View.MEMBER_UPDATE;
         case 2:
            return View.MOVIE_LIST;
         case 3:
            return View.TICKET_CHECK;
         case 4:
            System.out.println("로그아웃되었습니다");
            sessionStorage.clear();
            return View.MAIN;
         case 5:
            return View.MEMBER_DELETE;
         default:
            System.out.println("잘못된 입력값입니다 다시 입력해주십시오");
            return View.MEMBER_DETAIL;
      }
   }

   // 예매예약 취소
   private void bookingDelete(int bkCode) {
      List<Object> paramBk = new ArrayList<Object>();
      paramBk.add(bkCode);
      bookingService.bookingDelete(paramBk);
   }

   private void bookingDelete1(int bkCode1, int bkCode2) {
      List<Object> paramBk = new ArrayList<Object>();
      paramBk.add(bkCode1);
      paramBk.add(bkCode2);
      bookingService.bookingDelete1(paramBk);
   }

   private void seatDelete(String seatCode) {
      List<Object> paramSt = new ArrayList<Object>();
      paramSt.add(seatCode);
      seatService.seatDelete(paramSt);
   }

   private void seatDelete1(String seatCode1, String seatCode2) {
      List<Object> paramSt = new ArrayList<Object>();
      paramSt.add(seatCode1);
      paramSt.add(seatCode2);
      seatService.seatDelete1(paramSt);
   }

   // 티켓구매
   private View ticketBuy() {
      var();
      System.out.println("결제확인 창입니다");
      int selMovie = (int) sessionStorage.get("movieNo");
      List<Object> param = new ArrayList<Object>();
      param.add(selMovie);
      MovieVo movie = movieService.ticketSelect(param);
      String movieName = movie.getMn(); // 영화제목
      String genre = movie.getGn(); // 장르
      String partType = movie.getPart_type(); // 관람가
      String thName = movie.getTn(); // 상영관
      int price = movie.getPr(); // 가격
      int bookingSel = (int) sessionStorage.get("bookingSel");

      System.out.println("영화제목 : " + movieName + " 상영관 : " + thName + "\n장르 : " + genre + "\t관람가 : " + partType);
      if (bookingSel == 1) {
         System.out.println("결제금액 : " + price);
      }
      if (bookingSel == 2) {
         System.out.println("결제금액 : " + price * 2);
      }
      System.out.println("================================");
      System.out.println("결제하실 방법을 선택하세요");
      System.out.println("1. 카드");
      System.out.println("2. 현금");
      System.out.println("3. 문화상품권");
      System.out.println("4. 예매취소");

      int memNo = (int) sessionStorage.get("memNo");

      List<Object> paramTk = new ArrayList<Object>();
      int paySel = ScanUtil.menu();
      if (paySel > 4 || paySel < 1) {
         System.out.println("잘못된 입력값입니다 다시 입력해주십시오");
         return View.TICKET_BUY;
      }
      if (paySel == 4) {
         if (bookingSel == 1) {
            int bkCode = (int) sessionStorage.get("bkCode");
            bookingDelete(bkCode);
         }
         if (bookingSel == 2) {
            int bkCode1 = (int) sessionStorage.get("bkCode1");
            int bkCode2 = (int) sessionStorage.get("bkCode2");
            bookingDelete1(bkCode1, bkCode2);
         }
         return View.MOVIE_LIST;
      }

      paramTk.add(memNo);
      switch (bookingSel) {
         case 1:
            int bkCode = (int) sessionStorage.get("bkCode");
            paramTk.add(bkCode);
            paramTk.add(paySel);
            paramTk.add(price);
            ticketService.tkInsert(paramTk);
            return View.ADMIN;
         case 2:
            int bkCode1 = (int) sessionStorage.get("bkCode1");
            int bkCode2 = (int) sessionStorage.get("bkCode2");
            paramTk.add(bkCode1);
            paramTk.add(paySel);
            paramTk.add(price);
            paramTk.add(memNo);
            paramTk.add(bkCode2);
            paramTk.add(paySel);
            paramTk.add(price);
            ticketService.tkInsert1(paramTk);
            return View.ADMIN;
         default:
            return View.TICKET_BUY;
      }

   }

   // 예매내역확인
   private View booking() {
      String id = (String) sessionStorage.get("id");
      var();
      MovieVo movie = (MovieVo) sessionStorage.get("movie");
      String seat = (String) sessionStorage.get("seat");
      String movieName = movie.getMovie_name();
      sessionStorage.put("movieName", movieName); // 선택한 영화이름
      String genre = movie.getGenre();
      String rateType = movie.getRATETYPE();
      int rateCode = movie.getFk_rate();

      List<Object> param = new ArrayList<>();
      param.add(id);
      param.add(rateCode);
      MemberVo mem = memberService.ageCheck(param);



      int grade = mem.getOk();
      if (grade == 2) {
         System.out.println("나이제한 예매 불가능 합니다");
         return View.MOVIE_LIST;
      }

      int bookingSel = (int) sessionStorage.get("bookingSel");
      if (bookingSel == 1) {
         String schCode = (String) sessionStorage.get("sch");
         String seatCode = (String) sessionStorage.get("seatCode");
         List<Object> paramBk = new ArrayList<>();
         paramBk.add(schCode);
         paramBk.add(seatCode);
         bookingService.bookingUpdate(paramBk);
         BookingVo booking = bookingService.bookingSelect(paramBk);
         int bkCode = booking.getBk_code();
         sessionStorage.put("bkCode", bkCode); // bkCode
      }
      if (bookingSel == 2) {
         String schCode = (String) sessionStorage.get("sch");
         String seatCode1 = (String) sessionStorage.get("seatCode1");
         String seatCode2 = (String) sessionStorage.get("seatCode2");
         List<Object> paramBk = new ArrayList<>();
         paramBk.add(schCode);
         paramBk.add(seatCode1);
         paramBk.add(schCode);
         paramBk.add(seatCode2);
         bookingService.bookingUpdate1(paramBk);
         ArrayList<Integer> arr = new ArrayList<>();
         List<BookingVo> booking = bookingService.bookingSelectList(paramBk);
         for (BookingVo bookingVo : booking) {
            int bkCode = bookingVo.getBk_code();
            arr.add(bkCode);
         }
         int bkCode1 = arr.get(0);
         int bkCode2 = arr.get(1);
         sessionStorage.put("bkCode1", bkCode1);
         sessionStorage.put("bkCode2", bkCode2);
         // 여기서 List<BookingVo>로 bk_code 값을 받아와서 두개값을 다 담아야함
      }
      System.out.println("영화 제목 : " + movieName + "\n장르 : " + genre + " 관람가 : " + rateType);
      System.out.println("좌석번호 : " + seat);
      System.out.println("1. 결제하기");
      System.out.println("2. 메인으로 돌아가기");
      int sel = ScanUtil.menu();
      switch (sel) {
         case 1:
            return View.TICKET_BUY;
         case 2:
            if (bookingSel == 1) {
               int bkCode = (int) sessionStorage.get("bkCode");
               bookingDelete(bkCode);
               String seatCode = (String) sessionStorage.get("seatCode");
               seatDelete(seatCode);
            }
            if (bookingSel == 2) {
               int bkCode1 = (int) sessionStorage.get("bkCode1");
               int bkCode2 = (int) sessionStorage.get("bkCode2");
               bookingDelete1(bkCode1, bkCode2);
               String seatCode1 = (String) sessionStorage.get("seatCode1");
               String seatCode2 = (String) sessionStorage.get("seatCode2");
               seatDelete1(seatCode1, seatCode2);
            }
            return View.ADMIN;

         default:
            System.out.println("잘못된 입력값입니다");
            return View.BOOKING;
      }
   }

   private View seat() {
      String th = (String) sessionStorage.get("th");
      List<Object> param1 = new ArrayList<>();
      param1.add(th);
      List<SeatVo> seat = seatService.seatList(param1);
      int max = 0;
      for (SeatVo seatVo : seat) {
         max = seatVo.getSeat_max();
      }
      if (max == 20) {
         displaySeats(seat, 5, new String[]{"A","B","C","D"});

      } else {
         displaySeats(seat, 6, new String[]{"A","B","C","D","E"});
      }
      System.out.println();
      int sel = ScanUtil.nextInt("예매 인원을 선택해주세요(1~2) : ");
      List<Object> param = new ArrayList<Object>();
      sessionStorage.put("bookingSel", sel); // 예매인원
      if (sel == 1) {
         singleSeatSelect(max, param, th);
      } else if (sel == 2) {
         doubleSeatSelect(max, param, th);
      } else {
         System.out.println("잘못된 인원을 입력하셨습니다 다시 입력해주세요");
         return View.SEAT;
      }

      return View.BOOKING;
   }

   //두명의 좌석 예약 입력
   private View doubleSeatSelect(int max, List<Object> param, String th) {

      String row1 = null;
      int seatNum1 = 0;
      String row2 = null;
      int seatNum2 = 0;
      boolean validInput1 = false;
      boolean validInput2 = false;

      while (!validInput1 || !validInput2) {
            if (max == 20) {
               row1 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~D : ").toUpperCase();
               seatNum1 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~5 : ");
               validInput1 = validateSeat(row1, seatNum1, 4, 5);
               row2 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~D : ").toUpperCase();
               seatNum2 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~5 : ");
               validInput2 = validateSeat(row2, seatNum2, 4, 5);
            } else {
               row1 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~E : ").toUpperCase();
               seatNum1 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~6 : ");
               validInput1 = validateSeat(row1, seatNum1, 5, 6);
               row2 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~E : ").toUpperCase();
               seatNum2 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~6 : ");
               validInput2 = validateSeat(row2, seatNum2, 5, 6);
            }
            if (!validInput1 || !validInput2) {
               System.out.println("잘못된 입력값입니다. 다시 입력해주세요");
            }
         }
      param.add(row1); // 파라미터 값 추가
      param.add(seatNum1);
      param.add(row2);
      param.add(seatNum2);
      param.add(th);
      seatService.seat1(param);
      sessionStorage.put("seat", row1 + seatNum1 + ", " + row2 + seatNum2);

      List<SeatVo> seatInfo1 = seatService.seatInfo1(param); //파라미터값 시트info입력

      ArrayList<String> arr1 = new ArrayList<>();
      for (SeatVo seatVo : seatInfo1) { // seatVo값뽑기
         String seatCode = seatVo.getSeat_code();
         arr1.add(seatCode);
      }

      String seatCode1 = arr1.get(0);
      String seatCode2 = arr1.get(1);
      sessionStorage.put("seatCode1", seatCode1);
      sessionStorage.put("seatCode2", seatCode2);

      return View.BOOKING;
   }

   // 한명의 인원 좌석 예약
   private View singleSeatSelect(int max, List<Object> param, String th) {
      String row1 = null;
      int seatNum1 = 0;
      boolean validInput = false; // 기본값 false

      while (!validInput) {
            if (max == 20) { //20좌석 상영관
               row1 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~D : ").toUpperCase();
               seatNum1 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~5 : ");
               validInput = validateSeat(row1, seatNum1, 4, 5);
            } else { //30좌석 상영관
               row1 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~E : ").toUpperCase();
               seatNum1 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~6 : ");
               validInput = validateSeat(row1, seatNum1, 5, 6);
            }
            if (!validInput) {
               System.out.println("잘못된 입력값입니다. 다시 입력해주세요");
            }
      }
      param.add(row1);
      param.add(seatNum1);
      param.add(th);
      seatService.seat(param);
      sessionStorage.put("seat", row1 + seatNum1); //좌석 기입 a1, b4 등
      SeatVo seatInfo = seatService.seatInfo(param);
      String seatCode = seatInfo.getSeat_code();
      sessionStorage.put("seatCode", seatCode); //완성된 seat코드 기입

      return View.BOOKING;
   }

   // 영화관 자리 리스트 출력
   private void displaySeats(List<SeatVo> seat, int columns, String[] rows) {
      Set<String> uniqueRows = new HashSet<>(); // SET으로 중복제거목적
      String[] arr = new String[50];
      int cnt = 0;
      int cnt1 = 0; // 현재 행의 인덱스를 추적하는 변수

      System.out.print("  ");
      for (int i = 1; i <= columns; i++) {
         System.out.print(i + "  "); // 좌석 번호 출력
      }
      System.out.println();

      for (int i = 0; i < seat.size(); i++) {
         SeatVo seatVo = seat.get(i);
         String rowNum = seatVo.getRow_num();
         uniqueRows.add(rowNum); // 값담기

         arr[i] = seatVo.getStatus();

         if (cnt % columns == 0) {
            System.out.print(rows[cnt1] + " "); // 행 이름 출력
         }

         if (arr[i].equals("Y")) {
            System.out.print("■  ");
         } else {
            System.out.print("□  ");
         }

         cnt++;

         if (cnt % columns == 0) {
            System.out.println(); // 다음 행으로 넘어가기
            cnt1++; // 다음 행으로 이동
         }
      }
   }

   // seat 오류 검증
   private boolean validateSeat(String row, int seatNum, int maxRows, int maxCols) {
      return row.length() == 1 && row.charAt(0) >= 'A' && row.charAt(0) <= 'A' + maxRows && seatNum >= 1 && seatNum <= maxCols;
   }

   private View scheduleDetail() {
      int timeCode = (int) sessionStorage.get("timeCode");
      String movieCode = (String) sessionStorage.get("movieCode");

      List<Object> param = new ArrayList<Object>();
      param.add(timeCode);
      param.add(movieCode);
      var();
      System.out.println("좌석선택 페이지");
      ScheduleVo scheduleVo = scheduleService.scheduleDetail(param);
      String th = scheduleVo.getFk_the_code();
      String sch = scheduleVo.getSch_code();
      sessionStorage.put("th", th);
      sessionStorage.put("sch", sch);

      return View.SEAT;
   }

   private View schedule() {
      var();
      int movieNo = (int) sessionStorage.get("movieNo");
      List<Object> param = new ArrayList<>();
      param.add(movieNo);
      System.out.println("상영번호\t1\t\t2\t\t3\t\t4\t\t5\t\t6");
      List<ScheduleVo> schedule = scheduleService.schedule(param);
      for (ScheduleVo scheduleVo : schedule) {
         String runningTime = scheduleVo.getRunning_time();
         System.out.print("\t" + runningTime);
      }
      System.out.println();
      int sel = ScanUtil.nextInt("상영번호를 골라주세요 : ");
      if (sel > 6) {
         System.out.println("없는 시간대 번호입니다 다시 입력해주십시오.");
         return View.SCHEDULE;
      }
      sessionStorage.put("timeCode", sel); // time_code

      return View.SCHEDULE_DETAIL;
   }

   // 상세영화조회
   private View movieDetail() {
      var();
      int movieNo = (int) sessionStorage.get("movieNo"); // 선택한 상세영화번호
      List<Object> param = new ArrayList<>();
      param.add(movieNo);
      MovieVo movie = movieService.movieDetail(param);
      String movieName = movie.getMovie_name();
      String genre = movie.getGenre();
      String rateType = movie.getRATETYPE();
      String movieCode = movie.getMovie_code();
      sessionStorage.put("movieCode", movieCode);
      sessionStorage.put("movie", movie);

      System.out.println("영화제목\t\t장르\t  관람가");
      System.out.println(movieName + "\t" + genre + "\t  " + rateType);
      System.out.println("1. 영화스케쥴 조회");
      System.out.println("2. 영화리스트(뒤로가기)");
      int sel = ScanUtil.menu();
      switch (sel) {
         case 1:
            return View.SCHEDULE;
         case 2:
            return View.MOVIE_LIST;
         default:
            System.out.println("잘못 입력하셨습니다. 다시입력해주세요");
            return View.MOVIE_DETAIL;
      }
   }

   // 무비 리스트
   private View movieList() {
      List<MovieVo> movieList = movieService.movieList();
      var();
      System.out.println("번호\t영화제목\t\t\t장르\t\t관람가\t     상영관");
      for (MovieVo movieVo : movieList) {
         String movieNo = movieVo.getMono();
         String movieName = movieVo.getMn();
         String genre = movieVo.getGn();
         String thName = movieVo.getTn();
         String partType = movieVo.getPart_type();
         System.out.println(movieNo + "\t" + movieName + "\t\t" + genre + "\t\t" + partType + "     " + thName);
      }
      System.out.println("5   뒤로가기");
      int sel = ScanUtil.nextInt("상세조회 할 영화의 번호를 입력해주세요 : ");

      if (sel == 5) {
         return View.ADMIN;
      }
      if (sel > 5 || sel < 1) {
         System.out.println("유효하지 않은 번호입니다 다시 입력해주세요");
         return View.MOVIE_LIST;
      }
      sessionStorage.put("movieNo", sel);
      return View.MOVIE_DETAIL;
   }

   // 영화관메인사이트
   private View admin() {
      MemberVo member = (MemberVo) sessionStorage.get("member");
      int memNo = member.getMem_no();
      sessionStorage.put("memNo", memNo);
      String memName = member.getMem_name();

      System.out.println("----------환영합니다! " + memName + "님----------");
      System.out.println();
      System.out.println("1. 예매화면으로");
      System.out.println("2. 마이페이지");
      System.out.println("3. 로그아웃");
      int sel = ScanUtil.menu();
      switch (sel) {
         case 1:
            return View.MOVIE_LIST;
         case 2:
            return View.MEMBER_DETAIL;
         case 3:
            sessionStorage.clear();
            return View.MAIN;
         default:
            return View.ADMIN;
      }
   }

   // 회원가입
   private View signUp() {
      System.out.println("회원가입 화면입니다");
      System.out.println("개인 정보를 입력해주세요");
      String name = ScanUtil.nextLine("이름 : ");
      String id = ScanUtil.nextLine("ID : ");
      String pw = ScanUtil.nextLine("PW : ");
      String bir = ScanUtil.nextLine("생년월일 8자리 : ");
      if (!isValidDate(bir)) {
         System.out.println("잘못된 생년월일 형식입니다. ex)19990718");
         return View.SIGN_UP;
      }
      String nickname = ScanUtil.nextLine("닉네임 : ");
      // 로그인 id 중복 검증
      // #설계미스#
      // 1. 처음부터 id를 기본키 값으로 설정했으면 null값, 중복값 금지여서
      // 굳이 로직을 구성하지않아도 알아서 db validation에서 걸렸을것(현재는 mem_No가 기본키)
      // 다음부터는 id처럼 중복값이 무조건 금지되는 컬럼은 기본키값을 설정하는것을 고려한다.
      memberService.idInfo();
      List<String> memIdList = (List<String>) sessionStorage.get("idInfo");
      // 리스트로 검증
      if (memIdList.contains(id)) {
         System.out.println("중복된 id입니다 다시 설정해주세요");
         // 다시 회원가입으로 스캔값입력하도록
         return View.SIGN_UP;
      }
      List<Object> param = new ArrayList<>();
      param.add(name);
      param.add(id);
      param.add(pw);
      param.add(bir);
      param.add(nickname);

      memberService.signUp(param);
      System.out.println("회원가입되었습니다 다시 로그인해주세요");
      return View.MAIN;
   }

   //생년월일 검증
   private boolean isValidDate(String date) {
      if (date == null || date.length() != 8) {
         return false;
      }
      try {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
         sdf.setLenient(false);
         sdf.parse(date);
         return true;
      } catch (ParseException e) {
         return false;
      }
   }

   // 로그인
   private View login() {
      var();
      System.out.println("로그인 하십시오");
      String id = ScanUtil.nextLine("ID : ");
      String pw = ScanUtil.nextLine("PW : ");
      List<Object> param = new ArrayList<>();
      param.add(id);
      sessionStorage.put("id", id); // id값 sessionstorage에 저장
      param.add(pw);
      boolean login = memberService.login(param);
      if (!login) {
         var();
         System.out.println("ID 혹은 PW를 잘못 입력 하셨습니다.");
         System.out.println("1. 재로그인");
         System.out.println("2. 회원가입");
         System.out.println("3. 초기 화면으로");
         System.out.println();

         int sel = ScanUtil.nextInt("숫자 입력 :");
         switch (sel) {
            case 1:
               return View.LOGIN;
            case 2:
               return View.SIGN_UP;
            case 3:
               return View.MAIN;
            default: return View.LOGIN;
         }
      }
      View view = null;
      if (sessionStorage.containsKey("view")) {
         view = (View) sessionStorage.get("view");
         return view;
      }
      return View.ADMIN;
   }

   public View main() {
      var();
      System.out.println("환영합니다 자바 영화관입니다");
      System.out.println("1. 로그인");
      System.out.println("2. 회원가입");

      int sel = ScanUtil.menu();
      // 궁금하면 nextInt 클릭
      switch (sel) {
         case 1:
            return View.LOGIN;
         case 2:
            return View.SIGN_UP;
         default:
            return View.MAIN;
      }
   }
}
