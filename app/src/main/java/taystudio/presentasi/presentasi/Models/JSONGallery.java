package taystudio.presentasi.presentasi.Models;

public class JSONGallery {
    private Gallery[] list;

    public JSONGallery(Gallery[] list) {
        this.list = list;
    }

    public Gallery[] getList() {
        return list;
    }
}
