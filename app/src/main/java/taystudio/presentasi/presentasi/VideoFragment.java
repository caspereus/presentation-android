package taystudio.presentasi.presentasi;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taystudio.presentasi.presentasi.Adapter.EventAdapter;
import taystudio.presentasi.presentasi.Adapter.GalleryAdapter;
import taystudio.presentasi.presentasi.Adapter.VideoAdapter;
import taystudio.presentasi.presentasi.Models.Gallery;
import taystudio.presentasi.presentasi.Models.JSONEvent;
import taystudio.presentasi.presentasi.Models.JSONVideo;
import taystudio.presentasi.presentasi.Models.Video;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Video> data;
    private VideoAdapter videoAdapter;
    private ProgressDialog pd;
    private SwipeRefreshLayout swipeRefreshLayout;
    RetrofitService retrofitService;
    LinearLayout linearLayout;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);

        swipeRefreshLayout = view.findViewById(R.id.myswipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load_video();
            }
        });

        recyclerView = view.findViewById(R.id.recycler_temp);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        linearLayout = view.findViewById(R.id.hidden_layout);
        load_video();


        return view;
    }

    public void load_video()
    {
        pd = new ProgressDialog(getContext());
        pd.setMessage("Tunggu sebentar...");
        pd.show();
        pd.setCancelable(false);
        Call<JSONVideo> call = retrofitService.getVideo();
        call.enqueue(new Callback<JSONVideo>() {
            @Override
            public void onResponse(Call<JSONVideo> call, Response<JSONVideo> response) {
                JSONVideo jsonVideo = response.body();
                data = new ArrayList<>(Arrays.asList(jsonVideo.getList()));
                videoAdapter = new VideoAdapter(data,getContext());
                if (videoAdapter.getItemCount() > 0){
                    recyclerView.setAdapter(videoAdapter);
                    videoAdapter.notifyDataSetChanged();
                    linearLayout.setVisibility(View.GONE);
                }else{
                    linearLayout.setVisibility(View.VISIBLE);
                }
                pd.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<JSONVideo> call, Throwable t) {

            }
        });

    }

}
