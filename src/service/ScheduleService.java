package service;

import java.util.List;

import dao.ScheduleDao;
import vo.ScheduleVo;

public class ScheduleService {
	private static ScheduleService instance;

	private ScheduleService() {

	}

	public static ScheduleService getInstance() {
		if (instance == null) {
			instance = new ScheduleService();
		}
		return instance;
	}
	ScheduleDao dao = ScheduleDao.getInstance();
	
	public List<ScheduleVo> schedule(List<Object> param) {
		return dao.schedule(param);
	}

	public ScheduleVo scheduleDetail(List<Object> param) {
		return dao.scheduleDetail(param);
	}

	public ScheduleVo ageCheck(List<Object> param) {
		
		return dao.ageCheck(param);
	}
}
