package com.szgentech.metro.base.utils;

/**
 * Created by wangcan on 2017/2/13.
 */
public class MathUtil {

	/**
	 * 计算水平偏差值
	 * 
	 * @param x0
	 * @param y0
	 * @param startX 起始里程X坐标
	 * @param startY 起始里程Y坐标
	 * @param endX 结束里程X坐标
	 * @param endY 结束里程Y坐标
	 * @return 水平偏差
	 */
	public static double getHdeviation(double x0, double y0, double startX, double startY, double endX, double endY) {
		double deltaX = endX - startX;
		double deltaY = endY - startY;
		double disV = deltaX * (y0 - startY) - deltaY * (x0 - startX);
		double hypot = Math.hypot(deltaX, deltaY);
		if (hypot < Double.MIN_NORMAL) {
			return 0D;
		}
		return disV / hypot * 1000.0D;
	}

	/**
	 * 
	 * @param z0
	 * @param z1
	 * @param z2
	 * @param mileage0
	 * @param mileage1
	 * @param mileage2
	 * @return
	 */
	public static double getVdeviation(double z0, double z1, double z2, double mileage0, double mileage1,
			double mileage2) {
		double deltaZ;
		double min = mileage0 - mileage1;
		double max = mileage2 - mileage0;
		if (min < max) {
			deltaZ = z0 - z1;
		} else {
			deltaZ = z0 - z2;
		}

		return deltaZ * 1000.0D;
	}

	/**
	 * 
	 * @param category
	 * @param status
	 * @return
	 */
	public static Boolean checkStatus(Integer category, Integer status) {

		if (category == null) {
			return true;
		}
		if (status > 2 && status <= 3) {
			if (category == 2 || category == 3 || category == 6 || category == 7) {
				return true;
			}
		} else if (status > 3 && status <= 4) {
			if (category == 1 || category == 3 || category == 5 || category == 7) {
				return true;
			}
		} else {
			if (category == 4 || category == 5 || category == 6 || category == 7) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param MapX
	 * @param MapY
	 * @param MapZ
	 * @return
	 */
	public static Boolean isZero(String MapX, String MapY, String MapZ) {
		if ((MapX.equals("0")) && (MapY.equals("0")) && (MapZ.equals("0"))) {
			return true;
		}
		return false;
	}

}