package dao;

import java.util.List;

import util.JDBCUtil;
import vo.ScheduleVo;

public class ScheduleDao {
	private static ScheduleDao instance;

	private ScheduleDao() {

	}

	public static ScheduleDao getInstance() {
		if (instance == null) {
			instance = new ScheduleDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();

	public List<ScheduleVo> schedule(List<Object> param) {
		String sql = "SELECT (F.DPT||'-'||(TRUNC((TO_NUMBER(SUBSTR(F.DPT,1,2))*60+\r\n" + 
				"       E.MOVIE_TIME)/60)||':'||MOD((TO_NUMBER(SUBSTR(F.DPT,1,2))*60+\r\n" + 
				"       E.MOVIE_TIME),60))) AS RUNNING_TIME\r\n" + 
				"       \r\n" + 
				"FROM MOVIE E, (SELECT C.FK_MOVIE_CODE AS MC,\r\n" + 
				"                      D.PT AS DPT\r\n" + 
				"                 FROM SCHEDULE C, (SELECT B.THE_CODE AS TC,\r\n" + 
				"                                          A.PART_TIME AS PT\r\n" + 
				"                                     FROM TIME A, THEATER B\r\n" + 
				"                                    WHERE A.TIME_CODE = B.FK_TIME_CODE) D\r\n" + 
				"                WHERE D.TC = C.FK_THE_CODE) F\r\n" + 
				"WHERE F.MC = E.MOVIE_CODE\r\n" + 
				"AND E.MOVIE_CODE = 'm00'|| ?";
		return jdbc.selectList(sql, param, ScheduleVo.class);
	}

	public ScheduleVo scheduleDetail(List<Object> param) {
		String sql = "SELECT SCH_CODE, FK_THE_CODE\r\n" + 
				"FROM SCHEDULE C, (SELECT THE_CODE AS TH\r\n" + 
				"                    FROM THEATER A, (SELECT TIME_CODE AS TC\r\n" + 
				"                                       FROM TIME\r\n" + 
				"                                      WHERE TIME_CODE = 't0'||? ) B\r\n" + 
				"                   WHERE A.FK_TIME_CODE = B.TC) D\r\n" + 
				"WHERE C.FK_THE_CODE = D.TH\r\n" + 
				"AND FK_MOVIE_CODE = ? ";
		return jdbc.selectOne(sql, param, ScheduleVo.class);
	}

	public ScheduleVo ageCheck(List<Object> param) {
		String sql = "SELECT " + 
                "CASE WHEN A.AGE-B.PT >= 0 THEN 1 " + 
                "     ELSE 2 " + 
                "END AS OK " + 
                "FROM (SELECT MEM_NAME, " + 
                "             CASE WHEN TO_NUMBER(TO_CHAR(SYSDATE, 'MMDD')) - TO_NUMBER(TO_CHAR(MEM_BIR, 'MMDD')) >= 0 " + 
                "                  THEN EXTRACT(YEAR FROM SYSDATE) - TO_NUMBER(SUBSTR(MEM_BIR, 1, 4)) " + 
                "                  ELSE EXTRACT(YEAR FROM SYSDATE) - TO_NUMBER(SUBSTR(MEM_BIR, 1, 4)) - 1 " + 
                "             END AS AGE " + 
                "      FROM MEMBER " + 
                "      WHERE MEM_ID = ?) A, " + 
                "     (SELECT RATE_CODE, " + 
                "             CASE WHEN RATE_CODE = 1 THEN 0 " + 
                "                  WHEN RATE_CODE = 2 THEN 12 " + 
                "                  WHEN RATE_CODE = 3 THEN 15 " + 
                "                  WHEN RATE_CODE = 4 THEN 18 " + 
                "             END AS PT " + 
                "      FROM RATE_CODE " + 
                "      WHERE RATE_CODE = ?) B";
		
		return jdbc.selectOne(sql, param, ScheduleVo.class);
	}
}
