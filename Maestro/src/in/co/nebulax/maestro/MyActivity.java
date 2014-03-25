package in.co.nebulax.maestro;

import in.co.nebulax.maestro.utils.ConnectionDetector;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class MyActivity extends SherlockActivity implements OnClickListener{

	public ConnectionDetector cd;
	public Boolean isInternetPresent;
	
	public MyActivity() {
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		
		if(!isInternetPresent) {
			Toast.makeText(this,"Unable to establish a connection", Toast.LENGTH_LONG).show();
		} 
	}
	
	@Override
	public void onClick(View v) {
	}
}