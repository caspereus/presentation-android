package taystudio.presentasi.presentasi.Models;

public class EventFile {
    String id,event_id,file_name,downloaded_count,file_password,file_size;

    public EventFile(String id, String event_id, String file_name, String downloaded_count, String file_password, String file_size) {
        this.id = id;
        this.event_id = event_id;
        this.file_name = file_name;
        this.downloaded_count = downloaded_count;
        this.file_password = file_password;
        this.file_size = file_size;
    }

    public String getId() {
        return id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getDownloaded_count() {
        return downloaded_count;
    }

    public String getFile_password() {
        return file_password;
    }

    public String getFile_size() {
        return file_size;
    }
}
