package taystudio.presentasi.presentasi;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taystudio.presentasi.presentasi.Models.Event;
import taystudio.presentasi.presentasi.Models.EventFile;
import taystudio.presentasi.presentasi.Models.Like;
import taystudio.presentasi.presentasi.Models.Visitors;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;
import taystudio.presentasi.presentasi.SessionManager.SessionManager;

public class DetailActivity extends AppCompatActivity {
    private Button btnFileOpen;
    private  View dialogView;
    private AlertDialog.Builder builder;
    DownloadManager downloadManager;
    private ImageView detail_images;
    private TextView detail_title,detail_date,detail_desc,file_name,tvDownload;
    Bundle bundle;
    private Button btnLike;
    RetrofitService retrofitService;
    String url;

    String status;

    private void init()
    {
        btnFileOpen = findViewById(R.id.btnFileOpen);
        detail_images = findViewById(R.id.cover_image);
        detail_title = findViewById(R.id.detail_title);
        detail_date = findViewById(R.id.detail_date);
        detail_desc = findViewById(R.id.detail_desc);

        bundle = getIntent().getExtras();
        detail_title.setText(bundle.getString("title"));
        detail_desc.setText(bundle.getString("desc"));
        detail_date.setText(bundle.getString("date"));
        Picasso.with(getApplicationContext()).load(RetroServer.base_images+bundle.getString("image")).fit().centerCrop().into(detail_images);

        builder = new AlertDialog.Builder(DetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.layout_file,null);
        file_name = dialogView.findViewById(R.id.tv_file_name);
        tvDownload = dialogView.findViewById(R.id.download);
        builder.setView(dialogView);
        builder.setCancelable(true);
        btnLike = findViewById(R.id.btnLike);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        init();
        load_file();

        RetrofitService retroServer = RetroServer.getClient().create(RetrofitService.class);
        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        Call<Like> call_check = retrofitService.check_like(bundle.getString("id"),sessionManager.getKeyId(),"event");
        call_check.enqueue(new Callback<Like>() {
            @Override
            public void onResponse(Call<Like> call, Response<Like> response) {
                if (response.body().getMessage().equals("unlike"))
                {
                    btnLike.setBackgroundResource(R.color.merah);
                }


                status = response.body().getMessage();
            }

            @Override
            public void onFailure(Call<Like> call, Throwable t) {
                Log.e("MY_ERROR",t.getMessage());
            }
        });

        Call<Event> call = retroServer.count_event_view(bundle.getString("id"));
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.e("ERROR_MESSAGE",t.getMessage());
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("unlike"))
                {
                    btnLike.setBackgroundResource(R.color.darkGray);
                    Call<Like> unlike = retrofitService.unlike(bundle.getString("id"),sessionManager.getKeyId(),"event");
                    unlike.enqueue(new Callback<Like>() {
                        @Override
                        public void onResponse(Call<Like> call, Response<Like> response) {
                            status = "like";
                        }

                        @Override
                        public void onFailure(Call<Like> call, Throwable t) {
                            Log.e("MY_ERROR",t.getMessage());
                        }
                    });
                }else{
                    btnLike.setBackgroundResource(R.color.merah);
                    Call<Like> like = retrofitService.do_like(bundle.getString("id"),sessionManager.getKeyId(),"event");
                    like.enqueue(new Callback<Like>() {
                        @Override
                        public void onResponse(Call<Like> call, Response<Like> response) {
                            status = "unlike";
                        }

                        @Override
                        public void onFailure(Call<Like> call, Throwable t) {
                            Log.e("MY_ERROR",t.getMessage());
                        }
                    });
                }
            }
        });

        btnFileOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btnViewPPT = dialogView.findViewById(R.id.btnViewPPT);
                btnViewPPT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(RetroServer.base_url+"files/"+url);
                        Log.e("URL_LINK",RetroServer.base_url+"files/"+url);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        Long reference = downloadManager.enqueue(request);
                        retrofitService = RetroServer.getClient().create(RetrofitService.class);
                        Call<Visitors> download = retrofitService.download(bundle.getString("id"));
                        download.enqueue(new Callback<Visitors>() {
                            @Override
                            public void onResponse(Call<Visitors> call, Response<Visitors> response) {
                                if (response.body().getStatus().equals("success"))
                                {
                                    Toast.makeText(DetailActivity.this, "Downloading file...", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Visitors> call, Throwable t) {
                                Toast.makeText(DetailActivity.this, "Check your internet", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

               AlertDialog show = builder.show();
            }
        });

    }

    private void load_file()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please waiting...");
        progressDialog.show();
        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        Call<EventFile> getFile = retrofitService.getFile(bundle.getString("id"));
        getFile.enqueue(new Callback<EventFile>() {
            @Override
            public void onResponse(Call<EventFile> call, Response<EventFile> response) {
                if(response.body().getFile_password().equals("null")){
                    btnFileOpen.setVisibility(View.GONE);
                }else{
                    file_name.setText(response.body().getFile_name());
                    tvDownload.setText("Downloaded : "+response.body().getDownloaded_count());
                    url = response.body().getFile_name();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<EventFile> call, Throwable t) {
                Log.e("MY_ERROR",t.getMessage());
                progressDialog.hide();
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
