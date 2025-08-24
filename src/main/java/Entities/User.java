package Entities;

public class User {
    private int id,phone;
    private String username,email,role,password;
    //getters
    public int getId(){return id;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public int getPhone(){return phone;}
    public String getRole(){return role;}
    public String getPassword(){return password;}
    //setters
    public void setId(int id){this.id=id;}
    public void setPhone(int phone){this.phone=phone;}
    public void setUsername(String username){this.username=username;}
    public void setEmail(String email){this.email=email;}
    public void setRole(String role){this.role=role;}
    public void setPassword(String password){this.password=password;}
    //Constructor
    public User(int id,int phone,String username , String email,String role,String password){
        this.id=id;
        this.phone=phone;
        this.username=username;
        this.email=email;
        this.role=role;
        this.password=password;
    }

}
