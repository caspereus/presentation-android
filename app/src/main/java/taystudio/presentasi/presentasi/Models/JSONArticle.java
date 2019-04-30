package taystudio.presentasi.presentasi.Models;

public class JSONArticle {
    private  Article[] list;

    public JSONArticle(Article[] list) {
        this.list = list;
    }

    public Article[] getList() {
        return list;
    }

    public void setList(Article[] list) {
        this.list = list;
    }
}
