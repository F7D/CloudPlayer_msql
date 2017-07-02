package us.vp7.cloudplayer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Farzad-msi7 on 5/21/2017.
 */
class Adapter_frag2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public Context context;
    SQLiteDatabase mydb;
    public static ArrayList<String> id_data=new ArrayList<>();
    public static ArrayList<String> name_data=new ArrayList<>();
    public static ArrayList<String> artist_data=new ArrayList<>();
    public static ArrayList<String> picture_data=new ArrayList<>();
    public static ArrayList<String> link_mp3_data=new ArrayList<>();

    public Adapter_frag2(Context cont){
        context=cont;
        id_data.clear();  name_data.clear();   artist_data.clear();  picture_data.clear();
        link_mp3_data.clear();
        mydb=context.openOrCreateDatabase("database",context.MODE_PRIVATE,null);
        Cursor c=mydb.rawQuery("SELECT * FROM music",null);
        c.moveToFirst();
        if (c.getCount()>0){
            do{
                id_data.add(c.getString(0));
                name_data.add(c.getString(1));
                artist_data.add(c.getString(2));
                picture_data.add(c.getString(3));
                link_mp3_data.add(c.getString(4));

            }while (c.moveToNext());
        }





    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     final View view=LayoutInflater.from(context).inflate(R.layout.itemfeed,parent,false);

        return new CellFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, final int position) {
    final CellFeedViewHolder holder =(CellFeedViewHolder) viewholder;
        Picasso.with(context).load(picture_data.get(position)).into(holder.image);
        holder.name.setText(name_data.get(position));
        holder.artist.setText(artist_data.get(position));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i0=new Intent(context,Play.class);
            i0.putExtra("id",id_data.get(position));
            i0.putExtra("name",name_data.get(position));
            i0.putExtra("artist",artist_data.get(position));
            i0.putExtra("picture",picture_data.get(position));
            i0.putExtra("link",link_mp3_data.get(position));
            context.startActivity(i0);

            }
        });


    }


    public  void updateItems(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return id_data.size();
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
