package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JDBCMain {
	public static void main(String[] args) {
		JDBCUtil jdbc = JDBCUtil.getInstance();
		
//		String sql = "SELECT * FROM MEMBER WHERE MEM_ID = ? AND MEM_PASS = ? ";
//		// ? 로 동적으로 만들어도됨 개수는 또 늘려도됨
//		List<Object> param = new ArrayList();
//		param.add("a001");
//		param.add("asdfasdf");
//		// Scanner 값을 받아서 param.add(스캐너 입력값); 을 해도 됨
//		Map<String, Object> map = jdbc.selectOne(sql, param);
//		// 한회원 가져오는법 selectOne
//		System.out.println(map);
		
//		String sql = " SELECT * FROM MEMBER WHERE MEM_LIKE = ? ";
//		// 한줄을 나타내는 것이기에 list에 담아서 실행
//		List<Object> param = new ArrayList();
//		param.add("낚시");
//		List<Map<String, Object>> list = jdbc.selectList(sql,param);
//		// 모든 회원들을 출력할때 // 여러 값을 출력할 때
//		for (Map<String, Object> map : list) {
//			System.out.println(map);
//		}
		
		// sql 업데이트
		// java에서는 오타확인이 어려우니 sql에서 써놓고 가져오기 \r\n+
		// 가져왔을때 문안에 ;조심
//		String sql ="UPDATE JAVA_MEMBER\r\n"+
//				   "SET MEM_NAME = ?\r\n"+
//				   "WHERE MEM_NO =?";
//		List<Object> param = new ArrayList();
//		param.add("박반장");
//		param.add(3);
//		int num = jdbc.update(sql,param);
//		System.out.println(num+"행 변경되었습니다");
//		num==0 라면 업데이트에 오류
		
		// DELETE
		String sql ="DELETE JAVA_MEMBER WHERE MEM_NO= 3 "; 
		jdbc.update(sql);
	}
}
