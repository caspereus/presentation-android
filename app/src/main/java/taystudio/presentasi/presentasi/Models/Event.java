package taystudio.presentasi.presentasi.Models;

public class Event {
    String id,title,description,cover_image,status,created_at,views,message;

    public Event(String id, String title, String description, String cover_image, String status, String created_at, String views, String message) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover_image = cover_image;
        this.status = status;
        this.created_at = created_at;
        this.views = views;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCover_image() {
        return cover_image;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getViews() {
        return views;
    }

    public String getMessage() {
        return message;
    }
}
