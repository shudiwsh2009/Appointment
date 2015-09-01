package cn.tsinghua.edu.appointment.sms;

import cn.tsinghua.edu.appointment.config.EnvConfig;
import cn.tsinghua.edu.appointment.exception.ActionRejectException;
import cn.tsinghua.edu.appointment.exception.BasicException;
import cn.tsinghua.edu.appointment.exception.FormatException;
import cn.tsinghua.edu.appointment.util.FormatUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class SMS {

    public final static String UID = "";
    public final static String KEY = "";

    public static void sendSMS(String mobile, String content) throws FormatException, ActionRejectException {
        if (EnvConfig.ENVIRONMENT.equals("DEV")) {
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
