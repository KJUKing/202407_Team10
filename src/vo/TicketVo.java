package vo;

import lombok.Data;

@Data
public class TicketVo {
	private int ticket;
	private int fk_mem_no;
	private int fk_bk_code;
	private int fk_pay_code;
	private int total_price;
}
