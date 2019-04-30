package taystudio.presentasi.presentasi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.interfaces.AlertActionListener;
import com.irozon.alertview.objects.AlertAction;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taystudio.presentasi.presentasi.Models.Visitors;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;
import taystudio.presentasi.presentasi.SessionManager.SessionManager;

public class FormActivity extends AppCompatActivity {
    //Init Component
    MaterialEditText tvName,tvEmail,tvPhone;
    Button btnSignIn;
    ProgressBar loadingBar;
    RetrofitService retrofitService;
    SessionManager sessionManager;
    private void init()
    {
        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.getKeyEmail().equals(""))
        {

        }else{
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
        
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail  = findViewById(R.id.tvEmail);
        btnSignIn = findViewById(R.id.btnSignIn);
        loadingBar = findViewById(R.id.loadingBar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        init();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSignIn();
            }
        });

    }

    private void goSignIn()
    {
        final String name,email,phone;
        name = tvName.getText().toString();
        phone = tvPhone.getText().toString();
        email = tvEmail.getText().toString().toLowerCase();
        if (name.equals("") || phone.equals("") || email.equals(""))
        {
            Toast.makeText(FormActivity.this, "Your form is still empty!", Toast.LENGTH_SHORT).show();
        }else{
            AlertView alert = new AlertView("Confirmation", "Whether the data you input is correct?", AlertStyle.BOTTOM_SHEET);
            alert.addAction(new AlertAction("Yes", AlertActionStyle.POSITIVE, new AlertActionListener() {
                @Override
                public void onActionClick(AlertAction alertAction) {
                    loadingBar.setVisibility(View.VISIBLE);
                    addVisitor(name,email,phone);
                }
            }));

            alert.addAction(new AlertAction("No", AlertActionStyle.NEGATIVE, new AlertActionListener() {
                @Override
                public void onActionClick(AlertAction alertAction) {

                }
            }));

            alert.show(FormActivity.this);
        }
    }

    private void addVisitor(final String name, final String email, final String phone) {
        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        Call<Visitors> addVisitor = retrofitService.addVisitors(name,email,phone);
        addVisitor.enqueue(new Callback<Visitors>() {
            @Override
            public void onResponse(Call<Visitors> call, Response<Visitors> response) {
                if (response.body().getStatus().equals("success")){
                    Log.d("SUCCESS",response.body().getStatus());
                    sessionManager.setKeyId(response.body().getMessage());
                    sessionManager.setKeyEmail(email);
                    sessionManager.setKeyName(name);
                    sessionManager.setKeyPhone(phone);
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }else if(response.body().getStatus().equals("available"))
                {
                    Toast.makeText(FormActivity.this, "This email has been used", Toast.LENGTH_SHORT).show();
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Visitors> call, Throwable t) {
                if (t.getMessage().equals("timeout"))
                {
                    Toast.makeText(FormActivity.this, "Check Your Connection", Toast.LENGTH_SHORT).show();
                }
                Log.e("FORMACTIVITY",t.getMessage());
            }
        });
    }
}
