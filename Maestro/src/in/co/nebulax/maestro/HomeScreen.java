package in.co.nebulax.maestro;

import com.parse.ParseUser;

import android.os.Bundle;
import android.widget.TextView;

public class HomeScreen extends MyActivity {

	TextView usrName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		usrName = (TextView) findViewById(R.id.tv_usrName);
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		usrName.setText(currentUser.getUsername());
	}
}
