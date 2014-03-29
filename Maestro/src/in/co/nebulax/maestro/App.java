package in.co.nebulax.maestro;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Application;

public class App extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		
		// Initialisation of Parse
		Parse.initialize(this, getResources().getString(R.string.APP_ID),
				getResources().getString(R.string.CLIENT_KEY));
		
		ParseUser currUser = ParseUser.getCurrentUser();
		if (currUser != null) {
			PushService.setDefaultPushCallback(this, HomeScreen.class);
		} else {
			PushService.setDefaultPushCallback(this, LoginActivity.class);
		}
	}	
}