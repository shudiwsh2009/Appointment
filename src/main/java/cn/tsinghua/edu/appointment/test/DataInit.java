package cn.tsinghua.edu.appointment.test;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.User;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.exception.FormatException;

public class DataInit {

    public static void main(String[] args) throws FormatException {
        // TODO Auto-generated method stub
        MongoAccess mongo = new MongoAccess();
//        Appointment app1 = new Appointment(
//                DateUtil.convertDateTime("2015-05-10 08:00"),
//                DateUtil.convertDateTime("2015-05-10 09:00"),
//                "李老师", "111111", "15210561025");
//        Appointment app2 = new Appointment(
//                DateUtil.convertDateTime("2015-05-10 09:00"),
//                DateUtil.convertDateTime("2015-05-10 10:00"),
//                "王老师", "222222", "15101186680");
//        Appointment app3 = new Appointment(
//                DateUtil.convertDateTime("2015-05-10 13:00"),
//                DateUtil.convertDateTime("2015-05-10 14:00"),
//                "李老师", "111111", "15210561025");
//        Appointment app4 = new Appointment(
//                DateUtil.convertDateTime("2015-05-10 14:00"),
//                DateUtil.convertDateTime("2015-05-10 15:00"),
//                "王老师", "222222", "15101186680");
//        mongo.saveApp(app1);
//        mongo.saveApp(app2);
//        mongo.saveApp(app3);
//        mongo.saveApp(app4);
//
        User admin = new User("1", "1", UserType.ADMIN);
        mongo.saveUser(admin);

        System.out.println(mongo.getUserByUsername("1"));
    }

}
