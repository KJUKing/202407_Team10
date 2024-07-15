package vo;

import lombok.Data;

@Data
public class SeatVo {
	private String seat_code;
	private String seat_info;
	private String row_num;
	private int seat_num;
	private String status;
	private String fk_the_code1;
	private int seat_max;
}
