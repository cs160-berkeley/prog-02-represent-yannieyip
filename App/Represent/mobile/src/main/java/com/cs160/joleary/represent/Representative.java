package com.cs160.joleary.represent;

/**
 * Created by yannie on 3/10/16.
 */
public class Representative {

    public String first_name;
    public String last_name;
    public String party;
    public String chamber;
    public String email;
    public String website;
    public String term_end;
    public String tweeter_name;
    public String tweet;
    public String pic_url;

    public Representative(String first_name, String last_name, String party, String chamber, String email, String website, String pic_url) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.party = party;
        this.chamber = chamber;
        this.email = email;
        this.website = website;
        this.pic_url = pic_url;
    }
}
