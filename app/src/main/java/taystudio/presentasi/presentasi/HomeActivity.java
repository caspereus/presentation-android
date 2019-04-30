package taystudio.presentasi.presentasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.interfaces.AlertActionListener;
import com.irozon.alertview.objects.AlertAction;

import taystudio.presentasi.presentasi.SessionManager.SessionManager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView textTitle,tvHomeName,tvHomeEmail;
    SessionManager sessionManager;
    public static HomeActivity ha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        ha = this;
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Log.e("TOKEN_TAY", FirebaseInstanceId.getInstance().getToken());

        textTitle = findViewById(R.id.textTitle);
        sessionManager = new SessionManager(this);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main,new ProfileFragment());
        ft.commit();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvHomeName =headerView. findViewById(R.id.tvHomeName);
        tvHomeEmail = headerView.findViewById(R.id.tvHomeEmail);
        tvHomeName.setText(sessionManager.getKeyName());
        tvHomeEmail.setText(sessionManager.getKeyEmail());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        exitApp();

    }

    private void exitApp()
    {

        if (getCurrentFragment().toString().equals("ProfileFragment"))
        {
            AlertView alert = new AlertView("Confirmation", "Are you sure you want to close the application?", AlertStyle.BOTTOM_SHEET);
            alert.addAction(new AlertAction("Yes", AlertActionStyle.POSITIVE, new AlertActionListener() {
                @Override
                public void onActionClick(AlertAction alertAction) {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            }));

            alert.addAction(new AlertAction("Cancel", AlertActionStyle.NEGATIVE, new AlertActionListener() {
                @Override
                public void onActionClick(AlertAction alertAction) {

                }
            }));

            alert.show(HomeActivity.this);
        }else {
            launchFragment(new ProfileFragment());
        }
    }

    public String getCurrentFragment(){
        return ha.getSupportFragmentManager().findFragmentById(R.id.content_main).getClass().getSimpleName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_logout){
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.setKeyPhone("");
            sessionManager.setKeyEmail("");
            sessionManager.setKeyName("");
            sessionManager.setKeyId("");
            startActivity(new Intent(getApplicationContext(),FormActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        android.support.v4.app.Fragment fragment = null;
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {
            textTitle.setText("Gallery");
            fragment = new GalleryFragment();
        } else if (id == R.id.nav_slideshow) {
            textTitle.setText("Article");
            fragment = new NewsFragment();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else  if(id == R.id.nav_profile)
        {
            textTitle.setText("Home");
            fragment = new ProfileFragment();
        }else if (id == R.id.nav_event)
        {
            textTitle.setText("Event");
            fragment = new EventFragment();
        }else if(id == R.id.nav_contact)
        {
            textTitle.setText("Contact");
            fragment = new ContactFragment();
        }else if(id == R.id.nav_about)
        {
            textTitle.setText("About");
            fragment = new AboutFragment();
        }else if(id == R.id.nav_video)
        {
            textTitle.setText("Video");
            fragment = new VideoFragment();
        }

        launchFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main,fragment);
        ft.commit();
    }

}
