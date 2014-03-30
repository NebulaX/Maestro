package in.co.nebulax.maestro;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyCustomReceiver extends BroadcastReceiver {
	private static final String TAG = "MyCustomReceiver";

	public static String recv_name, recv_email, recv_topic, recv_city,
			recv_phone;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			String action = intent.getAction();
			String channel = intent.getExtras().getString("com.parse.Channel");
			JSONObject json = new JSONObject(intent.getExtras().getString(
					"com.parse.Data"));
			PortalMaestro.request_status = 1;
			String content;
			// Log.d(TAG, "got action " + action + " on channel " + channel +
			// " with:");
			Iterator itr = json.keys();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				// Log.d(TAG, "..." + key + " => " + json.getString(key));
				// if(key.equalsIgnoreCase("content")){
				// content = json.getString(key);
				// Log.v(TAG , content);
				// String[] studata = content.split(",");
				// for (int i = 0; i<studata.length; i++){
				// //studata.split(":")
				// }
				// }
				recv_name = json.getString("name");
				recv_email = json.getString("email");
				recv_topic = json.getString("topic");
				recv_city = json.getString("city");
				recv_phone = json.getString("mobile");

				Bundle data = new Bundle();
				data.putString("name", recv_name);
				data.putString("email", recv_email);
				data.putString("topic", recv_topic);
				data.putString("city", recv_city);
				data.putString("phone", recv_phone);
				
				

			}
			
			Log.v(TAG , recv_name);
			Log.v(TAG , recv_email);
			Log.v(TAG , recv_topic);
			Log.v(TAG , recv_city);
			Log.v(TAG , recv_phone);
		} catch (JSONException e) {
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
}