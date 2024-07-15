package dao;

import java.util.List;

import util.JDBCUtil;
import vo.SeatVo;

public class SeatDao {
	private static SeatDao instance;

	private SeatDao() {

	}

	public static SeatDao getInstance() {
		if (instance == null) {
			instance = new SeatDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();



	public void seat(List<Object> param) {
		String sql = " UPDATE SEAT\r\n" + 
				" SET STATUS = 'Y'\r\n" + 
				" WHERE ROW_NUM = ? "
				+ " AND SEAT_NUM = ? "
				+ " AND FK_THE_CODE1 = ? ";
		
		jdbc.update(sql, param);
	}

	public void seat1(List<Object> param) {
		String sql = " UPDATE SEAT\r\n" + 
				" SET STATUS = 'Y'\r\n" + 
				" WHERE ((ROW_NUM = ? AND SEAT_NUM = ?)\r\n" + 
				" OR    (ROW_NUM = ? AND SEAT_NUM = ?))\r\n"+
				" AND FK_THE_CODE1 = ? ";
		
		jdbc.update(sql, param);
		
	}

	public List<SeatVo> seatList(List<Object> param1) {
		String sql = " SELECT A.ROW_NUM, A.SEAT_NUM, A.STATUS, A.FK_THE_CODE1, B.SEAT_MAX\r\n" + 
				"  FROM SEAT A, THEATER B\r\n" + 
				" WHERE A.FK_THE_CODE1 = B.THE_CODE\r\n" + 
				"   AND FK_THE_CODE1 = ? ";
		return jdbc.selectList(sql,param1,SeatVo.class);
	}

	public SeatVo seatInfo(List<Object> param) {
		String sql = "SELECT SEAT_CODE\r\n" + 
				"FROM SEAT\r\n" + 
				"WHERE ROW_NUM = ? \r\n" + 
				"AND SEAT_NUM = ? \r\n" + 
				"AND STATUS = 'Y'\r\n" + 
				"AND FK_THE_CODE1 = ?";
		return jdbc.selectOne(sql, param, SeatVo.class);
	}

	public List<SeatVo> seatInfo1(List<Object> param) {
		String sql = "SELECT *\r\n" + 
				"FROM SEAT\r\n" + 
				"WHERE ((ROW_NUM = ? AND SEAT_NUM = ?)\r\n" + 
				"OR (ROW_NUM = ? AND SEAT_NUM = ?))\r\n" + 
				"AND STATUS = 'Y'\r\n" + 
				"AND FK_THE_CODE1 = ?";
		return jdbc.selectList(sql, param, SeatVo.class);
	}

	public void seatDelete(List<Object> paramSt) {
		String sql = "UPDATE SEAT\r\n" + 
				"SET STATUS = 'N'\r\n" + 
				"WHERE SEAT_CODE = ?";
		jdbc.update(sql, paramSt);
		
	}

	public void seatDelete1(List<Object> paramSt) {
		String sql = "UPDATE SEAT\r\n" + 
				"SET STATUS = 'N'\r\n" + 
				"WHERE SEAT_CODE IN(?,?)";
		jdbc.update(sql, paramSt);
		
	}
}
