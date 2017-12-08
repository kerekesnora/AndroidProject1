package sapiadvertiser.sapiadvertiser;

/**
 * Created by Kerekes Nora on 2017.12.07..
 */

public class UserInformation {
    private String userName;
    private String emailAdd;

    public UserInformation() {
    }

    public UserInformation(String userName, String emailAdd) {
        this.userName = userName;
        this.emailAdd = emailAdd;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailAdd() {
        return emailAdd;
    }
}
