package cn.tsinghua.edu.appointment.repository;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.User;
import cn.tsinghua.edu.appointment.exception.EmptyFieldException;
import cn.tsinghua.edu.appointment.exception.NoExistException;

public class UserRepository {

	private MongoAccess mongo = new MongoAccess();

	public User login(String username, String password)
			throws EmptyFieldException, NoExistException {
		if (username == null || username.equals("")) {
			throw new EmptyFieldException("用户名为空");
		} else if (password == null || password.equals("")) {
			throw new EmptyFieldException("密码为空");
		}
		User user = mongo.getUserByUsername(username);
		if (user == null || !user.getPassword().equals(password)) {
			throw new NoExistException("用户名或密码不正确");
		}
		return user;
	}

}
