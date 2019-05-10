package com.prabakaran_g.dream11star;




import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


public class AdminLoginActivity extends AppCompatActivity {
    private Toolbar matchToolbar;


    EditText upass;
    Button enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        matchToolbar = (Toolbar)findViewById(R.id.adminlogin_page_toolbar);
        setSupportActionBar(matchToolbar);
        getSupportActionBar().setTitle("Dream11Star Teams");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);



        upass = (EditText)findViewById(R.id.e2);
        enter = (Button)findViewById(R.id.adminbutton);




        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upass.getText().toString().equals("gpkguru007")) {

                    Intent u = new Intent(AdminLoginActivity.this,AddPostActivity.class);
                    startActivity(u);

                }
                else
                {
                    Toast.makeText(AdminLoginActivity.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }




}


