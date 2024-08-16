package MyLibs;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String role;

    public User(String firstName, String lastName, String username, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}