package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by josevillanuva on 2/19/16.
 */
public class User {

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    /*
    "user": {
        "name": "Raffi Krikorian",
                "profile_sidebar_fill_color": "DDEEF6",
                "profile_background_tile": false,
                "profile_sidebar_border_color": "C0DEED",
                "profile_image_url": "http://a0.twimg.com/profile_images/1270234259/raffi-headshot-casual_normal.png",
                "created_at": "Sun Aug 19 14:24:06 +0000 2007",
                "location": "San Francisco, California",
                "follow_request_sent": false,
                "id_str": "8285392",
                "is_translator": false,
                "profile_link_color": "0084B4",
                "entities": {
                    "url": {
                        "urls": [
                            {
                                "expanded_url": "http://about.me/raffi.krikorian",
                                    "url": "http://t.co/eNmnM6q",
                                    "indices": [
                                0,
                                        19
                                ],
                                "display_url": "about.me/raffi.krikorian"
                            }
                        ]
                    }

            */

    public static User fromJSON(JSONObject json){
        User u = new User();

        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;

    }

}
