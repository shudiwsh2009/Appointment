package cn.tsinghua.edu.appointment.test;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.util.ExcelUtil;

public class ExcelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExcelUtil.DEFAULT_EXCEL = "D:\\Workspace\\git-workspace\\Appointment\\doc\\咨询预约信息导出模板.xlsx";
		String filepath = "E:\\Downloads\\554b4ca6fa8c2f3ba8658f06.xlsx";
		String appId = "554b4ca6fa8c2f3ba8658f06";
		MongoAccess mongo = new MongoAccess();
		Appointment app = mongo.getAppById(appId);
		ExcelUtil.exportToExcel(app, filepath);
	}

}
