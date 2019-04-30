package taystudio.presentasi.presentasi.Models;

public class Article {
    String id,title,description,full_content,cover_image,category_id,category,created_at,message,views;

    public Article(String id, String title, String description, String full_content, String cover_image, String category_id, String category, String created_at, String message, String views) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.full_content = full_content;
        this.cover_image = cover_image;
        this.category_id = category_id;
        this.category = category;
        this.created_at = created_at;
        this.message = message;
        this.views = views;
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

    public String getFull_content() {
        return full_content;
    }

    public String getCover_image() {
        return cover_image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getMessage() {
        return message;
    }

    public String getViews() {
        return views;
    }
}
