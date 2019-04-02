package krasnov.bookcrossing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME = 100;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                for (int progress=0; progress<100; progress+=5) {
                    try {
                        sleep(SPLASH_TIME);
                        mProgress.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        myThread.start();
    }

}
