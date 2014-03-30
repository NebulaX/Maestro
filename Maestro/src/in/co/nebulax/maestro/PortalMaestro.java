package in.co.nebulax.maestro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.parse.ParseUser;

public class PortalMaestro extends SherlockFragment implements TabListener,
		OnClickListener {

	View v;
	TextView mName;
	ParseUser currUser;

	LinearLayout student_details;

	ImageView no_not;

	TextView stName, stEmail, stTopic, stLocation, stPhone;
	
	ImageButton accept , reject;
	public static int request_status = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_maestro, container, false);

		initialsiseFields();
		setFields();
		return v;
	}

	public void initialsiseFields() {

		currUser = ParseUser.getCurrentUser();
		mName = (TextView) v.findViewById(R.id.fragMaestro_tv_name);
		no_not = (ImageView) v.findViewById(R.id.imView_no_not);
		student_details = (LinearLayout) v
				.findViewById(R.id.ll_student_details);

		stName = (TextView) v.findViewById(R.id.fragMaestro_name);
		stEmail = (TextView) v.findViewById(R.id.fragMaestro_email);
		stTopic = (TextView) v.findViewById(R.id.fragMaestro_topic);
		stLocation = (TextView) v.findViewById(R.id.fragMaestro_location);
		stPhone = (TextView) v.findViewById(R.id.fragMaestro_phone);

		accept = (ImageButton) v.findViewById(R.id.fragMaestro_btn_accept);
		reject = (ImageButton) v.findViewById(R.id.fragMaestro_btn_reject);
	}

	public void setFields() {

		stName.setText(MyCustomReceiver.recv_name);
		stEmail.setText(MyCustomReceiver.recv_email);
		stTopic.setText(MyCustomReceiver.recv_topic);
		stLocation.setText(MyCustomReceiver.recv_city);
		stPhone.setText(MyCustomReceiver.recv_phone);

		mName.setText("Hi " + currUser.getString("Name"));
		if (request_status == 0) {
			no_not.setVisibility(View.VISIBLE);
			student_details.setVisibility(View.GONE);
		} else {
			no_not.setVisibility(View.GONE);
			student_details.setVisibility(View.VISIBLE);
		}
		
		accept.setOnClickListener(this);
		reject.setOnClickListener(this);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.add(android.R.id.content, this, "Maestro");
		ft.attach(this);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.detach(this);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.fragMaestro_btn_accept:
			  Intent callIntent = new Intent(Intent.ACTION_CALL);          
	            callIntent.setData(Uri.parse("tel:"+MyCustomReceiver.recv_phone));          
	            startActivity(callIntent); 
			break;
		case R.id.fragMaestro_btn_reject :
			request_status = 0;
			initialsiseFields();
			setFields();
			break;
		}

	}

}
