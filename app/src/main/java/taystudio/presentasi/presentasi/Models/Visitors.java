package taystudio.presentasi.presentasi.Models;

public class Visitors {
    private String id,name,email,phone,status,created_at,message;

    public Visitors(String id, String name, String email, String phone, String status, String created_at, String message) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.created_at = created_at;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getMessage() {
        return message;
    }
}
