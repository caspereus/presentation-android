package taystudio.presentasi.presentasi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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

import taystudio.presentasi.presentasi.GalleryDetail;
import taystudio.presentasi.presentasi.Models.Gallery;
import taystudio.presentasi.presentasi.Models.ImageGallery;
import taystudio.presentasi.presentasi.R;
import taystudio.presentasi.presentasi.RetrofitServer.RetroServer;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.viewHolder> {
    private ArrayList<ImageGallery> list;
    private Context context;

    public ImageGalleryAdapter(ArrayList<ImageGallery> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_images,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        Picasso.with(context).load(RetroServer.base_images+"/gallery/"+list.get(position).getImage_name()).fit().centerCrop().into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public viewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageList);
        }
    }
}
