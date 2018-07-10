package com.szgentech.metro.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroOperateDao;
import com.szgentech.metro.model.MetroUserOperateRec;
import com.szgentech.metro.model.Operation;
import com.szgentech.metro.model.OperationRecord;
import com.szgentech.metro.model.UserRecordAnaly;
import com.szgentech.metro.service.IMetroOperateService;
import com.szgentech.metro.vo.MonitorLrAlldicView;

@Service("operateService")
public class MetroOperateService extends BaseService<MetroUserOperateRec> implements IMetroOperateService {
	@Autowired
	private IMetroOperateDao operateDao;

	@Override
	public PageResultSet<MetroUserOperateRec> findMetroOperateInfo(int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		return this.getPageResultSet(params, pageNum, pageSize, operateDao);
	}

	@Override
	public boolean addLogs(List<MetroUserOperateRec> logs) {
		Map<String, Object> params = new HashMap<>();
		params.put("logs", logs);
		int count = operateDao.insertObjs(params);
		return count > 0 ? true : false;
	}

	@Override
	public List<OperationRecord> operateList(String starttime, String endtime) {
		String starttime2 = null;
		String endtime2 = null;
		if (starttime == null || endtime == null) {
			String qq = "00:00:00";
			String ww = "23:59:59";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = new Date();
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -6);
			date = calendar.getTime();
			String aa = dateFormat.format(date2);
			String bb = dateFormat.format(date);
			
			starttime2 = aa + " " + ww;
			endtime2 = bb + " " + qq;
		}else{
			starttime2 = starttime;
			endtime2 = endtime;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("starttime", starttime2);
		params.put("endtime", endtime2);
		List<MetroUserOperateRec> user = operateDao.usernameList(params);
		List<MetroUserOperateRec> visit = operateDao.visitMenuList(params);
		List<MetroUserOperateRec> operat = operateDao.operationList(params);
		List<OperationRecord> list3 = new ArrayList<OperationRecord>();
		UserRecordAnaly mav = null;
		OperationRecord record = null;
		Operation operation = null;
		for (int i = 0; i < user.size(); i++) {
			
			List<UserRecordAnaly> list2 = new ArrayList<UserRecordAnaly>();
			int b = 0;
			for (MetroUserOperateRec visit2 : visit) {
				List<Operation> list = new ArrayList<Operation>();
				for (MetroUserOperateRec visit3 : operat) {
					Map<String, Object> params3 = new HashMap<>();
					params3.put("username", user.get(i).getUsername());
					params3.put("visitmenu", visit2.getVisitMenu());
					params3.put("operation", visit3.getOperation());
					params3.put("starttime", starttime2);
					params3.put("endtime", endtime2);
					int user3 = operateDao.operation(params3);
					if(user3>0){
						String visi = visit3.getOperation();
						operation = new Operation();
						operation.setOperation(visi);
						operation.setModuleamount(user3);
//						System.out.println("用户:" + user.get(i).getUsername() + "操作了:" + visit3.getOperation() + "===" + user3 + "===次");
						list.add(operation);
					}
				}
				Map<String, Object> params2 = new HashMap<>();
				params2.put("username", user.get(i).getUsername());
				params2.put("visitmenu", visit2.getVisitMenu());
				params2.put("starttime", starttime2);
				params2.put("endtime", endtime2);
				int user2 = operateDao.visitMenu(params2);
				if (user2 > 0) {
					b += user2;
					String visi = visit2.getVisitMenu();
//					System.out.println("用户:" + user.get(i).getUsername() + "操作了:" + visi + "=菜单模块===" + user2 + "===次");
					mav = new UserRecordAnaly();
					mav.setName(user.get(i).getUsername());
					mav.setVisitMenu(visi);
					mav.setAmount(user2);
					mav.setOperations(list);
					list2.add(mav);
				}
			}
			
			record = new OperationRecord();
			record.setNsername(user.get(i).getUsername());
			record.setUseramount(b);
			record.setAnalies(list2);
			list3.add(record);
//			System.out.println("用户:" + user.get(i).getUsername() + "一共操作了" + b + "次");
		}
		return list3;
	}

}
