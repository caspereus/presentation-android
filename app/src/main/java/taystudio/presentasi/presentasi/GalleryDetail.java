package taystudio.presentasi.presentasi;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taystudio.presentasi.presentasi.Adapter.GalleryAdapter;
import taystudio.presentasi.presentasi.Adapter.ImageGalleryAdapter;
import taystudio.presentasi.presentasi.Models.Gallery;
import taystudio.presentasi.presentasi.Models.ImageGallery;
import taystudio.presentasi.presentasi.Models.JSONGallery;
import taystudio.presentasi.presentasi.Models.JSONImageGallery;
import taystudio.presentasi.presentasi.Models.Like;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;
import taystudio.presentasi.presentasi.SessionManager.SessionManager;

public class GalleryDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ImageGallery> data;
    private ImageGalleryAdapter imageGallery;
    private ProgressDialog pd;
    private SwipeRefreshLayout swipeRefreshLayout;
    RetrofitService retrofitService;
    private Context context;
    LinearLayout linearLayout;
    Bundle bundle;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        swipeRefreshLayout = findViewById(R.id.myswipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load_event();
            }
        });

        context = this;
        bundle = getIntent().getExtras();
        recyclerView = findViewById(R.id.recycler_temp);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        linearLayout = findViewById(R.id.hidden_layout);
        final Button btnLike = findViewById(R.id.btnLike);

        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        Call<Like> call_check = retrofitService.check_like(bundle.getString("id"),sessionManager.getKeyId(),"gallery");
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

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("unlike"))
                {
                    btnLike.setBackgroundResource(R.color.darkGray);
                    Call<Like> unlike = retrofitService.unlike(bundle.getString("id"),sessionManager.getKeyId(),"gallery");
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
                    Call<Like> like = retrofitService.do_like(bundle.getString("id"),sessionManager.getKeyId(),"gallery");
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



        load_event();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void load_event()
    {
        pd = new ProgressDialog(context);
        pd.setMessage("Tunggu sebentar...");
        pd.show();
        pd.setCancelable(false);
        Call<JSONImageGallery> call = retrofitService.getImages(bundle.getString("id"));
        call.enqueue(new Callback<JSONImageGallery>() {
            @Override
            public void onResponse(Call<JSONImageGallery> call, Response<JSONImageGallery> response) {
                JSONImageGallery jsonGallery = response.body();
                data = new ArrayList<>(Arrays.asList(jsonGallery.getList()));
                imageGallery = new ImageGalleryAdapter(data,getApplicationContext());
                if (imageGallery.getItemCount() > 0){
                    recyclerView.setAdapter(imageGallery);
                    imageGallery.notifyDataSetChanged();
                    linearLayout.setVisibility(View.GONE);
                }else{
                    linearLayout.setVisibility(View.VISIBLE);
                }
                pd.dismiss();

                Call<Gallery> call1 = retrofitService.count_view(bundle.getString("id"));
                call1.enqueue(new Callback<Gallery>() {
                    @Override
                    public void onResponse(Call<Gallery> call, Response<Gallery> response) {
                        Log.e("GALLERYDETAIL",response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<Gallery> call, Throwable t) {

                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<JSONImageGallery> call, Throwable t) {
                Log.e("ERRORNYA",t.getMessage());
            }
        });

    }
}
