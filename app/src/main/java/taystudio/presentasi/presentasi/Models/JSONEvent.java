package taystudio.presentasi.presentasi.Models;

public class JSONEvent {
    private  Event[] list;

    public JSONEvent(Event[] list) {
        this.list = list;
    }

    public Event[] getList() {
        return list;
    }

    public void setList(Event[] list) {
        this.list = list;
    }
}
