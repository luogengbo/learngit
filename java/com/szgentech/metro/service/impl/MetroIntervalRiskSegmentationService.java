package com.szgentech.metro.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.background.vo.IntervalRiskSegmentationVO;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.base.utils.SMSUtil;
import com.szgentech.metro.base.utils.ZookeTransactionException;
import com.szgentech.metro.dao.IMetroIntervalRiskSegmentationDao;
import com.szgentech.metro.dao.IMetroUserRiskSegmentationRelDao;
import com.szgentech.metro.model.MetroIntervalRiskSegmentation;
import com.szgentech.metro.model.MetroIntervalRiskSegmentationRec;
import com.szgentech.metro.model.MetroUserRiskSegmentationRel;
import com.szgentech.metro.service.IMetroIntervalRiskSegmentationRecService;
import com.szgentech.metro.service.IMetroIntervalRiskSegmentationService;
import com.szgentech.metro.vo.IhistorianResponse;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 盾构地质风险划分阻段划分接口实现
 * 
 * @author luohao
 *
 */
@Service("riskSegmentationService")
public class MetroIntervalRiskSegmentationService extends BaseService<MetroIntervalRiskSegmentation>
		implements IMetroIntervalRiskSegmentationService {
	private static Logger logger = Logger.getLogger(MetroIntervalRiskSegmentationService.class);

	private static int WARNING_NUM = 10; // 风险中报警间隔环数

	@Autowired
	private IMetroIntervalRiskSegmentationDao riskSegmentationDao;

	@Autowired
	private IMetroUserRiskSegmentationRelDao riskSegmentationRelDao;

	@Autowired
	private IMetroIntervalRiskSegmentationRecService riskSegmentationRecService;

	/**
	 * 查找地质风险划分阻段划分数据
	 */
	@Override
	public List<MetroIntervalRiskSegmentation> findriskSegmentation(long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return riskSegmentationDao.findriskSegmentation(params);
	}

	/**
	 * 分页查询 地质风险划分阻段划分数据
	 * 
	 * @param intervalId
	 *            线路区间id
	 * @param leftOrRight
	 *            左右线标识
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroIntervalRiskSegmentation> findriskSegmentationInfo(Long intervalId, String leftOrRight,
			int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		PageResultSet<MetroIntervalRiskSegmentation> resultSet = getPageResultSet(params, pageNum, pageSize,
				riskSegmentationDao);
		return resultSet;
	}

	/**
	 * 更新地质风险划分阻段划分数据
	 */
	@Override
	public boolean updateObj(MetroIntervalRiskSegmentation riskSegmentation) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskId", riskSegmentation.getId());
		params.put("intervalId", riskSegmentation.getIntervalId());
		params.put("leftOrRight", riskSegmentation.getLeftOrRight());
		params.put("geologicNo", riskSegmentation.getGeologicNo());
		params.put("geologicDescription", riskSegmentation.getGeologicDescription());
		params.put("hydrogeololgy", riskSegmentation.getHydrogeololgy());
		params.put("riskNo", riskSegmentation.getRiskNo());
		params.put("riskPoint", riskSegmentation.getRiskPoint());
		params.put("riskPhoto", riskSegmentation.getRiskPhoto());
		params.put("riskStartRing", riskSegmentation.getRiskStartRing());
		params.put("riskEndRing", riskSegmentation.getRiskEndRing());
		params.put("startMileage", riskSegmentation.getStartMileage());
		params.put("endMileage", riskSegmentation.getEndMileage());
		params.put("earlyWarningRing", riskSegmentation.getEarlyWarningRing());
		params.put("warningLevel", riskSegmentation.getWarningLevel());
		params.put("riskDescription", riskSegmentation.getRiskDescription());
		params.put("riskImg1Url", riskSegmentation.getRiskImg1Url());
		params.put("riskImg2Url", riskSegmentation.getRiskImg2Url());
		params.put("riskImg3Url", riskSegmentation.getRiskImg3Url());
		int r = riskSegmentationDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更新风险图片url
	 */
	@Override
	public boolean updateImg(MetroIntervalRiskSegmentation rs) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskId", rs.getId());
		params.put("riskImg1Url", rs.getRiskImg1Url());
		params.put("riskImg2Url", rs.getRiskImg2Url());
		params.put("riskImg3Url", rs.getRiskImg3Url());
		int r = riskSegmentationDao.updateImg(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateRiskImg(Long riskId, String Img1Url, String Img2Url, String Img3Url) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskId", riskId);
		MetroIntervalRiskSegmentation rs = riskSegmentationDao.findObjById(params);
		rs.setRiskImg1Url(Img1Url);
		rs.setRiskImg2Url(Img2Url);
		rs.setRiskImg3Url(Img3Url);
		return updateImg(rs);
	}

	/**
	 * 删除地质风险划分阻段划分数据
	 * 
	 * @param riskId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long riskId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskId", riskId);
		int r = riskSegmentationDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 插入地质风险划分阻段划分数据
	 */
	@Override
	public Long insertObj(MetroIntervalRiskSegmentation riskSegmentation) {
		int r = riskSegmentationDao.insertObj(riskSegmentation);
		if (r > 0) {
			return riskSegmentation.getId();
		}
		return null;
	}

	@Override
	public MetroIntervalRiskSegmentation findObjById(Long riskId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskId", riskId);
		return riskSegmentationDao.findObjById(params);
	}

	/**
	 * 导入excel数据
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean importExcelData(String intervalId, String leftOrRight, MultipartFile file) throws Exception {
		// 读取excel文件内容
		InputStream instream = file.getInputStream();
		Workbook readwb = Workbook.getWorkbook(instream);
		Sheet sheet = readwb.getSheet(0);
		List<String[]> dataList = getSheetData(sheet);
		if (CommonUtils.isNotNull(dataList)) {
			for (String[] value : dataList) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("intervalId", Long.parseLong(intervalId));
				params.put("leftOrRight", leftOrRight);
				params.put("geologic_no", (value[0]));
				MetroIntervalRiskSegmentation result = riskSegmentationDao.findUniqueriskSegmentation(params);
				if (result == null) { // 新增数据
					MetroIntervalRiskSegmentation save = new MetroIntervalRiskSegmentation();
					save.setIntervalId(Long.parseLong(intervalId));
					save.setLeftOrRight(leftOrRight);
					save.setGeologicNo(value[0]);
					save.setGeologicDescription(value[1]);
					save.setHydrogeololgy(value[2]);
					save.setRiskNo(value[3]);
					save.setRiskPoint(value[4]);
					save.setRiskPhoto(value[5]);
					save.setRiskStartRing(Integer.parseInt(value[6]));
					save.setRiskEndRing(Integer.parseInt(value[7]));
					save.setStartMileage(Float.parseFloat(value[8]));
					save.setEndMileage(Float.parseFloat(value[9]));
					save.setEarlyWarningRing(Integer.parseInt(value[10]));
					save.setWarningLevel(value[11]);
					save.setRiskDescription(value[12]);
					Long j = insertObj(save);
					if (j == null) { // 新增失败
						logger.error("新增数据失败，区间：" + intervalId + "左右线：" + leftOrRight + "地质组段编号：" + value[0]);
						throw new ZookeTransactionException("新增数据失败");
					}
				} else { // 更新数据
					result.setGeologicNo(value[0]);
					result.setGeologicDescription(value[1]);
					result.setHydrogeololgy(value[2]);
					result.setRiskNo(value[3]);
					result.setRiskPoint(value[4]);
					result.setRiskPhoto(value[5]);
					result.setRiskStartRing(Integer.parseInt(value[6]));
					result.setRiskEndRing(Integer.parseInt(value[7]));
					result.setStartMileage(Float.parseFloat(value[8]));
					result.setEndMileage(Float.parseFloat(value[9]));
					result.setEarlyWarningRing(Integer.parseInt(value[10]));
					result.setWarningLevel(value[11]);
					result.setRiskDescription(value[12]);
					boolean j = updateObj(result);
					if (!j) { // 更新失败
						logger.error("更新数据失败，id：" + result.getId());
						throw new ZookeTransactionException("更新数据失败");
					}
				}
			}
		} else {
			logger.error("解析导入的文件数据为空");
		}
		return true;
	}

	/**
	 * 通过excel的sheet对象获取数据
	 *
	 * @param s
	 * @return
	 */
	private List<String[]> getSheetData(Sheet s) {
		int column = s.getColumns();
		int row = s.getRows();
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 1; i < row; i++) {
			String[] value = new String[column];
			for (int j = 0; j < column; j++) {
				value[j] = s.getCell(j, i).getContents();
			}
			result.add(value);
		}
		if (CommonUtils.isNotNull(result)) {
			return result;
		}
		return null;
	}

	/**
	 * 获取所有监测预警阀值设置信息的路线左右线列表，用于historian接口查询该路线对应左右线的环数
	 * 
	 * @return
	 */
	@Override
	public List<IntervalRiskSegmentationVO> findQueryVOListForAll() {
		return riskSegmentationDao.findQueryVOListForAll();
	}

	/**
	 * 获取某线路左右线的地质风险划分阻段划分信息列表，用于historian接口查询符合环数要求的数据
	 * 
	 * @param intervalId  线路区间id
	 * @param leftOrRight 左右线标识
	 * @return
	 */
	@Override
	public List<IntervalRiskSegmentationVO> findQueryVOList(Long intervalId, String leftOrRight) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("intervalId", intervalId);
		paras.put("leftOrRight", leftOrRight);
		return riskSegmentationDao.findQueryVOList(paras);
	}

	public void riskSegmentationTimerService() {
		// 获取所有有效的区间地质风险的线路区间左右线列表
		List<IntervalRiskSegmentationVO> allRisk = findQueryVOListForAll();
		for (IntervalRiskSegmentationVO riskVo : allRisk) {
			logger.info(riskVo.getLineNo() + "号线" + riskVo.getIntervalMark() + "标"+ "，左右线：" + riskVo.getLeftOrRight().toUpperCase() + "线，"  + " start*****");
			List<String> keys = new ArrayList<String>();
			String ringNumkey = IhistorianUtil.getKey(riskVo.getLineNo(), riskVo.getIntervalMark(),
					riskVo.getLeftOrRight(), Constants.PARAM_A0001);
			keys.add(ringNumkey);
			// 获取该路线左右线具体的环数
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200) {
				Map<String, Object> result = ir.getResult();
				Integer ringNum = (Integer) result.get(ringNumkey);
				// 获取该区间左右线对应地质风险划分组段划分信息
				List<IntervalRiskSegmentationVO> intervalRiskList = findQueryVOList(riskVo.getIntervalId(),
						riskVo.getLeftOrRight());
				for (IntervalRiskSegmentationVO intervalRisk : intervalRiskList) {
					if (intervalRisk.getRiskEndRing() < intervalRisk.getRiskStartRing()) {
						logger.warn("风险点配置异常，结束环小于开始环！风险编号：" + intervalRisk.getRiskNo());
						continue;
					}
					// 判断当前环属于该风险配置的什么位置（前中后）
					// 当前处于风险位置(风险配置中段)
					if (intervalRisk.getRiskEndRing() >= ringNum && intervalRisk.getRiskStartRing() <= ringNum) {
						innerRisk(intervalRisk, ringNum);
					}
					// 当前处于风险位置前(风险配置前段)
					if (intervalRisk.getRiskStartRing() > ringNum
							&& (intervalRisk.getRiskStartRing()-intervalRisk.getEarlyWarningRing()) <= ringNum) {
						beforeRisk(intervalRisk, ringNum);
					}
					// 已离开风险位置(风险配置后段)
					if (intervalRisk.getRiskEndRing() < ringNum) {
						afterRisk(intervalRisk, ringNum);
					}
				}
			}
			logger.info(riskVo.getLineNo() + "号线" + riskVo.getIntervalMark() + "标"+ "，左右线：" + riskVo.getLeftOrRight().toUpperCase() + "线，"  + " end*****");
		}
	}

	/**
	 * 风险中处理
	 * 
	 * @param intervalRisk
	 *            风险点配置信息
	 * @param currentRing
	 *            当前环号
	 */
	private void innerRisk(IntervalRiskSegmentationVO intervalRisk, Integer currentRing) {
		// 开始到结束环之间按10环分段
		for (int i = intervalRisk.getRiskStartRing(); i < intervalRisk.getRiskEndRing();) {
			// 判断当前处于哪一段
			if (currentRing >= i && currentRing < i + WARNING_NUM) {
				// 判断当前段内是否已报过预警
				// 查询段内是否已有预警记录, 这段内没有发送过预警才发送预警信息并插入预警记录
				List<MetroIntervalRiskSegmentationRec> recList = riskSegmentationRecService
						.findRiskSegmentationRec(intervalRisk.getId(), i, i + WARNING_NUM);
				if (recList == null || recList.size() < 1) {
					// 发送预警短信
					sendRiskSegmentationMessage(intervalRisk, currentRing, "inner");
					// 插入预警记录
					riskSegmentationRecService.insertSegmentationRec(intervalRisk.getIntervalId(),
							intervalRisk.getLeftOrRight(), intervalRisk.getId(), currentRing);
					break;
				}
			}
			i = i + WARNING_NUM;
		}
	}

	/**
	 * 风险点前
	 * 
	 * @param intervalRisk
	 *            风险点配置信息
	 * @param currentRing
	 *            当前环号
	 */
	private void beforeRisk(IntervalRiskSegmentationVO intervalRisk, Integer currentRing) {
		// 如果没有配置有提前多少环预警或者提前报警环数大于风险点设置的开始环号则不处理
		if (intervalRisk.getEarlyWarningRing() <= 0
				|| intervalRisk.getEarlyWarningRing() >= intervalRisk.getRiskStartRing()
				|| currentRing < (intervalRisk.getRiskStartRing()-intervalRisk.getEarlyWarningRing()) ) {
			return;
		}
		// 判断提前环号数对应环号到风险是设置开始环号之间是否已经有预警记录，有则不再处理，没有则发送预警短信并插入预警记录
		List<MetroIntervalRiskSegmentationRec> recList = riskSegmentationRecService.findRiskSegmentationRec(
				intervalRisk.getId(), intervalRisk.getRiskStartRing() - intervalRisk.getEarlyWarningRing(),
				intervalRisk.getRiskStartRing());
		if (recList == null || recList.size() < 1) {
			// 发送预警短信
			sendRiskSegmentationMessage(intervalRisk, currentRing, "before");
			// 插入预警记录
			riskSegmentationRecService.insertSegmentationRec(intervalRisk.getIntervalId(),
					intervalRisk.getLeftOrRight(), intervalRisk.getId(), currentRing);
		}
	}

	/**
	 * 风险点后
	 * 
	 * @param intervalRisk
	 *            风险点配置信息
	 * @param currentRing
	 *            当前环号
	 */
	private void afterRisk(IntervalRiskSegmentationVO intervalRisk, Integer currentRing) {
		if (intervalRisk.getRiskEndRing() >= currentRing) {
			return;
		}
		// 判断是否已经有预警记录，没有则发送预警短信并插入预警记录
		List<MetroIntervalRiskSegmentationRec> recList = riskSegmentationRecService.findRiskSegmentationRec(
				intervalRisk.getId(), intervalRisk.getRiskEndRing(), intervalRisk.getRiskEndRing() + 10);// 往后查10环都没有当做没有记录处理
		if (recList == null || recList.size() < 1) {
			// 发送预警短信
			sendRiskSegmentationMessage(intervalRisk, currentRing, "after");
			// 插入预警记录
			riskSegmentationRecService.insertSegmentationRec(intervalRisk.getIntervalId(),
					intervalRisk.getLeftOrRight(), intervalRisk.getId(), currentRing);
		}
		// 更新风险设置表is_used为0，避免定时任务重复进来
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isUsed", "0");
		params.put("riskId", intervalRisk.getId());
		riskSegmentationDao.updateObj(params);
	}

	/**
	 * 发送风险点预警短信
	 * 
	 * @param riskVo
	 *            风险点配置信息
	 * @param ringNum
	 *            当前环
	 * @param type
	 */
	private void sendRiskSegmentationMessage(IntervalRiskSegmentationVO riskVo, Integer ringNum, String type) {
		// 根据风险点配置ID查询，风险点相关人员联系方式。
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskSegmentationId", riskVo.getId());
		List<MetroUserRiskSegmentationRel> riskUserList = riskSegmentationRelDao.findObjsByRiskId(params);
		// 根据风险设置相关人员信息拼装相关人员的手机号码；多个用英文逗号分隔
		StringBuilder to = new StringBuilder();
		for (MetroUserRiskSegmentationRel riskUser:riskUserList) {
			if(riskUser == null || !CommonUtils.isNotNull(riskUser.getPhoneNo())){
				continue;
			}
			if(CommonUtils.isNotNull(to.toString())){
				to.append(",");
			}
			to.append(riskUser.getPhoneNo());
		}
		// 根据当前环所在段选择不同的短信模板
		String templateId = "241764";
		if ("inner".equals(type)) {
			templateId = "241762";
		} else if ("after".equals(type)) {
			templateId = "241763";
		}
		//拼装发送短信需要参数
		List<String> smsParamList = new ArrayList<String>();
		// 线路
		smsParamList.add(riskVo.getLineName());
		// 区间
		smsParamList.add(riskVo.getIntervalName().replace("区间", ""));
		// 左右线
		smsParamList.add("l".equals(riskVo.getLeftOrRight())?"左":"右");
		// 当前环
		smsParamList.add(String.valueOf(ringNum));
		// 风险点
		smsParamList.add(riskVo.getRiskPoint());
		// 开始环
		smsParamList.add(String.valueOf(riskVo.getRiskStartRing()));
		// 结束环
		smsParamList.add(String.valueOf(riskVo.getRiskEndRing()));
		if("before".equals(type)){
			// 距离开始环相距多少环
			smsParamList.add(String.valueOf(riskVo.getRiskStartRing()-ringNum));
		}
		String[] paramArr = smsParamList.toArray(new String[smsParamList.size()]);
		SMSUtil.sendTemlateSMS(to.toString(), templateId, paramArr);
	}

}
