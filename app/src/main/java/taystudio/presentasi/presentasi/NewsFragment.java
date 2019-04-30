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
import taystudio.presentasi.presentasi.Adapter.ArticleAdapter;
import taystudio.presentasi.presentasi.Adapter.EventAdapter;
import taystudio.presentasi.presentasi.Models.Article;
import taystudio.presentasi.presentasi.Models.Event;
import taystudio.presentasi.presentasi.Models.JSONArticle;
import taystudio.presentasi.presentasi.Models.JSONEvent;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Article> data;
    private ArticleAdapter eventAdapter;
    private ProgressDialog pd;
    private SwipeRefreshLayout swipeRefreshLayout;
    RetrofitService retrofitService;
    LinearLayout linearLayout;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        swipeRefreshLayout = view.findViewById(R.id.myswipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load_event();
            }
        });

        recyclerView = view.findViewById(R.id.recycler_temp);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        linearLayout = view.findViewById(R.id.hidden_layout);
        load_event();


        return view;
    }

    public void load_event()
    {
        pd = new ProgressDialog(getContext());
        pd.setMessage("Tunggu sebentar...");
        pd.show();
        pd.setCancelable(false);
       Call<JSONArticle> call = retrofitService.getArticle();
       call.enqueue(new Callback<JSONArticle>() {
           @Override
           public void onResponse(Call<JSONArticle> call, Response<JSONArticle> response) {
               JSONArticle jsonArticle = response.body();
               data = new ArrayList<>(Arrays.asList(jsonArticle.getList()));
               eventAdapter = new ArticleAdapter(data,getContext());
               if (eventAdapter.getItemCount() > 0)
               {
                   recyclerView.setAdapter(eventAdapter);
                   eventAdapter.notifyDataSetChanged();
                   linearLayout.setVisibility(View.GONE);
               }else{
                   linearLayout.setVisibility(View.VISIBLE);
               }
               pd.dismiss();
               swipeRefreshLayout.setRefreshing(false);
           }

           @Override
           public void onFailure(Call<JSONArticle> call, Throwable t) {
                Log.e("MY_ERROR",t.getMessage());
                pd.dismiss();
           }
       });

    }

}
