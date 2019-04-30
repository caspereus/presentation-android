package taystudio.presentasi.presentasi.Models;

public class Gallery {
    private String id,gallery_title,message,created_at,updated_at,image_thumbnail,views;

    public Gallery(String id, String gallery_title, String message, String created_at, String updated_at, String image_thumbnail, String views) {
        this.id = id;
        this.gallery_title = gallery_title;
        this.message = message;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image_thumbnail = image_thumbnail;
        this.views = views;
    }

    public String getId() {
        return id;
    }

    public String getGallery_title() {
        return gallery_title;
    }

    public String getMessage() {
        return message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getImage_thumbnail() {
        return image_thumbnail;
    }

    public String getViews() {
        return views;
    }
}
