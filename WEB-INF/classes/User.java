

public class User {
    
    private String login;
    private String password;
    private String username;
    private int id;

    public User(String login,String password,String username,int id){
        System.out.println(username);
        this.login=login;
        this.password=password;
        this.username=username;
        this.id=id;
    }

    public void update(String login,String password,String username){
        this.login=login;
        this.password=password;
        this.username=username;
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
