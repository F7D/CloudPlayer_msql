package us.vp7.cloudplayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Farzad-msi7 on 5/21/2017.
 */
public class Play extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener,
                    SeekBar.OnSeekBarChangeListener,MediaPlayer.OnCompletionListener{
    TextView name,artist,current_time_textview,total_time_textview;
    ImageView play,next,prev,repeat,cover,blur_image,tarh,like,download_image;
    SeekBar seekbar;
    SQLiteDatabase mydb=null;
    MediaPlayer mediaPlayer;
    Context context;
    Bundle list;
    Boolean rep=false,like_type=false;
    long totatl_time,current_time;
    Utilities utils;
    public Handler handler=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        context=Play.this;
        utils=new Utilities();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        current_time_textview= (TextView) findViewById(R.id.duration);
        total_time_textview= (TextView) findViewById(R.id.endtime);
        name= (TextView) findViewById(R.id.song_name);
        artist= (TextView) findViewById(R.id.artis_name);
        play= (ImageView) findViewById(R.id.play);
        next= (ImageView) findViewById(R.id.next);
        prev= (ImageView) findViewById(R.id.prev);
        repeat= (ImageView) findViewById(R.id.repeat);
        cover= (ImageView) findViewById(R.id.main_cover);
        blur_image= (ImageView) findViewById(R.id.blur_picture);
        tarh= (ImageView) findViewById(R.id.background_tarh);
        like= (ImageView) findViewById(R.id.img_like);
        download_image= (ImageView) findViewById(R.id.download_img);
        seekbar= (SeekBar) findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(this);
        list=getIntent().getExtras();
        name.setText(list.getString("name"));
        artist.setText(list.getString("artist"));
        getSupportActionBar().setTitle(list.getString("name")+" |"+list.getString("artist"));
        play.setEnabled(false);
        next.setEnabled(false);
        prev.setEnabled(false);
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        Picasso.with(context).load(list.getString("picture")).into(cover);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play.setImageResource(R.mipmap.play);
                }else{
                    mediaPlayer.start();
                    play.setImageResource(R.mipmap.pause);
                    Update_seekbar_timer();
                }
            }
        });
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int currenttime=mediaPlayer.getCurrentPosition();
        if (currenttime+5000<=mediaPlayer.getDuration()){
            mediaPlayer.seekTo(currenttime+5000);
        }else{
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }
    }
});

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currenttime=mediaPlayer.getCurrentPosition();
                if (currenttime-5000>=0){
                    mediaPlayer.seekTo(currenttime-5000);
                }else{
                    mediaPlayer.seekTo(0);
                }

            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rep){
                    rep=false;
                    Toast.makeText(context,"Repeat off",Toast.LENGTH_SHORT).show();
                    repeat.setImageResource(R.mipmap.repeat_off);
                }else{
                    rep=true;
                    Toast.makeText(context,"Repeat on",Toast.LENGTH_SHORT).show();
                    repeat.setImageResource(R.mipmap.repeat_on);
                }
            }
        });

        mydb=openOrCreateDatabase("database",MODE_PRIVATE,null);

        Cursor c=mydb.rawQuery("SELECT * FROM music WHERE id="+Integer.parseInt(list.getString("id")),null);
        if (c.getCount()==1){
            like.setImageResource(R.mipmap.like_on);
            like_type=true;
        }else{
            like.setImageResource(R.mipmap.like_off);
            like_type=false;
        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=list.getString("id");
                String name=list.getString("name");
                String artist=list.getString("artist");
                String picture=list.getString("picture");
                String link=list.getString("link");
                if (like_type){
                    mydb.delete("music","id="+Integer.parseInt(id),null);
                    like.setImageResource(R.mipmap.like_off);
                    like_type=false;
                }else{
                    mydb.execSQL("INSERT INTO music (id,name,artist,picture,link) VALUES " +
                            "('" +Integer.parseInt(id)+"','"+name+"','"+artist+"','"+picture+"','"+link+"');");
                    like.setImageResource(R.mipmap.like_on);
                    like_type=true;

                }
            }
        });

        download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            DownloadTask down=new DownloadTask(context);
            down.execute(list.getString("link"),list.getString("name"));
            }
        });



        if (!mediaPlayer.isPlaying()){
            new Player().execute();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        seekbar.setSecondaryProgress(i);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    handler.removeCallbacks(null);
        int total=mediaPlayer.getDuration();
        int currentposition=utils.progressToTimer(seekBar.getProgress(),total);
        mediaPlayer.seekTo(currentposition);
        Update_seekbar_timer();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    handler.removeCallbacks(null);
        play.setImageResource(R.mipmap.play);
        if (rep){
            mediaPlayer.start();
            play.setImageResource(R.mipmap.pause);
            Update_seekbar_timer();

        }else{
           seekbar.setProgress(0);
            total_time_textview.setText("00:00");
            current_time_textview.setText("00:00");
            play.setImageResource(R.mipmap.play);
        }




    }

    private class Player extends AsyncTask {



        @Override
        protected Object doInBackground(Object[] objects) {
        try{
            mediaPlayer.setDataSource(list.getString("link"));
            mediaPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mediaPlayer.start();
            play.setEnabled(true);
            next.setEnabled(true);
            prev.setEnabled(true);
            play.setImageResource(R.mipmap.pause);
            cover.buildDrawingCache();
            Bitmap bit=cover.getDrawingCache();
            blur_image.setImageBitmap(BlurBuilder.blur(context,bit));
            tarh.setImageResource(R.mipmap.background_player);
            Update_seekbar_timer();


        }
    }

    private void Update_seekbar_timer() {
     try {
         if (mediaPlayer.isPlaying()) {
             totatl_time = mediaPlayer.getDuration();
             current_time = mediaPlayer.getCurrentPosition();
             total_time_textview.setText("" + utils.milliSecondsToTimer(totatl_time));
             current_time_textview.setText("" + utils.milliSecondsToTimer(current_time));
             int progress = (int) (utils.getProgressPercentage(current_time, totatl_time));
             seekbar.setProgress(progress);

             Runnable notif = new Runnable() {
                 @Override
                 public void run() {
                     Update_seekbar_timer();
                 }
             };
             handler.postDelayed(notif, 1000);
         }
     }catch (Exception e){
         e.printStackTrace();
     }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }



}
