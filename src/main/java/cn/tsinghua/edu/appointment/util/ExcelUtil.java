package cn.tsinghua.edu.appointment.util;

import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.exception.BasicException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelUtil {

    // public static final String DEFAULT_EXPORT_FOLDER =
    // "D:\\Workspace\\apache-tomcat-8.0.21-appointment\\webapps\\appointment\\export\\";
    public static final String DEFAULT_EXPORT_FOLDER = "/mnt/appointment/apache-tomcat-8.0.26/webapps/appointment/export/";
    public static final String EXPORT_PREFIX = "export/";
    public static final String EXCEL_SUFFIX = ".xlsx";
    public static final String DEFAULT_EXCEL = DEFAULT_EXPORT_FOLDER
            + "export_template.xlsx";

    public static void exportToExcel(List<Appointment> appointments,
                                     String filename) throws BasicException {
        String filepath = DEFAULT_EXPORT_FOLDER + filename;
        try {
            InputStream input = new FileInputStream(DEFAULT_EXCEL);
            Workbook wb = WorkbookFactory.create(input);
            Sheet sheet = wb.getSheetAt(0);
            Cell cell;

            for (int i = 0; i < appointments.size(); ++i) {
                Appointment app = appointments.get(i);
                Row row = sheet.createRow(i + 1);

                // 学生申请表
                cell = row.createCell(0);
                cell.setCellValue(app.getStudentInfo().getName());
                cell = row.createCell(1);
                cell.setCellValue(app.getStudentInfo().getGender());
                cell = row.createCell(2);
                cell.setCellValue(app.getStudentInfo().getStudentId());
                cell = row.createCell(4);
                cell.setCellValue(app.getStudentInfo().getSchool());
                cell = row.createCell(5);
                cell.setCellValue(app.getStudentInfo().getHometown());
                cell = row.createCell(6);
                cell.setCellValue(app.getStudentInfo().getMobile());
                cell = row.createCell(7);
                cell.setCellValue(app.getStudentInfo().getEmail());
                cell = row.createCell(9);
                cell.setCellValue(app.getStudentInfo().getProblem());

                // 预约信息
                cell = row.createCell(10);
                cell.setCellValue(app.getTeacher());
                cell = row.createCell(11);
                cell.setCellValue(DateUtil.getYYMMDD(app.getStartTime()));

                // 咨询师反馈表
                cell = row.createCell(14);
                cell.setCellValue(app.getTeacherFeedback().getProblem());
                cell = row.createCell(15);
                cell.setCellValue(app.getTeacherFeedback().getSolution());

                // 学生反馈表
                cell = row.createCell(17);
                cell.setCellValue(app.getStudentFeedback().getScore());
                cell = row.createCell(18);
                cell.setCellValue(app.getStudentFeedback().getFeedback());
                if (!app.getStudentFeedback().getChoices().isEmpty()) {
                    for (int j = 0; j < 12; ++j) {
                        cell = row.createCell(j + 19);
                        char choice = app.getStudentFeedback().getChoices()
                                .charAt(j);
                        switch (choice) {
                            case 'A':
                                cell.setCellValue("非常同意");
                                break;
                            case 'B':
                                cell.setCellValue("一般");
                                break;
                            case 'C':
                                cell.setCellValue("不同意");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

            FileOutputStream output = new FileOutputStream(filepath);
            wb.write(output);
            input.close();
            output.close();
        } catch (Exception e) {
            throw new BasicException("导出错误");
        }
    }
}
