package net.doorstop.metrocalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calculator extends Activity {
	
	private int cents = 200;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        refresh();
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
		String dollars = new DecimalFormat("$0.").format(value / 100);
		String cents = new DecimalFormat("00").format(value % 100);
		return dollars + cents;
	}
}