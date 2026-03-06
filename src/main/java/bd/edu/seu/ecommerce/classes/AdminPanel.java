package bd.edu.seu.ecommerce.classes;
public class AdminPanel {
    private String emailId;
    private String adminPass;

    public AdminPanel(String emailId, String adminPass) {
        this.emailId = emailId;
        this.adminPass = adminPass;
    }
    public String getEmailId() {
        return emailId;
    }
    public String getAdminPass() {
        return adminPass;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

}
