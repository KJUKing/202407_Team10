package vo;

import lombok.Data;

@Data
public class MovieVo {
	private String movie_code;
	private String movie_name;
	private String genre;
	private int fk_rate;
	private String RATETYPE;
	private String movie_no;
	private String mn; //영화이름
	private String gn; //영화장르
	private String tn; //상영관이름
	private String part_type; //이용가
	private int pr; // 가격
	private String mono; // 영화번호
	private int movie_time; // 영화 러닝타임
}
