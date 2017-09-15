package seedu.addressbook.data.person;

import java.util.*;

public class personToAdd {
    private String name = "empty";                          //inputCounter 0 = name
    private String phone = "empty";                         //inputCounter 1 = phone & phone privacy
    private Boolean phonePrivacy = false;
    private String email = "empty";                         //inputCounter 2 = email & email privacy
    private Boolean emailPrivacy = false;

    private String address = "empty";                       //inputCounter 3 = address & address privacy
    private Boolean addressPrivacy = false;
    private HashSet<String> tags = new HashSet<String>();   //inputCounter 4 = Set of tags

    public personToAdd() {
        this.name = "empty";
        this.phone = "empty";
        this.phonePrivacy = false;
        this.email = "empty";
        this.emailPrivacy = false;
        this.address = "empty";
        this.addressPrivacy = false;
        this.tags = new HashSet<String>();
    }

    public String getName() {
        return name;
    }

    public Boolean getPhonePrivacy() {
        return phonePrivacy;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEmailPrivacy() {
        return emailPrivacy;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getAddressPrivacy() {
        return addressPrivacy;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhonePrivacy(Boolean phonePrivacy) {
        this.phonePrivacy = phonePrivacy;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailPrivacy(Boolean emailPrivacy) {
        this.emailPrivacy = emailPrivacy;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddressPrivacy(Boolean addressPrivacy) {
        this.addressPrivacy = addressPrivacy;
    }

    public void addTag(String onetag) {
        tags.add(onetag.trim());
    }

}
