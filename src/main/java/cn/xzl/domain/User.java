package cn.xzl.domain;

/**
 * 用户类
 *
 * @author xzl
 * @create 2018-01-23 16:11
 **/
public class User {

    private Integer id;        //id

    private String name;       //姓名

    private String password;   //密码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
