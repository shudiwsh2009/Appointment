package cn.tsinghua.edu.appointment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    @Indexed
    protected String username = "";
    protected String password = "";
    protected UserType userType = UserType.STUDENT;

    public User(String u, String p, UserType type) {
        username = u;
        password = p;
        userType = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

}
