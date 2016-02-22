package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "6vW7OImbNCjMTfRSgkf9fzEU0";       // Change this
	public static final String REST_CONSUMER_SECRET = "Oqw3ukjgYJr9V4hzY6BWobULHwIXvGczmkEmyAyiXy4ulwwm5V"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

	public void postTweet(String status, AsyncHttpResponseHandler handler){
		//https://api.twitter.com/1.1/statuses/update.json
		String apiUrl = getApiUrl("/statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status" , status);
		getClient().post(apiUrl, params, handler);
	}

	public void getHometimeline(long sinceId, long maxId,  AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("/statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if(sinceId > 0){
			params.put("since_id", sinceId);
		}
		if (maxId > 0){
			params.put("max_id", maxId);
		}

		getClient().get(apiUrl, params, handler);
	}

	public void getHometimeline(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("/statuses/home_timeline.json");
		// specify the params

		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);

		getClient().get(apiUrl, params, handler);
	}

	/*

	{
	  "coordinates": null,
	  "favorited": false,
	  "created_at": "Wed Sep 05 00:37:15 +0000 2012",
	  "truncated": false,
	  "id_str": "243145735212777472",
	  "entities": {
		"urls": [

		],
		"hashtags": [
		  {
			"text": "peterfalk",
			"indices": [
			  35,
			  45
			]
		  }
		],
		"user_mentions": [

		]
	  },
	  "in_reply_to_user_id_str": null,
	  "text": "Maybe he'll finally find his keys. #peterfalk",
	  "contributors": null,
	  "retweet_count": 0,
	  "id": 243145735212777472,
	  "in_reply_to_status_id_str": null,
	  "geo": null,
	  "retweeted": false,
	  "in_reply_to_user_id": null,
	  "place": null,
	  "user": {
		"name": "Jason Costa",
		"profile_sidebar_border_color": "86A4A6",
		"profile_sidebar_fill_color": "A0C5C7",
		"profile_background_tile": false,
		"profile_image_url": "http://a0.twimg.com/profile_images/1751674923/new_york_beard_normal.jpg",
		"created_at": "Wed May 28 00:20:15 +0000 2008",
		"location": "",
		"is_translator": true,
		"follow_request_sent": false,
		"id_str": "14927800",
		"profile_link_color": "FF3300",
		"entities": {
		  "url": {
			"urls": [
			  {
				"expanded_url": "http://www.jason-costa.blogspot.com/",
				"url": "http://t.co/YCA3ZKY",
				"indices": [
				  0,
				  19
				],
				"display_url": "jason-costa.blogspot.com"
			  }
			]
		  },
		  "description": {
			"urls": [

			]
		  }
		},
		"default_profile": false,
		"contributors_enabled": false,
		"url": "http://t.co/YCA3ZKY",
		"favourites_count": 883,
		"utc_offset": -28800,
		"id": 14927800,
		"profile_image_url_https": "https://si0.twimg.com/profile_images/1751674923/new_york_beard_normal.jpg",
		"profile_use_background_image": true,
		"listed_count": 150,
		"profile_text_color": "333333",
		"protected": false,
		"lang": "en",
		"followers_count": 8760,
		"time_zone": "Pacific Time (US & Canada)",
		"profile_background_image_url_https": "https://si0.twimg.com/images/themes/theme6/bg.gif",
		"verified": false,
		"profile_background_color": "709397",
		"notifications": false,
		"description": "Platform at Twitter",
		"geo_enabled": true,
		"statuses_count": 5532,
		"default_profile_image": false,
		"friends_count": 166,
		"profile_background_image_url": "http://a0.twimg.com/images/themes/theme6/bg.gif",
		"show_all_inline_media": true,
		"screen_name": "jasoncosta",
		"following": false
	  },
	  "source": "<a href="//jason-costa.blogspot.com%5C%22" rel="\"nofollow\"">My Shiny App</a>",
	  "in_reply_to_screen_name": null,
	  "in_reply_to_status_id": null
	}
	 */
}