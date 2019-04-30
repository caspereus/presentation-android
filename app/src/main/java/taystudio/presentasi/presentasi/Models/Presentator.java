package taystudio.presentasi.presentasi.Models;

public class Presentator {
    String id,name,email,phone,company,position,address,facebook,instagram,photo,website,about;

    public Presentator(String id, String name, String email, String phone, String company, String position, String address, String facebook, String instagram, String photo, String website, String about) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.position = position;
        this.address = address;
        this.facebook = facebook;
        this.instagram = instagram;
        this.photo = photo;
        this.website = website;
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    public String getAddress() {
        return address;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getPhoto() {
        return photo;
    }

    public String getWebsite() {
        return website;
    }

    public String getAbout() {
        return about;
    }
}
