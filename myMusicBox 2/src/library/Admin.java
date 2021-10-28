package library;

public class Admin {

	private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private Boolean premium;

    public Admin(int id, String name, String surname, String email, String password, String role, Boolean premium) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.premium = premium;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Boolean getPremium() {
        return premium;
    }
}
