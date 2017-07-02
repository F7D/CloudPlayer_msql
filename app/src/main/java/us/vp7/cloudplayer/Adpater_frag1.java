package us.vp7.cloudplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Farzad-msi7 on 5/21/2017.
 */
class Adapter_frag1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public Context context;
    public Adapter_frag1(Context cont){
        context=cont;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     final View view=LayoutInflater.from(context).inflate(R.layout.itemfeed,parent,false);

        return new CellFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, final int position) {
    final CellFeedViewHolder holder =(CellFeedViewHolder) viewholder;
        Picasso.with(context).load(Splash.picture.get(position)).into(holder.image);
        holder.name.setText(Splash.name.get(position));
        holder.artist.setText(Splash.artist.get(position));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i0=new Intent(context,Play.class);
            i0.putExtra("id",Splash.id.get(position));
            i0.putExtra("name",Splash.name.get(position));
            i0.putExtra("artist",Splash.artist.get(position));
            i0.putExtra("picture",Splash.picture.get(position));
            i0.putExtra("link",Splash.link_mp3.get(position));
            context.startActivity(i0);

            }
        });


    }


    public  void updateItems(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Splash.id.size();
    }

    private class  CellFeedViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,artist;
        CardView card;
        public CellFeedViewHolder(View view) {
            super(view);
            image= (ImageView) view.findViewById(R.id.img);
            name= (TextView) view.findViewById(R.id.namesong);
            artist= (TextView) view.findViewById(R.id.nameartist);
            card= (CardView) view.findViewById(R.id.card);





        }
    }
}
