package service;

import java.util.List;

import dao.TicketDao;
import vo.MemberVo;

public class TicketService {
	private static TicketService instance;

	private TicketService() {

	}

	public static TicketService getInstance() {
		if (instance == null) {
			instance = new TicketService();
		}
		return instance;
	}
	TicketDao dao = TicketDao.getInstance();

	public void tkInsert(List<Object> paramTk) {
		dao.tkInsert(paramTk);
		
	}

	public void tkInsert1(List<Object> paramTk) {
		dao.tkInsert1(paramTk);
		
	}

	public List<MemberVo> ticketCheck(List<Object> param) {
		return dao.ticketCheck(param);

	}

	public List<MemberVo> ticketCheckList(List<Object> param) {
		return dao.ticketCheckList(param);
	}

}
