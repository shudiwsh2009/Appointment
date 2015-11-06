package cn.edu.tsinghua.appointment.data;


import cn.edu.tsinghua.appointment.domain.User;
import cn.edu.tsinghua.appointment.domain.UserType;
import cn.edu.tsinghua.appointment.exception.FormatException;

/**
 * Created by shudi on 15/11/1.
 */
public class Test {
    public static void main(String[] args) throws FormatException {
        MongoAccess mongo = new MongoAccess();
//        Reservation reservation = new Reservation(DateUtil.convertDateTime("2015-11-05 12:00"),
//                DateUtil.convertDateTime("2015-11-05 13:00"), "2010013212", "汪抒浩", "15210561025");
//        reservation = mongo.saveReservation(reservation);
//        System.out.println(reservation);

        User admin = new User("1", "1", UserType.ADMIN);
        admin = mongo.saveUser(admin);
        System.out.println(admin);
    }
}
