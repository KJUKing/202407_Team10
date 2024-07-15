package service;

import java.util.List;

import dao.SeatDao;
import vo.SeatVo;

public class SeatService {
	private static SeatService instance;

	private SeatService() {

	}

	public static SeatService getInstance() {
		if (instance == null) {
			instance = new SeatService();
		}
		return instance;
	}
	SeatDao dao = SeatDao.getInstance();


	public void seat(List<Object> param) {
		dao.seat(param);
		
	}

	public void seat1(List<Object> param) {
		dao.seat1(param);
		
	}

	public List<SeatVo> seatList(List<Object> param1) {
		
		return dao.seatList(param1);
	}

	public SeatVo seatInfo(List<Object> param) {
		
		return dao.seatInfo(param);
	}

	public List<SeatVo> seatInfo1(List<Object> param) {
		
		return dao.seatInfo1(param);
	}

	public void seatDelete(List<Object> paramSt) {
		dao.seatDelete(paramSt);
		
	}

	public void seatDelete1(List<Object> paramSt) {
		dao.seatDelete1(paramSt);
		
	}
}
