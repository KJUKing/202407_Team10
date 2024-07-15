package service;

import java.util.List;

import dao.BookingDao;
import vo.BookingVo;

public class BookingService {
	private static BookingService instance;

	private BookingService() {

	}

	public static BookingService getInstance() {
		if (instance == null) {
			instance = new BookingService();
		}
		return instance;
	}
	BookingDao dao = BookingDao.getInstance();

	public void bookingUpdate(List<Object> paramBk) {
		dao.bookingUpdate(paramBk);
		
	}

	public void bookingUpdate1(List<Object> paramBk) {
		dao.bookingUpdate1(paramBk);
		
	}

	public BookingVo bookingSelectOne(List<Object> paramBk) {
		return dao.bookingSelectOne(paramBk);
		
	}

	public BookingVo bookingSelect(List<Object> paramBk) {
		
		return dao.bookingSelect(paramBk);
	}

	public List<BookingVo> bookingSelectList(List<Object> paramBk) {
		
		return dao.bookingSelectList(paramBk);
	}

	public void bookingDelete(List<Object> paramBk) {
		dao.bookingDelete(paramBk);
		
	}

	public void bookingDelete1(List<Object> paramBk) {
		dao.bookingDelete1(paramBk);
		
	}
}
