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
import taystudio.presentasi.presentasi.Models.Event;
import taystudio.presentasi.presentasi.Models.JSONEvent;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Event> data;
    private EventAdapter eventAdapter;
    private ProgressDialog pd;
    private SwipeRefreshLayout swipeRefreshLayout;
    RetrofitService retrofitService;
    LinearLayout linearLayout;
    public EventFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_event, container, false);

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
        Call<JSONEvent> call = retrofitService.getEvent();
        call.enqueue(new Callback<JSONEvent>() {
            @Override
            public void onResponse(Call<JSONEvent> call, Response<JSONEvent> response) {
                JSONEvent jsonEvent = response.body();
                data = new ArrayList<>(Arrays.asList(jsonEvent.getList()));
                eventAdapter = new EventAdapter(data,getContext());
                if (eventAdapter.getItemCount() > 0){
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
            public void onFailure(Call<JSONEvent> call, Throwable t) {
                Log.e("DATA_ERROR",t.getMessage());
                pd.dismiss();
            }
        });

    }

}
