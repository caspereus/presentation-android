package taystudio.presentasi.presentasi.Models;

public class Like {
    String post_id,user_id,type,message,status;

    public Like(String post_id, String user_id, String type, String message, String status) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.type = type;
        this.message = message;
        this.status = status;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
