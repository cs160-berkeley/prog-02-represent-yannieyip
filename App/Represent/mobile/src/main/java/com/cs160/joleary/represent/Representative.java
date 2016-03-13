package com.cs160.joleary.represent;

/**
 * Created by yannie on 3/10/16.
 */

import java.util.ArrayList;

public class Representative {

    public String id;
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
    public ArrayList<String> comms;
    public ArrayList<String> bills;

    public Representative(String id, String first_name, String last_name, String party, String chamber,
                          String email, String website, String term_end, String pic_url) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.party = party;
        this.chamber = chamber;
        this.email = email;
        this.website = website;
        this.term_end = term_end;
        this.pic_url = pic_url;
    }

    public void setComms(ArrayList<String> all_committees) {
        comms = all_committees;
    }

    public void setBills(ArrayList<String> all_bills) {
        bills = all_bills;
    }
}
