package taystudio.presentasi.presentasi;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taystudio.presentasi.presentasi.Adapter.ArticleAdapter;
import taystudio.presentasi.presentasi.Adapter.EventAdapter;
import taystudio.presentasi.presentasi.Models.Article;
import taystudio.presentasi.presentasi.Models.Event;
import taystudio.presentasi.presentasi.Models.JSONArticle;
import taystudio.presentasi.presentasi.Models.JSONEvent;
import taystudio.presentasi.presentasi.Models.Presentator;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;

import static taystudio.presentasi.presentasi.RetrofitServer.RetroServer.base_url;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private View view;
    Button btnJoin;
    private  View dialogView;
    private AlertDialog.Builder builder;
    private AlertDialog show;
    private CircleImageView imageView;
    private TextView tvProfileName,tvEmail,tvAddress;
    private TextView tvSecondary,tvVisible;
    private RetrofitService retrofitService;
    private ImageView cardFacebook,cardInstagram,cardWhatsapp,cardWeb;
    private String urlFacebook,urlInstagram,urlWhatsapp,urlWebsite;
    ProgressDialog pd;
    private Button moreBtn,moreArticle;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Recycler 1
    private RecyclerView recyclerView;
    private ArrayList<Event> data;
    private EventAdapter eventAdapter;
    LinearLayout linearLayout;

    //Recycler 2
    private RecyclerView recyclerView2;
    private ArrayList<Article> data2;
    private ArticleAdapter articleAdapter;
    private LinearLayout linearLayout2,primaryLinear,linearEvent,linearArticle;
    private ProgressBar primaryProgres;
    private Integer event,article;

    private void init()
    {
        moreArticle = view.findViewById(R.id.btnMoreArticle);
        moreBtn = view.findViewById(R.id.btnMoreEvent);
        btnJoin = view.findViewById(R.id.btnJoinEvent);
        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        imageView = view.findViewById(R.id.circleImages);
        tvProfileName = view.findViewById(R.id.tvNameProfile);
        tvSecondary = view.findViewById(R.id.tvPositionProfile);
        cardFacebook = view.findViewById(R.id.cardFacebook);
        cardInstagram = view.findViewById(R.id.cardInstagram);
        cardWhatsapp = view.findViewById(R.id.cardWhatsapp);
        cardWeb = view.findViewById(R.id.cardWebsite);
        recyclerView = view.findViewById(R.id.recycler_temp);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView2 = view.findViewById(R.id.recycler_temp2);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(view.getContext());
        recyclerView2.setLayoutManager(layoutManager2);


        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        linearLayout = view.findViewById(R.id.hidden_layout);
        linearLayout2 = view.findViewById(R.id.hidden_layout2);
        primaryLinear = view.findViewById(R.id.linearPrimary);
        linearArticle = view.findViewById(R.id.linearArticle);
        linearEvent   = view.findViewById(R.id.linearEvent);
        primaryProgres = view.findViewById(R.id.primaryProgress);
        tvVisible = view.findViewById(R.id.textVisible);
    }

    private void load()
    {
        Call<Presentator> getPresentator = retrofitService.getPresentator();
        getPresentator.enqueue(new Callback<Presentator>() {
            @Override
            public void onResponse(Call<Presentator> call, Response<Presentator> response) {
                if (response.body() != null)
                {
                    tvProfileName.setText(response.body().getName());
                    tvSecondary.setText(response.body().getPosition()+ " of " + response.body().getCompany());
//                    circleImageView.setImageResource(R.drawable.author);
                    Picasso.with(view.getContext()).load(RetroServer.base_images+"presentator/"+response.body().getPhoto()).into(imageView);
                    Log.d("IMAGE_LINK",RetroServer.base_images+"presentator/"+response.body().getPhoto());
                    urlFacebook = response.body().getFacebook();
                    urlInstagram = response.body().getInstagram();
                    urlWhatsapp = response.body().getPhone();
                    urlWebsite     = response.body().getWebsite();
                    load_event();
                    primaryLinear.setVisibility(View.VISIBLE);
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Presentator> call, Throwable t) {
                Log.e("MY_ERROR",t.getMessage());
                pd.hide();
            }
        });
    }

    public void load_article()
    {
        Call<JSONArticle> call = retrofitService.getMainArticle();
        call.enqueue(new Callback<JSONArticle>() {
            @Override
            public void onResponse(Call<JSONArticle> call, Response<JSONArticle> response) {
                JSONArticle jsonArticle = response.body();
                data2 = new ArrayList<>(Arrays.asList(jsonArticle.getList()));
                articleAdapter = new ArticleAdapter(data2, getContext());
                if (articleAdapter.getItemCount() > 0) {
                    recyclerView2.setAdapter(articleAdapter);
                    articleAdapter.notifyDataSetChanged();
                    linearLayout2.setVisibility(View.GONE);
                    linearArticle.setVisibility(View.VISIBLE);
                } else {
                    moreArticle.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                }
                primaryProgres.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (articleAdapter.getItemCount() <= 0 && event <= 0) {
                    tvVisible.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JSONArticle> call, Throwable t) {
                Log.e("MY_ERROR",t.getMessage());
                pd.dismiss();
            }
        });

    }

    public void load_event()
    {
        Call<JSONEvent> call = retrofitService.getMainEvent();
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
                    linearEvent.setVisibility(View.VISIBLE);
                }else{
                    moreBtn.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }

                event = eventAdapter.getItemCount();
                load_article();
            }

            @Override
            public void onFailure(Call<JSONEvent> call, Throwable t) {
                Log.e("DATA_ERROR",t.getMessage());
                pd.dismiss();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        init();

        swipeRefreshLayout = view.findViewById(R.id.myswipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });

        pd = new ProgressDialog(getContext());
        pd.setMessage("Please waiting..");
        pd.show();
        pd.setCancelable(false);
        load();

        moreArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.textTitle.setText("Article");
                HomeActivity.ha.launchFragment(new NewsFragment());
            }
        });

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.textTitle.setText("Event");
                HomeActivity.ha.launchFragment(new EventFragment());
            }
        });

        cardWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlWebsite)));
            }
        });

        cardFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(urlFacebook);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.facebook.katana");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(urlFacebook)));
                }
            }
        });

        cardInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(urlInstagram);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(urlInstagram)));
                }
            }
        });

        cardWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent sendIntent =new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"");
                    sendIntent.putExtra("jid", "62"+urlWhatsapp.substring(1) +"@s.whatsapp.net");
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                }
                catch(Exception e)
                {
                    Toast.makeText(view.getContext(),"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.layout_code,null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                final ProgressBar checkProgress = dialogView.findViewById(R.id.checkProgress);
                Button goJoin = dialogView.findViewById(R.id.goJoin);

                goJoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkProgress.setVisibility(View.VISIBLE);
                        startActivity(new Intent(getContext(),DetailActivity.class));
                        show.dismiss();
                    }
                });

                show = builder.show();
            }
        });


        return view;
    }

}
