package cn.tsinghua.edu.appointment.sms;

import cn.tsinghua.edu.appointment.config.EnvConfig;
import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.exception.ActionRejectException;
import cn.tsinghua.edu.appointment.exception.FormatException;
import cn.tsinghua.edu.appointment.util.DateUtil;
import cn.tsinghua.edu.appointment.util.FormatUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.time.format.TextStyle;
import java.util.Locale;

public class SMS {

    public static String UID = "";
    public static String KEY = "";
    public final static String SUCCESS_STUDENT = "%s同学你好，你已成功预约学习" +
            "发展中心咨询。具体时间星期%s（%s月%s日）%s-%s，地点：老10号楼103室（" +
            "汽车系11号楼南侧，文图北侧，六教往北好汉坡西侧）。请按时赴约。";
    public final static String SUCCESS_TEACHER = "%s咨询师您好，%s同学已预约您" +
            "咨询，时间星期%s（%s月%s日）%s-%s，地点：老10号楼103室（汽车系11号楼" +
            "南侧，文图北侧，六教往北好汉坡西侧）。感谢您拨冗为同学提供辅导。";
    public final static String REMINDER_STUDENT = "【学习发展中心温馨提示】%s同" +
            "学你好，您已预约明天（%s月%s日）%s-%s%s咨询师的一对一咨询，地点：老10" +
            "号楼103室（汽车系11号楼南侧，文图北侧，六教往北好汉坡西侧）。请按时赴约" +
            "，如有特殊情况请致电62792453。";
    public final static String REMINDER_TEACHER = "【学习发展中心温馨提示】%s咨" +
            "询师您好，%s同学已预约您明天（%s月%s日）%s-%s的一对一咨询，地点：老10" +
            "号楼103室（汽车系11号楼南侧，文图北侧，六教往北好汉坡西侧）。感谢您拨冗" +
            "指导学生，如有特殊情况请致电62792453。";

    static {
        String uid = System.getenv("APPOINTMENT_SMS_UID");
        if (uid != null && !uid.equals("")) {
            SMS.UID = uid;
        }
        String key = System.getenv("APPOINTMENT_SMS_KEY");
        if (key != null && !key.equals("")) {
            SMS.KEY = key;
        }
    }

    public static void sendSMS(Appointment app) throws ActionRejectException, FormatException {
        // send sms to student
        String studentSMS = String.format(Locale.CHINA, SUCCESS_STUDENT,
                app.getStudentInfo().getName(),
                app.getStartTime().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.CHINA),
                app.getStartTime().getMonthValue(),
                app.getStartTime().getDayOfMonth(),
                DateUtil.getHHmm(app.getStartTime()),
                DateUtil.getHHmm(app.getEndTime()));
        String teacherSMS = String.format(Locale.CHINA, SUCCESS_TEACHER,
                app.getTeacher(),
                app.getStudentInfo().getName(),
                app.getStartTime().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.CHINA),
                app.getStartTime().getMonthValue(),
                app.getStartTime().getDayOfMonth(),
                DateUtil.getHHmm(app.getStartTime()),
                DateUtil.getHHmm(app.getEndTime()));
        sendSMS(app.getStudentInfo().getMobile(), studentSMS);
        sendSMS(app.getTeacherMobile(), teacherSMS);
    }

    public static void sendSMS(String mobile, String content) throws FormatException, ActionRejectException {
        if ((!System.getenv("APPOINTMENT_ENV").equals("ONLINE")
                && !EnvConfig.ENVIRONMENT.equals("ONLINE"))
                || UID.equals("") || KEY.equals("")) {
            System.out.printf("Send SMS:\"%s\" to %s.\r\n", content, mobile);
            return;
        }
        if (!FormatUtil.isMobile(mobile)) {
            throw new FormatException("手机号不正确");
        }
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] data = {new NameValuePair("Uid", UID),
                new NameValuePair("Key", KEY),
                new NameValuePair("smsMob", mobile),
                new NameValuePair("smsText", content)};
        post.setRequestBody(data);
        try {
            client.executeMethod(post);
        } catch (IOException e) {
            throw new ActionRejectException("连接短信服务器失败");
        }

        Header[] headers = post.getRequestHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode: " + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }
        String result;
        try {
            result = new String(post.getResponseBodyAsString().getBytes("gbk"));
            System.out.println(result);
        } catch (IOException e) {
            throw new ActionRejectException("获取网络回馈失败");
        }
        post.releaseConnection();
    }

}
