package omerrom.happybirthday.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import omerrom.happybirthday.R;
import omerrom.happybirthday.loginActivity.LoginScreenActivity;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startActivityForResult(new Intent(this, LoginScreenActivity.class), REQUEST_CODE);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            finish();
        }
    }



}
