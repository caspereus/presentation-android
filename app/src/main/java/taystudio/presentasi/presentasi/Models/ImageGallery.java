package taystudio.presentasi.presentasi.Models;

public class ImageGallery {
    private String image_name,created_at,gallery_id,id,updated_at;

    public ImageGallery(String image_name, String created_at, String gallery_id, String id, String updated_at) {
        this.image_name = image_name;
        this.created_at = created_at;
        this.gallery_id = gallery_id;
        this.id = id;
        this.updated_at = updated_at;
    }

    public String getImage_name() {
        return image_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getGallery_id() {
        return gallery_id;
    }

    public String getId() {
        return id;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
