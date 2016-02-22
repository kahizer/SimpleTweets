package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.Extras.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapter.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.activeandroid.util.Log;

public class timelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private long highestId = 0;
    private long lowestId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        client = TwitterApplication.getRestClient();
        populateTimeline(1, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compose, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.action_compose:
                    Toast.makeText(this, "composing..", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(timelineActivity.this, ComposeActivity.class);
                    startActivityForResult(i, 10);
                    return true;
            }


        }
        catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCocde, Intent data){
        if(resultCocde == 20 && requestCode == 10){
            String status = data.getStringExtra("status");
            //Toast.makeText(this, status, Toast.LENGTH_LONG).show();

            client.postTweet(status, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json){
                 //Toast.makeText(getApplicationContext(), "successful", Toast.LENGTH_LONG).show();
                    Tweet tweet = Tweet.fromJSON(json);
                    Log.w("my profile image url", tweet.getUser().getProfileImageUrl() );
                    aTweets.insert(tweet, 0);
                 }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorRespond) {
                    Toast.makeText(getApplicationContext(), "Failed to post status", Toast.LENGTH_LONG).show();
                }

            } );
        }
    }

    private void customLoadMoreDataFromApi(int page) {
        populateTimeline(1, lowestId);
    }


    private void populateTimeline(long sinceId, long maxId) {
        try{
//            client.getHometimeline(new JsonHttpResponseHandler(){
            client.getHometimeline(sinceId, maxId, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    //Log.d("DEBUG", json.toString());
                    //Toast.makeText(getApplicationContext(), "Loadded right", Toast.LENGTH_LONG).show();

                    aTweets.addAll(Tweet.fromJSONArray(json));

                    highestId = tweets.get(0).getUid();
                    lowestId = tweets.get(tweets.size() - 1).getUid();
                    //Toast.makeText(getApplicationContext(), "highest id at position 0 " + highestId, Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "lowest id at position " + tweets.size() + " " + lowestId, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorRespond) {
                    Log.d("DEBUG", errorRespond.toString());
                    Toast.makeText(getApplicationContext(), "failed to load", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception ex){
            Log.d("exception", ex.toString());
        }
    }
}
