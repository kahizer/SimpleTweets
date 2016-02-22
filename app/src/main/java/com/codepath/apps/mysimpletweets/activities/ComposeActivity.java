package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.squareup.picasso.Picasso;

public class ComposeActivity extends AppCompatActivity {

    EditText etStatus;
    ImageView ivAccountProfilePicture;
    TextView tvCharacterCount;
    String accountProfileImageUrl = "http://pbs.twimg.com/profile_images/547492694826549250/D9YoSG7N_normal.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etStatus = (EditText) findViewById(R.id.etStatus);

        etStatus.addTextChangedListener(mTextEditorWatcher);
        ivAccountProfilePicture = (ImageView) findViewById(R.id.ivAccountProfilePicture);
        tvCharacterCount = (TextView) findViewById(R.id.tvCharacterCount);
        loadDisplay();
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length

            int characterCount = s.length();
            int numberOfCharactersLeft = 140 - characterCount;
            tvCharacterCount.setText(String.valueOf(numberOfCharactersLeft));
//            tvCharacterCount.setText(String.valueOf(s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public void loadDisplay(){
        ivAccountProfilePicture.setImageResource(android.R.color.transparent);
        Picasso.with(this).load(accountProfileImageUrl).into(ivAccountProfilePicture);
    }

    public void OnDiscard(View view) {
        this.finish();
    }

    public void OnSendPost(View view) {
        String status = etStatus.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("status", status);
        setResult(20, intent);
        finish();
    }
}
