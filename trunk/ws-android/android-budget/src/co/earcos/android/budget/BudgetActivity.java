package co.earcos.android.budget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BudgetActivity extends Activity implements OnClickListener  {

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
			Log.i("test","button pressed");
			testText.setText("sssssss");
		} 
	}

}
