package huentps08729.com.myapplication.model;

public class User {
    private String uid;
    private String UserName;
    private String Phone;
    private String PassWord;
    private String RePassWord;


    public User() {
    }

    public User(String uid, String userName, String phone, String passWord, String rePassWord) {
        this.uid = uid;
        UserName = userName;
        Phone = phone;
        PassWord = passWord;
        RePassWord = rePassWord;
    }

    public User(String userName, String phone, String passWord, String rePassWord) {
        UserName = userName;
        Phone = phone;
        PassWord = passWord;
        RePassWord = rePassWord;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getRePassWord() {
        return RePassWord;
    }

    public void setRePassWord(String rePassWord) {
        RePassWord = rePassWord;
    }
}
