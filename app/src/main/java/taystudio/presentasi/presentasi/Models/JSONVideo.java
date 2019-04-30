package taystudio.presentasi.presentasi.Models;

public class JSONVideo {
    private  Video[] list;

    public JSONVideo(Video[] list) {
        this.list = list;
    }

    public Video[] getList() {
        return list;
    }

    public void setList(Video[] list) {
        this.list = list;
    }
}
