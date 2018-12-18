package domkia.basketball;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity
{
    private Context context = this;
    private Button startButton;
    private Button infoButton;
    private Button quitButton;
    private ImageView ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startButton = findViewById(R.id.main_menu_start);
        infoButton = findViewById(R.id.main_menu_info);
        quitButton = findViewById(R.id.main_menu_quit);

        //Spinning ball
        ball = findViewById(R.id.main_menu_ball);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spinning_animation);
        ball.startAnimation(startRotateAnimation);

        startButton.setOnClickListener(onClickStart);
        infoButton.setOnClickListener(onClickInfo);
        quitButton.setOnClickListener(onClickQuit);
    }

    private View.OnClickListener onClickStart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GameActivity.class);
            context.startActivity(intent);
        }
    };

    private View.OnClickListener onClickInfo = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Domantas Kiaušinis IFF-6/15", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener onClickQuit = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            finish();
            System.exit(0);
        }
    };
}
