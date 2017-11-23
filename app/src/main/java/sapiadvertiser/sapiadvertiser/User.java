package sapiadvertiser.sapiadvertiser;

/**
 * Created by Elod on 11/7/2017.
 */

public class User {
    private String name;
    private String password;
    private String email;
    private Double telefon;

    public User() {
    }

    public User(String name, String password, String email, Double telefon) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.telefon = telefon;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getTelefon() {
        return telefon;
    }

    public void setTelefon(Double telefon) {
        this.telefon = telefon;
    }
}
