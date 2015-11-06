package cn.edu.tsinghua.appointment.sms;

import cn.edu.tsinghua.appointment.domain.Appointment;
import cn.edu.tsinghua.appointment.exception.FormatException;
import cn.edu.tsinghua.appointment.exception.ActionRejectException;
import cn.edu.tsinghua.appointment.exception.BasicException;
import cn.edu.tsinghua.appointment.util.DateUtil;
import cn.edu.tsinghua.appointment.util.FormatUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.time.format.TextStyle;
import java.util.Locale;

public class SMS {

    public final static String SUCCESS_STUDENT = "%s你好，你已成功预约星期%s" +
            "（%s月%s日）%s-%s咨询，地点：紫荆C楼407室。电话：62792453。";
    public final static String SUCCESS_TEACHER = "%s您好，%s已预约您星期%s（" +
            "%s月%s日）%s-%s咨询，地点：紫荆C楼407室。电话：62792453。";
    public final static String REMINDER_STUDENT = "温馨提示：%s你好，你已成功" +
            "预约明天%s-%s咨询，地点：紫荆C楼407室。电话：62792453。";
    public final static String REMINDER_TEACHER = "温馨提示：%s您好，%s已预约" +
            "您明天%s-%s咨询，地点：紫荆C楼407室。电话：62792453。";
    public final static String FEEDBACK_STUDENT = "温馨提示：%s你好，感谢使用" +
            "我们的一对一咨询服务，请再次登录乐学预约界面，为咨询师反馈评分，帮助" +
            "我们成长。";

    public static void sendSuccessSMS(Appointment app) throws FormatException {
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
        try {
            sendSMS(app.getStudentInfo().getMobile(), studentSMS);
        } catch (BasicException e) {
            System.err.println(e.getInfo());
        } finally {
            try {
                sendSMS(app.getTeacherMobile(), teacherSMS);
            } catch (BasicException e) {
                System.err.println(e.getInfo());
            }
        }
    }

    public static void sendReminderSMS(Appointment app) {
        try {
            String studentSMS = String.format(Locale.CHINA, REMINDER_STUDENT,
                    app.getStudentInfo().getName(),
                    DateUtil.getHHmm(app.getStartTime()),
                    DateUtil.getHHmm(app.getEndTime()));
            sendSMS(app.getStudentInfo().getMobile(), studentSMS);
        } catch (BasicException e) {
            System.err.println(e.getInfo());
        } finally {
            try {
                String teacherSMS = String.format(Locale.CHINA, REMINDER_TEACHER,
                        app.getTeacher(),
                        app.getStudentInfo().getName(),
                        DateUtil.getHHmm(app.getStartTime()),
                        DateUtil.getHHmm(app.getEndTime()));
                sendSMS(app.getTeacherMobile(), teacherSMS);
            } catch (BasicException e) {
                System.err.println(e.getInfo());
            }
        }
    }

    public static void sendFeedbackSMS(Appointment app) {
        try {
            String studentSMS = String.format(Locale.CHINA, FEEDBACK_STUDENT,
                    app.getStudentInfo().getName());
            sendSMS(app.getStudentInfo().getMobile(), studentSMS);
        } catch (BasicException e) {
            System.err.println(e.getInfo());
        }
    }

    private static void sendSMS(String mobile, String content) throws FormatException, ActionRejectException {
        if (!FormatUtil.isMobile(mobile)) {
            throw new FormatException("手机号不正确");
        }
        String appEnv = System.getenv("APPOINTMENT_ENV");
        String uid = System.getenv("APPOINTMENT_UID");
        String key = System.getenv("APPOINTMENT_KEY");
        if (appEnv == null || !appEnv.equals("ONLINE")
                || uid == null || uid.equals("")
                || key == null || key.equals("")) {
            System.out.printf("Send SMS:\"%s\" to %s.\r\n", content, mobile);
            return;
        }
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] data = {new NameValuePair("Uid", uid),
                new NameValuePair("Key", key),
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
