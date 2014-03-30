package in.co.nebulax.maestro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.parse.ParseUser;

public class Splash extends MyActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		getSupportActionBar().hide();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash);



		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (isRemembered == true) {
						ParseUser user = ParseUser.getCurrentUser();
						if (user != null) {
							Intent i = new Intent(Splash.this, HomeScreen.class);
							startActivity(i);
						} else {
							Intent i = new Intent(Splash.this,
									LoginActivity.class);
							startActivity(i);
						}
					} else {
						Intent i = new Intent(Splash.this, LoginActivity.class);
						startActivity(i);
					}
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}