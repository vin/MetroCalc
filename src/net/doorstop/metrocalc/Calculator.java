package net.doorstop.metrocalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calculator extends Activity {
	
	private int cents;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void addFive(View view) {
    	cents += 5;
    	refresh();
    }
    
    public void subtractFive(View view) {
    	cents = Math.max(0, cents - 5);
    	refresh();
    }

	private void refresh() {
		TextView currentValue = (TextView) findViewById(R.id.currentValue);
		currentValue.setText(formatCents(cents));
		// TODO calculate the amount to add and show it too.
	}

	private String formatCents(int value) {
		return "$" + value / 100 + "." + value % 100; 
	}
}