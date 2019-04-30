package taystudio.presentasi.presentasi;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taystudio.presentasi.presentasi.Models.Presentator;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.RetrofitService.RetrofitService;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private  View dialogView;
    private AlertDialog.Builder builder;
    private AlertDialog show;
    private CircleImageView imageView;
    private TextView tvProfileName,tvEmail,tvAddress,tvPhone;
    private TextView tvSecondary;
    private RetrofitService retrofitService;
    private ImageView cardFacebook,cardInstagram,cardWhatsapp,cardWeb;
    private String urlFacebook,urlInstagram,urlWhatsapp,urlWebsite;
    private ProgressDialog pd;
    private View view;
    private Context context;



    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_contact, container, false);

        retrofitService = RetroServer.getClient().create(RetrofitService.class);
        imageView = view.findViewById(R.id.circleImages);
        tvProfileName = view.findViewById(R.id.tvNameProfile);
        tvSecondary = view.findViewById(R.id.tvPositionProfile);
        cardFacebook = view.findViewById(R.id.cardFacebook);
        cardInstagram = view.findViewById(R.id.cardInstagram);
        cardWhatsapp = view.findViewById(R.id.cardWhatsapp);
        cardWeb = view.findViewById(R.id.cardWebsite);
        tvEmail = view.findViewById(R.id.tvcemail);
        tvPhone = view.findViewById(R.id.tvcphone);
        tvAddress = view.findViewById(R.id.tvcaddress);
        context = view.getContext();
        load();

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


        return view;
    }

    private void load()
    {
        pd = new ProgressDialog(context);
        pd.setMessage("Please wait...");
        pd.show();
        Call<Presentator> getPresentator = retrofitService.getPresentator();
        getPresentator.enqueue(new Callback<Presentator>() {
            @Override
            public void onResponse(Call<Presentator> call, Response<Presentator> response) {
                if (response.body() != null)
                {
                    tvProfileName.setText(response.body().getName());
                    tvSecondary.setText(response.body().getPosition()+ " - " + response.body().getCompany());
                    Picasso.with(view.getContext()).load(RetroServer.base_images+"presentator/"+response.body().getPhoto()).into(imageView);
                    Log.d("IMAGE_LINK",RetroServer.base_images+"presentator/"+response.body().getPhoto());
                    urlFacebook = response.body().getFacebook();
                    urlInstagram = response.body().getInstagram();
                    urlWhatsapp = response.body().getPhone();
                    urlWebsite     = response.body().getWebsite();
                    tvEmail.setText(response.body().getEmail());
                    tvAddress.setText(response.body().getAddress());
                    tvPhone.setText(response.body().getPhone());
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

}
