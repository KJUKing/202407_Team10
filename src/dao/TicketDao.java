package dao;

import java.util.List;

import util.JDBCUtil;
import vo.MemberVo;

public class TicketDao {
	private static TicketDao instance;

	private TicketDao() {

	}

	public static TicketDao getInstance() {
		if (instance == null) {
			instance = new TicketDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();

	public void tkInsert(List<Object> paramTk) {
		String sql = "INSERT INTO TICKET\r\n" + 
				" VALUES ((SELECT NVL(MAX(TICKET),0)+1 FROM TICKET), ? , ? , ? , ?)";
		
		jdbc.update(sql,paramTk);
		
	}

	public void tkInsert1(List<Object> paramTk) {
		String sql = "INSERT INTO TICKET\r\n" + 
				"SELECT (SELECT NVL(MAX(TICKET),0)+1 FROM TICKET), ?, ?, ?, ?\r\n" + 
				"FROM DUAL\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT (SELECT NVL(MAX(TICKET),0)+2 FROM TICKET), ?, ?, ?, ?\r\n" + 
				"FROM DUAL";
		jdbc.update(sql,paramTk);
	}

    public List<MemberVo> ticketCheck(List<Object> param) {
		String sql = "SELECT G.FK_MOVIE_CODE AS movie_code, H.RN AS row_num, H.SN AS seat_num, H.FDMN AS mem_name, H.FDNIC AS mem_nick,  H.FTP AS price, G.FK_THE_CODE AS FTC\n" +
				"         FROM SCHEDULE G, (SELECT E.ROW_NUM AS RN, E.SEAT_NUM AS SN, FK_THE_CODE1 AS TC, F.DMN AS FDMN, F.DNIC AS FDNIC, F.DTI AS FDTI, F.TP AS FTP\n" +
				"                             FROM SEAT E, (SELECT FK_SEAT_CODE AS SC , D.MN AS DMN, D.NIC AS DNIC, D.TI AS DTI, D.TP\n" +
				"                                             FROM BOOKING C, (SELECT A.MEM_NAME AS MN, A.MEM_NICK AS NIC, B.TICKET AS TI, B.FK_BK_CODE AS BK, B.TOTAL_PRICE AS TP\n" +
				"                                                                FROM MEMBER A, TICKET B\n" +
				"                                                               WHERE A.MEM_NO = B.FK_MEM_NO\n" +
				"                                                                 AND A.MEM_ID = ?) D\n" +
				"                                            WHERE C.BK_CODE = D.BK)F\n" +
				"                            WHERE F.SC = E.SEAT_CODE\n" +
				"                              AND E.STATUS = 'Y') H\n" +
				"        WHERE G.FK_THE_CODE = H.TC";

		return jdbc.selectList(sql, param, MemberVo.class);
    }

	public List<MemberVo> ticketCheckList(List<Object> param) {

		String sql = "SELECT J.HFDNIC AS MEM_NICK, I.MN AS MOVIE_NAME, I.EGN AS GENRE, I.KPT AS PART_TYPE, I.RUNNING_TIME AS RUNNING_TIME, J.HRN AS ROW_NUM, J.HSN AS SEAT_NUM, J.HFTP AS PRICE, I.FDTN AS THE_NAME\r\n" + 
				"FROM (SELECT G.FK_MOVIE_CODE AS FMC, H.RN AS HRN, H.SN AS HSN, H.FDMN AS HFDMN, H.FDNIC AS HFDNIC, H.FDTI AS HFDTI, H.FTP AS HFTP, G.FK_THE_CODE AS FTC\r\n" + 
				"         FROM SCHEDULE G, (SELECT E.ROW_NUM AS RN, E.SEAT_NUM AS SN, FK_THE_CODE1 AS TC, F.DMN AS FDMN, F.DNIC AS FDNIC, F.DTI AS FDTI, F.TP AS FTP\r\n" + 
				"                             FROM SEAT E, (SELECT FK_SEAT_CODE AS SC , D.MN AS DMN, D.NIC AS DNIC, D.TI AS DTI, D.TP\r\n" + 
				"                                             FROM BOOKING C, (SELECT A.MEM_NAME AS MN, A.MEM_NICK AS NIC, B.TICKET AS TI, B.FK_BK_CODE AS BK, B.TOTAL_PRICE AS TP\r\n" + 
				"                                                                FROM MEMBER A, TICKET B\r\n" + 
				"                                                               WHERE A.MEM_NO = B.FK_MEM_NO\r\n" + 
				"                                                                 AND A.MEM_ID = ?) D\r\n" + 
				"                                            WHERE C.BK_CODE = D.BK)F\r\n" + 
				"                            WHERE F.SC = E.SEAT_CODE\r\n" + 
				"                              AND E.STATUS = 'Y') H\r\n" + 
				"        WHERE G.FK_THE_CODE = H.TC)J,\r\n" + 
				"      (SELECT (F.DPT||'-'||(TRUNC((TO_NUMBER(SUBSTR(F.DPT,1,2))*60+\r\n" + 
				"              E.MOVIE_TIME)/60)||':'||MOD((TO_NUMBER(SUBSTR(F.DPT,1,2))*60+\r\n" + 
				"              E.MOVIE_TIME),60))) AS RUNNING_TIME,\r\n" + 
				"              E.MOVIE_CODE AS MC,\r\n" + 
				"              E.MOVIE_NAME AS MN,\r\n" + 
				"              F.DTC AS FDTC,\r\n" + 
				"              E.GENRE AS EGN,\r\n" + 
				"              E.MOVIE_TIME AS EMT,\r\n" + 
				"              K.PART_TYPE AS KPT,\r\n" + 
				"              F.DTN AS FDTN\r\n" + 
				"              \r\n" + 
				"         FROM MOVIE E, (SELECT C.FK_MOVIE_CODE AS MC,\r\n" + 
				"                              D.PT AS DPT,\r\n" + 
				"                              D.TC AS DTC,\r\n" + 
				"                              D.TN AS DTN\r\n" + 
				"                         FROM SCHEDULE C, (SELECT B.THE_CODE AS TC,\r\n" + 
				"                                                  A.PART_TIME AS PT,\r\n" + 
				"                                                  B.THE_NAME AS TN\r\n" + 
				"                                             FROM TIME A, THEATER B\r\n" + 
				"                                            WHERE A.TIME_CODE = B.FK_TIME_CODE) D\r\n" + 
				"                        WHERE D.TC = C.FK_THE_CODE) F, RATE_CODE K\r\n" + 
				"        WHERE F.MC = E.MOVIE_CODE\r\n" + 
				"          AND K.RATE_CODE = E.FK_RATE\r\n" + 
				"          AND E.MOVIE_CODE = ?)I\r\n" + 
				"      \r\n" + 
				"WHERE J.FMC = I.MC\r\n" + 
				"AND J.FTC = I.FDTC\r\n" + 
				"ORDER BY SEAT_NUM, ROW_NUM";

		return jdbc.selectList(sql, param, MemberVo.class);
	}

	public List<MemberVo> ticketList(List<Object> param) {
		String sql = "SELECT MOVIE_NAME\r\n" + 
				"FROM movie E, (SELECT G.FK_MOVIE_CODE AS FMC, H.RN AS HRN, H.SN AS HSN, H.FDMN AS HFDMN, H.FDNIC AS HFDNIC, H.FDTI AS HFDTI, H.FTP AS HFTP, G.FK_THE_CODE AS FTC\r\n" + 
				"         FROM SCHEDULE G, (SELECT E.ROW_NUM AS RN, E.SEAT_NUM AS SN, FK_THE_CODE1 AS TC, F.DMN AS FDMN, F.DNIC AS FDNIC, F.DTI AS FDTI, F.TP AS FTP\r\n" + 
				"                             FROM SEAT E, (SELECT FK_SEAT_CODE AS SC , D.MN AS DMN, D.NIC AS DNIC, D.TI AS DTI, D.TP\r\n" + 
				"                                             FROM BOOKING C, (SELECT A.MEM_NAME AS MN, A.MEM_NICK AS NIC, B.TICKET AS TI, B.FK_BK_CODE AS BK, B.TOTAL_PRICE AS TP\r\n" + 
				"                                                                FROM MEMBER A, TICKET B\r\n" + 
				"                                                               WHERE A.MEM_NO = B.FK_MEM_NO\r\n" + 
				"                                                                 AND A.MEM_ID = ?) D\r\n" + 
				"                                            WHERE C.BK_CODE = D.BK)F\r\n" + 
				"                            WHERE F.SC = E.SEAT_CODE\r\n" + 
				"                              AND E.STATUS = 'Y') H\r\n" + 
				"        WHERE G.FK_THE_CODE = H.TC) I\r\n" + 
				"WHERE E.MOVIE_CODE = I.FMC";
		
		return jdbc.selectList(sql, param, MemberVo.class);
	}
}
