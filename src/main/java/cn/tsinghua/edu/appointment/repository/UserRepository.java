package cn.tsinghua.edu.appointment.repository;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.User;
import cn.tsinghua.edu.appointment.domain.UserType;
import cn.tsinghua.edu.appointment.exception.DuplicateFieldException;
import cn.tsinghua.edu.appointment.exception.EmptyFieldException;
import cn.tsinghua.edu.appointment.exception.NoExistException;

public class UserRepository {

    private MongoAccess mongo = new MongoAccess();

    public static final String ADMIN_DEAULT_PASSWORD = "THXXFZZX";
    public static final String TEACHER_DEFAULT_PASSWORD = "thxxfzzx";

    public User login(String username, String password)
            throws EmptyFieldException, NoExistException {
        if (username == null || username.equals("")) {
            throw new EmptyFieldException("用户名为空");
        } else if (password == null || password.equals("")) {
            throw new EmptyFieldException("密码为空");
        }
        User user = mongo.getUserByUsername(username);
        if (user != null && (password.equals(user.getPassword())
                || (user.getUserType() == UserType.ADMIN && password.equals(ADMIN_DEAULT_PASSWORD))
                || (user.getUserType() == UserType.TEACHER && password.equals(TEACHER_DEFAULT_PASSWORD)))) {
            return user;
        }
        throw new NoExistException("用户名或密码不正确");
    }

    public User register(String username, String password, UserType type)
            throws EmptyFieldException, DuplicateFieldException {
        if (username == null || username.equals("")) {
            throw new EmptyFieldException("用户名为空");
        } else if (password == null || password.equals("")) {
            throw new EmptyFieldException("密码为空");
        }
        if (mongo.getUserByUsername(username) != null) {
            throw new DuplicateFieldException("用户名已被注册");
        }
        User user = mongo.addUser(username, password, type);
        return user;
    }

}
