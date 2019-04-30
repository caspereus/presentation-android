package taystudio.presentasi.presentasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taystudio.presentasi.presentasi.Models.Like;
import taystudio.presentasi.presentasi.Models.Video;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;
import taystudio.presentasi.presentasi.SessionManager.SessionManager;

public class VideoDetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    Bundle bundle;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        bundle = getIntent().getExtras();

        YouTubePlayerView youTubePlayerView = findViewById(R.id.videoPlayer);
        youTubePlayerView.initialize("AIzaSyD7JPjjiz3x6-Yx7IpzxejFpCrETu21D5w",this);

        TextView textTitle = findViewById(R.id.titleVideo);
        textTitle.setText(bundle.getString("title"));

        TextView textDescription = findViewById(R.id.descriptionVideo);
        textDescription.setText(bundle.getString(" desc"));
        final Button btnLike = findViewById(R.id.btnLike);

        final RetrofitService retrofitService = RetroServer.getClient().create(RetrofitService.class);

        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        Call<Like> call_check = retrofitService.check_like(bundle.getString("id"),sessionManager.getKeyId(),"video");
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

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("unlike"))
                {
                    btnLike.setBackgroundResource(R.color.merah);
                    Call<Like> unlike = retrofitService.unlike(bundle.getString("id"),sessionManager.getKeyId(),"video");
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
                    btnLike.setBackgroundResource(R.color.darkGray);
                    Call<Like> like = retrofitService.do_like(bundle.getString("id"),sessionManager.getKeyId(),"video");
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

        Call<Video> call = retrofitService.count_video_view(bundle.getString("id"));
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {

            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.e("EERRROOR",t.getMessage());
            }
        });


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(bundle.getString("_GWjQZK2bCU"));
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
