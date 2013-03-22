package co.earcos.android.budget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BudgetActivity extends Activity implements OnClickListener {

	private Button testButton;
	private TextView testText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_budget);

		testButton = (Button) findViewById(R.id.test_button);
		testText = (TextView) findViewById(R.id.test_text);

		testButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		if (view.equals(testButton)) {
			Log.i("test", "button pressed");
			testText.setText("sssssss");
		}
	}

	// protected void onResume() {
	// super.onResume();
	//
	// // ...
	//
	// if (mDBApi.getSession().authenticationSuccessful()) {
	// try {
	// // MANDATORY call to complete auth.
	// // Sets the access token on the session
	// mDBApi.getSession().finishAuthentication();
	//
	// AccessTokenPair tokens = mDBApi.getSession()
	// .getAccessTokenPair();
	//
	// // Provide your own storeKeys to persist the access token pair
	// // A typical way to store tokens is using SharedPreferences
	// storeKeys(tokens.key, tokens.secret);
	// } catch (IllegalStateException e) {
	// Log.i("DbAuthLog", "Error authenticating", e);
	// }
	// }
	//
	// // ...
	// }

}
