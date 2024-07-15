package dao;

import util.JDBCUtil;
import vo.MovieVo;
import vo.MemberVo;

import java.util.List;
import java.util.Map;

public class MemberDao {

    private static MemberDao instance;

    private MemberDao() {

    }

    public static MemberDao getInstance() {
        if (instance == null) {
            instance = new MemberDao();
        }
        return instance;
    }
    JDBCUtil jdbc = JDBCUtil.getInstance();


    public MemberVo login(List<Object> param) {
        String sql = "SELECT *\n" +
                "FROM MEMBER\n" +
                "WHERE MEM_ID = ?\n" +
                "AND MEM_PASS = ?\n" +
                "AND DELYN = 'N'";

        return jdbc.selectOne(sql, param, MemberVo.class);
    }

    public void signUp(List<Object> param) {
        String sql = "INSERT INTO MEMBER VALUES ((SELECT NVL(MAX(MEM_NO),0)+1\n" +
                "                                    FROM MEMBER),\n" +
                "                                    ?, ?, ?, ?, 'N', ?)";
        jdbc.update(sql, param);
    }


    public void memberUpdate(List<Object> param, int sel) {
        String sql = "UPDATE MEMBER " +
                "     SET  ";
        if (sel == 1 || sel == 3) {
            sql += " MEM_NICK = ?";
            if (sel == 3) sql += " , ";
        }
        if (sel == 2 || sel == 3) {
            sql += "MEM_PASS  = ?";
        }
        sql += " WHERE MEM_NO = ? ";

        jdbc.update(sql, param);
    }

    public void memberDelete(List<Object> param) {
        String sql = " UPDATE MEMBER " +
                "      SET DELYN = 'Y'" +
                "      WHERE MEM_NO = ?";
        jdbc.update(sql, param);
    }

	public MemberVo ageCheck(List<Object> param) {
//		String sql = "SELECT \r\n" + 
//				"    MEM_NAME AS NAME,\r\n" + 
//				"    (EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM MEM_BIR)) - \r\n" + 
//				"    CASE \r\n" + 
//				"        WHEN TO_CHAR(SYSDATE, 'MMDD') < TO_CHAR(MEM_BIR, 'MMDD') THEN 1 \r\n" + 
//				"        ELSE 0 \r\n" + 
//				"    END AS REAL_AGE\r\n" + 
//				"FROM MEMBER\r\n" + 
//				"WHERE MEM_ID = ? ";
		String sql = "SELECT \r\n" + 
				"CASE WHEN A.AGE-B.PT >=0 THEN 1\r\n" + 
				"                        ELSE 2\r\n" + 
				"                        END AS OK\r\n" + 
				"FROM (SELECT MEM_NAME,\r\n" + 
				"        CASE WHEN TO_NUMBER(TO_CHAR(SYSDATE, 'MMDD'))-TO_NUMBER(TO_CHAR(MEM_BIR,'MMDD')) >=0 THEN EXTRACT(YEAR FROM SYSDATE)-TO_NUMBER(TO_CHAR(MEM_BIR,'YYYY'))\r\n" + 
				"                                                                                    ELSE EXTRACT(YEAR FROM SYSDATE)-TO_NUMBER(TO_CHAR(MEM_BIR,'YYYY'))-1\r\n" + 
				"                                                                                    END AS AGE\r\n" + 
				"        FROM MEMBER\r\n" + 
				"       WHERE MEM_ID = ?) A,\r\n" + 
				"       (SELECT RATE_CODE,\r\n" + 
				"          CASE WHEN RATE_CODE=1 THEN 0\r\n" + 
				"               WHEN RATE_CODE=2 THEN 12\r\n" + 
				"               WHEN RATE_CODE=3 THEN 15\r\n" + 
				"               WHEN RATE_CODE=4 THEN 18\r\n" + 
				"                END AS PT\r\n" + 
				"         FROM RATE_CODE\r\n" + 
				"        WHERE RATE_CODE = ? ) B ";

		return jdbc.selectOne(sql, param, MemberVo.class);
	}
	
    public List<MemberVo> idInfo() {
        String sql = "SELECT MEM_ID\n" +
                "FROM MEMBER\n";
                //회원 탈퇴한 id여도 db에는 남아있기때문에 같이 집계한다
                //그러므로 DELYN = 'N' 값만 찾는게아니라 전체조회를 실시
        return jdbc.selectList(sql, MemberVo.class);
    }

}
