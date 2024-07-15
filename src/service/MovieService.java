package service;

import java.util.List;
import java.util.Map;

import controller.MainController;
import dao.MovieDao;
import vo.MovieVo;
import vo.ScheduleVo;

public class MovieService {
	private static MovieService instance;

	private MovieService() {

	}

	public static MovieService getInstance() {
		if (instance == null) {
			instance = new MovieService();
		}
		return instance;
	}
	MovieDao dao = MovieDao.getInstance();
	
	public boolean login(List<Object> param) {
		boolean pass = true;
		Map<String, Object> map = dao.login(param);
		if(map == null) {
			return false;
		}
		MainController.sessionStorage.put("member", map);
		// sessionStorage 은 MainController에 만들어 놓은 Map 
		return pass;
	}
	
	public void login(int memNo) {
		Map<String, Object> map = dao.login(memNo);
		MainController.sessionStorage.put("member", map);
}

	public void insert(List<Object> param) {
		dao.insert(param);
	}

	public MovieVo movieDetail(List<Object> param) {
		
		return dao.movieDetail(param);
	}

	public List<MovieVo> movieList() {
		
		return dao.movieList();
	}

	public MovieVo ticketSelect(List<Object> param) {
		
		return dao.ticketSelect(param);
	}


}