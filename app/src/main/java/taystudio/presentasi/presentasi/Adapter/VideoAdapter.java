package taystudio.presentasi.presentasi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import taystudio.presentasi.presentasi.DetailActivity;
import taystudio.presentasi.presentasi.Models.Event;
import taystudio.presentasi.presentasi.Models.Video;
import taystudio.presentasi.presentasi.R;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;
import taystudio.presentasi.presentasi.VideoDetailActivity;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {
    private ArrayList<Video> list;
    private Context context;

    public VideoAdapter(ArrayList<Video> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_events,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.titleEvent.setText(list.get(position).getTitle());
        holder.descEvent.setText(list.get(position).getViews()+" Views");
        holder.dateEvent.setText(list.get(position).getCreated_at());

        Picasso.with(context).load("https://img.youtube.com/vi/"+list.get(position).getUrl_link()+"/0.jpg").fit().centerCrop().into(holder.imageEvent);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, VideoDetailActivity.class);
                detail.putExtra("id",list.get(position).getId());
                detail.putExtra("title",list.get(position).getTitle());
                detail.putExtra(" desc",list.get(position).getDescription());
                detail.putExtra("date",list.get(position).getCreated_at());
                detail.putExtra("video_id",list.get(position).getUrl_link());
                view.getContext().startActivity(detail);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imageEvent;
        private CardView cardView;
        private TextView titleEvent,dateEvent,descEvent;
        public viewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardLayout);
            imageEvent = view.findViewById(R.id.imageEvent);
            titleEvent = view.findViewById(R.id.titleEvent);
            dateEvent = view.findViewById(R.id.dateEvent);
            descEvent = view.findViewById(R.id.placeEvent);
        }
    }
}
