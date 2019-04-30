package taystudio.presentasi.presentasi.Models;

public class Video {
    String id,title,description,url_link,created_at,updated_at,views,message;

    public Video(String id, String title, String description, String url_link, String created_at, String updated_at, String views, String message) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url_link = url_link;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getUrl_link() {
        return url_link;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getViews() {
        return views;
    }

    public String getMessage() {
        return message;
    }
}
