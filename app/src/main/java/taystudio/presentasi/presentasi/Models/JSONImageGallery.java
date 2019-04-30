package taystudio.presentasi.presentasi.Models;

public class JSONImageGallery {
    private ImageGallery[] list;

    public JSONImageGallery(ImageGallery[] list) {
        this.list = list;
    }

    public ImageGallery[] getList() {
        return list;
    }
}
