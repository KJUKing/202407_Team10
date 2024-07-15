package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import vo.MovieVo;

public class MovieDao {
	private static MovieDao instance;

	private MovieDao() {

	}

	public static MovieDao getInstance() {
		if (instance == null) {
			instance = new MovieDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();

	public Map<String, Object> login(List<Object> param) {
		String sql = "SELECT *\r\n" + 
				"FROM MEMBER\r\n" + 
				"WHERE MEM_ID = ? \r\n" + 
				" AND MEM_PASS = ? ";
		
		return jdbc.selectOne(sql,param);
	}
	public Map<String, Object> login(int memNo) {
		String sql = " SELECT *" + 
				" FROM MOVIE_MEMBER" + 
				" WHERE MEM_NO = "+memNo;
		return jdbc.selectOne(sql);
	}

	public void insert(List<Object> param) {
		String sql = "INSERT INTO MOVIE_MEMBER\r\n" + 
				"VALUES ((SELECT NVL(MAX(MEM_NO),0)+1 FROM MOVIE_MEMBER), \r\n" + 
				"        ? , ? , ? , TO_DATE( ? ), ? )";
		
		jdbc.update(sql,param);
	}

	public MovieVo movieDetail(List<Object> param) {
		String sql = "SELECT MOVIE_CODE, MOVIE_NAME, GENRE,FK_RATE, B.PT AS RATETYPE\r\n" + 
				"FROM MOVIE A, (SELECT RATE_CODE,\r\n" + 
				"                        PART_TYPE AS PT \r\n" + 
				"                 FROM RATE_CODE)B\r\n" + 
				"WHERE A.FK_RATE = B.RATE_CODE\r\n" + 
				"AND MOVIE_CODE = 'm00'||? ";
		
		return jdbc.selectOne(sql, param, MovieVo.class);
	}

	public List<MovieVo> movieList() {
		String sql = "SELECT F.MN, F.GN, F.TN ,E.PART_TYPE, F.PR, SUBSTR(F.MC,4,1) AS MONO\r\n" + 
				"FROM RATE_CODE E , (SELECT C.MOVIE_CODE AS MC, C.MOVIE_NAME AS MN, C.GENRE AS GN, C.FK_RATE AS CFR, D.BTHE_NAME AS TN, D.BPRICE AS PR\r\n" + 
				"                      FROM MOVIE C, (SELECT A.FK_THE_CODE AS FKTC , A.FK_MOVIE_CODE AS FKMC , B.THE_NAME AS BTHE_NAME, B.PRICE AS BPRICE\r\n" + 
				"                                       FROM SCHEDULE A, THEATER B\r\n" + 
				"                                      WHERE A.FK_THE_CODE = B.THE_CODE\r\n" + 
				"                                        AND FK_TIME_CODE = 't01') D\r\n" + 
				"                     WHERE D.FKMC = C.MOVIE_CODE) F\r\n" + 
				"WHERE F.CFR = E.RATE_CODE\r\n" + 
				"ORDER BY 6";
		return jdbc.selectList(sql, MovieVo.class);
	}

	public MovieVo ticketSelect(List<Object> param) {
		String sql = "SELECT F.MN, F.GN, F.TN ,E.PART_TYPE, F.PR, SUBSTR(F.MC,4,1) AS MONO\r\n" + 
				"FROM RATE_CODE E , (SELECT C.MOVIE_CODE AS MC, C.MOVIE_NAME AS MN, C.GENRE AS GN, C.FK_RATE AS CFR, D.BTHE_NAME AS TN, D.BPRICE AS PR\r\n" + 
				"                      FROM MOVIE C, (SELECT A.FK_THE_CODE AS FKTC , A.FK_MOVIE_CODE AS FKMC , B.THE_NAME AS BTHE_NAME, B.PRICE AS BPRICE\r\n" + 
				"                                       FROM SCHEDULE A, THEATER B\r\n" + 
				"                                      WHERE A.FK_THE_CODE = B.THE_CODE\r\n" + 
				"                                        AND FK_TIME_CODE = 't01') D\r\n" + 
				"                     WHERE D.FKMC = C.MOVIE_CODE) F\r\n" + 
				"WHERE F.CFR = E.RATE_CODE\r\n" + 
				"AND SUBSTR(F.MC,4,1) = ? ";
		return jdbc.selectOne(sql, param, MovieVo.class);
	}



}
