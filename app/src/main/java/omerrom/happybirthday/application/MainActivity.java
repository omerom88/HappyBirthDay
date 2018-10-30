package omerrom.happybirthday.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import omerrom.happybirthday.R;
import omerrom.happybirthday.loginscreen.LoginScreenActivity;


/**
 * Created by omerom88 on 23-Oct-18
 */
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            finish();
        }
    }



}
