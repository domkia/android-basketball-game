package domkia.basketball;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import domkia.basketball.framework.GameView;

public class MainActivity extends Activity
{
    public static Context ctx;
    GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        MainActivity.ctx = getApplicationContext();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (GameView)findViewById(R.id.mysurfaceview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }
}
