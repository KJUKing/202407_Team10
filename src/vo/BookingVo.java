package vo;

import lombok.Data;

@Data
public class BookingVo {
	private int bk_code;
	private String fk_sch_code;
	private String fk_seat_code;
}
