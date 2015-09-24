package cn.edu.tsinghua.appointment.sms;

import cn.edu.tsinghua.appointment.domain.Appointment;
import cn.edu.tsinghua.appointment.data.MongoAccess;
import cn.edu.tsinghua.appointment.domain.Status;
import cn.edu.tsinghua.appointment.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Created by Shudi on 2015/9/5.
 */
public class Reminder {

    private MongoAccess mongo = new MongoAccess();

    public void remind() {
        LocalDate tomorrow = LocalDate.now(ZoneId.of(DateUtil.ZONE_ID)).plusDays(1L);
        LocalDateTime from = LocalDateTime.of(tomorrow, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(tomorrow, LocalTime.MAX);
        List<Appointment> apps = this.mongo.getAppsBetweenDateTimes(from, to);
        apps.forEach((app) -> {
            if (app.getStatus() == Status.APPOINTED) {
                SMS.sendReminderSMS(app);
            }
        });
    }

    public static void main(String[] args) {
        Reminder reminder = new Reminder();
        reminder.remind();
    }
}
