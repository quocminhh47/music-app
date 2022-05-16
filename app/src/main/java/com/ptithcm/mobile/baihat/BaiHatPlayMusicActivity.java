package com.ptithcm.mobile.baihat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.mobile.LoginActivity;
import com.ptithcm.mobile.MainActivity;
import com.ptithcm.mobile.R;
import com.ptithcm.mobile.service.SendMailActivity;

public class BaiHatPlayMusicActivity extends AppCompatActivity {
    SeekBar seekbarPlayer;
    TextView textViewTotalDuration, textViewCurentTime;
    ImageView imageViewPlayPause;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    BaiHatEntity baiHatEntity;
    Button btnTroVe;

    TextView textViewTenBH, textViewTenNS, textViewNamST;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai_hat_play_music_activity);
        setControl();
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.idHome:
                startActivity(new Intent(BaiHatPlayMusicActivity.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(BaiHatPlayMusicActivity.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(BaiHatPlayMusicActivity.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    //get info of song
    public BaiHatEntity layDuLieu() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return (BaiHatEntity) b.get("baiHat");
        } else {
            return null;
        }
    }

    public Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            textViewCurentTime.setText(milliSecondToTimer(currentDuration));
        }
    };

    private void setControl() {
        imageViewPlayPause = findViewById(R.id.imageViewPlayPause);
        seekbarPlayer = findViewById(R.id.seekbarPlayer);
        textViewTotalDuration = findViewById(R.id.textViewTotalDuration);
        textViewCurentTime = findViewById(R.id.textViewCurentTime);
        textViewTenBH = findViewById(R.id.textViewTenBH);
        textViewTenNS = findViewById(R.id.textViewTenNS);
//        textViewNamST = findViewById(R.id.textViewNamST);
        btnTroVe = findViewById(R.id.btnTroVe);
    }

    private void setEvent() {
        baiHatEntity = layDuLieu();
        //fill data
        textViewTenBH.setText(baiHatEntity.getTenBH());
        textViewTenNS.setText(baiHatEntity.getNhacSi().getTenNS());
//        textViewNamST.setText(baiHatEntity.getNamST());
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troVe();
            }
        });

        mediaPlayer = new MediaPlayer();
        //Độ chia của thanh seekbar. Min = 0(always), Max = 100, Step = 1(always)
        seekbarPlayer.setMax(100);

        //Handle process when click
        imageViewPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    imageViewPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                } else {
                    mediaPlayer.start();
                    imageViewPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    updateSeekbar();
                }
            }
        });

        prepareMediaPlayer();

        //
        seekbarPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                //100 là độ chia
                //Tính vị trí phát hiện tại khi người dùng touch
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                textViewCurentTime.setText(milliSecondToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        //Thanh xanh xanh
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                seekbarPlayer.setSecondaryProgress(i);
            }
        });

        //Khi phát đến cuối bài
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                seekbarPlayer.setProgress(0);
                imageViewPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                textViewCurentTime.setText("0:00");
                textViewTotalDuration.setText("0:00");
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });
    }

    //Khi back thì dừng
    @Override
    public void onBackPressed() {
        mediaPlayer.pause();
        super.onBackPressed();
    }

    private void troVe() {
        mediaPlayer.pause();
        Intent intent = new Intent(BaiHatPlayMusicActivity.this, BaiHatUserMainActivity.class);
        startActivity(intent);
    }

    public void prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource(baiHatEntity.getUrl());
            mediaPlayer.prepare();
            //Hiển thị tồng thời gian của một bài hát khi bắt đầu
            textViewTotalDuration.setText(milliSecondToTimer(mediaPlayer.getDuration()));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateSeekbar() {
        if (mediaPlayer.isPlaying()) {
            seekbarPlayer.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondToTimer(long milliSecond) {
        String timerString = "";
        String secondsString;
        //1 hour = 60 minutes, 1 minute = 60 seconds, 1 second = 1000 millisecond
        int hours = (int) (milliSecond / (1000 * 60 * 60));
        //Chia lấy dư để tính ra số mili giây
        //Rồi tính được số phút
        int minutes = (int) ((milliSecond % (1000 * 60 * 60)) / (1000 * 60));
        //Tương tự
        int seconds = (int) ((milliSecond % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            timerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;

        }
        //0:0:00
        timerString = timerString + minutes + ":" + secondsString;
        return timerString;
    }

}
