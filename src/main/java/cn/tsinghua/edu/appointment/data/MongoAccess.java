package cn.tsinghua.edu.appointment.data;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.tsinghua.edu.appointment.config.SpringMongoConfig;
import cn.tsinghua.edu.appointment.domain.Appointment;
import cn.tsinghua.edu.appointment.domain.User;

public class MongoAccess {

	public final static ApplicationContext CTX = new AnnotationConfigApplicationContext(
			SpringMongoConfig.class);
	public final static MongoOperations MONGO = (MongoOperations) CTX
			.getBean("mongoTemplate");
	
	/*
	 * User
	 */
	
	public User addUser(String _username, String _password) {
		User newUser = new User(_username, _password);
		MONGO.save(newUser);
		return newUser;
	}
	
	public User saveUser(User _user) {
		MONGO.save(_user);
		return _user;
	}
	
	public User getUserById(String _userId) {
		return MongoAccess.MONGO.findOne(
				new Query(Criteria.where("id").is(_userId)), User.class);
	}

	public User getUserByUsername(String _username) {
		return MongoAccess.MONGO.findOne(new Query(Criteria.where("username")
				.is(_username)), User.class);
	}
	
	/*
	 * Appointment
	 */

	public Appointment saveApp(Appointment _app) {
		MONGO.save(_app);
		return _app;
	}

	public Appointment getAppById(String _appId) {
		return MONGO.findOne(new Query(Criteria.where("id").is(_appId)),
				Appointment.class);
	}
	
	public List<Appointment> getAppsBetweenDates(Date from, Date to) {
		Query query = new Query(Criteria.where("startDate").gte(from).lte(to));
		query.with(new Sort(Direction.ASC, "startDate"));
		return MONGO.find(query, Appointment.class);
	}

}
