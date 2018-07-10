package com.szgentech.metro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.Page;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.dao.SettlingDao;
import com.szgentech.metro.model.Homepage;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervaliLR;
import com.szgentech.metro.model.MetroUpdateApp;
import com.szgentech.metro.model.SettlingMd;
import com.szgentech.metro.model.SettlingName;
import com.szgentech.metro.model.SettlingParticulars;
import com.szgentech.metro.model.SettlingSp;
import com.szgentech.metro.model.SettlingTotal;
import com.szgentech.metro.model.SettlingVelocity;
import com.szgentech.metro.service.IMetroSettlingService;

/**
 * APP沉降速率
 * @author admin
 *
 */
@Service("settlingService")
public class MetroSettlingService implements IMetroSettlingService{

	@Autowired
	private SettlingDao settlingDao;

	@Override
	public List<Homepage> getfindSettling(Long userId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		
		List<Homepage> intervals = settlingDao.findHomepage(params);
		List<Homepage> list = new ArrayList<Homepage>();
		
		if(intervals!=null&&intervals.size()>0) {
			for (Homepage home : intervals) {  //线路遍历
				Homepage homepage = new Homepage();
				homepage.setId(home.getId());
				homepage.setLinename(home.getLinename());
				List<MetroLineIntervaliLR> intervaliLR2 = home.getIntervaliLR();
				List<MetroLineIntervaliLR> list2 = new ArrayList<MetroLineIntervaliLR>();
				for (MetroLineIntervaliLR lineIntervaliLR : intervaliLR2) {  //循环区间
					MetroLineIntervaliLR intervaliLR = new MetroLineIntervaliLR();
					intervaliLR.setId(lineIntervaliLR.getId());
					intervaliLR.setIntervalname(lineIntervaliLR.getIntervalname());
					Map<String, Object> params2 = new HashMap<>();
					params2.put("intervalid", intervaliLR.getId());
					SettlingVelocity settlingVelocity = settlingDao.getSettlingVelocity(params2);
					SettlingTotal settlingTotal = settlingDao.getSettlingTotal(params2);
					intervaliLR.setSettlingVelocity(settlingVelocity);
					intervaliLR.setSettlingTotal(settlingTotal);
					list2.add(intervaliLR);
				}
				
				homepage.setIntervaliLR(list2);
				list.add(homepage);
			}
		}
		return list;
	}

	@Override
	public List<SettlingName> findSettlingName(Long intervalid) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalid", intervalid);
		List<SettlingName> intervals = settlingDao.findSettlingName(params);
		
		return intervals;
	}

	@Override
	public SettlingParticulars findSettlingParticulars(String spname) {
		SettlingParticulars particulars = new SettlingParticulars();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spname", spname);
		
		List<SettlingSp> list = settlingDao.findSettlingSp(params);
		particulars.setSettlingSps(list);
		List<SettlingMd> list2 = settlingDao.findSettlingMd(params);
		particulars.setSettlingMds(list2);
//		if(list!=null && list.size()>0) {
//			String[] arr = new String[list.size()];
//			for(int i=0;i<list.size();i++){
//				 arr[i]=list.get(i);//数组赋值了。
//			    }
//		}
		return particulars;
	}

	@Override
	public MetroLineInterval findDuctId(Long intervalId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		MetroLineInterval interval = settlingDao.findDuctId(params);
		return interval;
	}

	@Override
	public MetroUpdateApp MetroUpdateData(Integer facilityflag) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("facilityflag", facilityflag);
		MetroUpdateApp listmetro = settlingDao.MetroUpdateData(params);
		if (listmetro != null) {
			return listmetro;
		}
		return null;
	}

	@Override
	public boolean addMetroUpdateApp(String upAppname,Integer upversioncode,String upversionname,
			String upUpdateURL,String upUpdatingContent,Integer facilityflag,Integer upWhetherupdating) {
		MetroUpdateApp upapp = new MetroUpdateApp();
		upapp.setUpAppname(upAppname);
		upapp.setUpversioncode(upversioncode);
		upapp.setUpversionname(upversionname);
		upapp.setUpUpdateURL(upUpdateURL);
		upapp.setUpUpdatingContent(upUpdatingContent);
		upapp.setFacilityflag(facilityflag);
		upapp.setUpWhetherupdating(upWhetherupdating);
		int toun = settlingDao.insertObj(upapp);
		return toun > 0 ? true : false;
	}

	@Override
	public boolean findMetroUpdateAppC(Integer upversioncode) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("upversioncode", upversioncode);
		return settlingDao.findMetroUpdateAppC(params)>0 ? true : false;
	}

	@Override
	public PageResultSet<MetroUpdateApp> ListMetroUpdateData(Integer facilityflag, int pageNum, int pageSize) {
		PageResultSet<MetroUpdateApp> pageResultSet = new PageResultSet<MetroUpdateApp>();
		Map<String, Object> params = new HashMap<>();
		params.put("facilityflag", facilityflag);
		
		int  total = settlingDao.countMetroUpdateData(params);
		Page page = new Page(total, pageSize, pageNum);
		List<MetroUpdateApp> listmetro = null;
		if(total > 0){
			params.put("start", page.getBeginIndex());
			params.put("pageSize", page.getPageSize());
			listmetro = settlingDao.ListMetroUpdateData(params);
		}
		pageResultSet.setList(listmetro);
		pageResultSet.setPage(page);
		
		
		return pageResultSet;
	}

	@Override
	public boolean deleteObj(Integer upid) {
		Map<String, Object> params = new HashMap<>();
		params.put("upid", upid);
		int r = settlingDao.deleteObj(params);
		return r > 0 ? true : false;
	}
	
}
