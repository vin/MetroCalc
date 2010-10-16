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
    
    public void addCents(int n) {
    	cents += n;
    	cents = Math.max(0, cents);
    	refresh();
    }
    
    public void add5(View view) {
    	addCents(5);
    }
    
    public void add25(View view) {
    	addCents(25);
    }
    
    public void add100(View view) {
    	addCents(100);
    }
    
    public void subtract5(View view) {
    	addCents(-5);
    }

    public void subtract25(View view) {
    	addCents(-25);
    }

    public void subtract100(View view) {
    	addCents(-100);
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