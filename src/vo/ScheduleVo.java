package vo;

import lombok.Data;

@Data
public class ScheduleVo {
	private String sch_code;
	private String fk_movie_code;
	private String fk_the_code;
	private String PART_TIME;
	private int ok;
	private String running_time;
}
