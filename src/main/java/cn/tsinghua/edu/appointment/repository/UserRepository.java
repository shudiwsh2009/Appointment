package cn.tsinghua.edu.appointment.repository;

import cn.tsinghua.edu.appointment.data.MongoAccess;
import cn.tsinghua.edu.appointment.domain.User;
import cn.tsinghua.edu.appointment.exception.DuplicateFieldException;
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

	public User register(String username, String password)
			throws EmptyFieldException, DuplicateFieldException {
		if (username == null || username.equals("")) {
			throw new EmptyFieldException("用户名为空");
		} else if (password == null || password.equals("")) {
			throw new EmptyFieldException("密码为空");
		}
		if (mongo.getUserByUsername(username) != null) {
			throw new DuplicateFieldException("用户名已被注册");
		}
		User user = mongo.addUser(username, password);
		return user;
	}

}
