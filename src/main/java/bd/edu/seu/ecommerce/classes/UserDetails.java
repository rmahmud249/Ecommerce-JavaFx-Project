package bd.edu.seu.ecommerce.classes;
public class UserDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;

    public UserDetails(String firstName, String lastName, String email, String mobile, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getMobile() {
        return mobile;
    }
    public String getPassword() {
        return password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
