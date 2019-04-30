package taystudio.presentasi.presentasi;

import android.app.DownloadManager;
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
import taystudio.presentasi.presentasi.Models.Article;
import taystudio.presentasi.presentasi.Models.Like;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;
import taystudio.presentasi.presentasi.SessionManager.SessionManager;

public class DetailArticleActivity extends AppCompatActivity {
    private ImageView detail_images;
    private TextView detail_title,detail_date,detail_desc,file_name,tvDownload;
    private Button btnLike;
    Bundle bundle;
    RetrofitService retrofitService;
    private String status;

    private void init()
    {
        detail_images = findViewById(R.id.cover_image);
        detail_title = findViewById(R.id.detail_title);
        detail_date = findViewById(R.id.detail_date);
        detail_desc = findViewById(R.id.detail_desc);

        bundle = getIntent().getExtras();
        detail_title.setText(bundle.getString("title"));
        detail_desc.setText(bundle.getString("desc"));
        detail_date.setText(bundle.getString("date"));
        Picasso.with(getApplicationContext()).load(RetroServer.base_images+bundle.getString("image")).fit().centerCrop().into(detail_images);

        btnLike = findViewById(R.id.btnLike);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Article");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        init();

        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        Call<Like> call_check = retrofitService.check_like(bundle.getString("id"),sessionManager.getKeyId(),"article");
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

            }
        });

        Call<Article> call = retrofitService.count_article_view(bundle.getString("id"));
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {

            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("MY_ERROR",t.getMessage());
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("unlike"))
                {
                    btnLike.setBackgroundResource(R.color.darkGray);
                    Call<Like> unlike = retrofitService.unlike(bundle.getString("id"),sessionManager.getKeyId(),"article");
                    unlike.enqueue(new Callback<Like>() {
                        @Override
                        public void onResponse(Call<Like> call, Response<Like> response) {
                            status = "unlike";
                        }

                        @Override
                        public void onFailure(Call<Like> call, Throwable t) {
                            Log.e("MY_ERROR",t.getMessage());
                        }
                    });
                }else{
                    btnLike.setBackgroundResource(R.color.merah);
                    Call<Like> like = retrofitService.do_like(bundle.getString("id"),sessionManager.getKeyId(),"article");
                    like.enqueue(new Callback<Like>() {
                        @Override
                        public void onResponse(Call<Like> call, Response<Like> response) {
                            status = "like";
                        }

                        @Override
                        public void onFailure(Call<Like> call, Throwable t) {
                            Log.e("MY_ERROR",t.getMessage());
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
