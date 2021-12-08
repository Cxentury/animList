package userPackage;
public class User {
    
    private String login;
    private String username;
    private int id;
    private String password;

    public User(String login,String username,String password,int id){
        this.login=login;
        this.username=username;
        this.password=password;
        this.id=id;
    }

    public void update(String login,String username,String password){
        this.login=login;
        this.username=username;
        this.password=password;
    }

    public String getLogin(){
        return login;
    }

    public String getUserName(){
        return this.username;
    }

    public int getId(){
        return id;
    }
}
