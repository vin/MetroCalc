package net.doorstop.metrocalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calculator extends Activity {

    private static final int RIDE_COST = 225;
    private static final float BONUS_MULTIPLIER = 1.15f;
    private static final int BONUS_THRESHOLD = 800;
    private int currentCents = 200;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        refresh();
    }

    public void addCents(int n) {
        currentCents += n;
        currentCents = Math.max(0, currentCents);
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
        setTextViewText(R.id.currentValue, formatCents(currentCents));
        setTextViewText(R.id.suggestion,
                formatCents(computeSuggestion(currentCents)));
    }

    public static int mod(int dividend, int divisor) {
        int result = dividend % divisor;
        if (result < 0) {
            result += divisor;
        }
        return result;
    }
    
    public static int computeSuggestion(int cents) {
        return mod(-cents, RIDE_COST);
    }

    private void setTextViewText(int textViewId, String value) {
        TextView view = (TextView) findViewById(textViewId);
        view.setText(value);
    }

    private String formatCents(int value) {
        String dollars = new DecimalFormat("$0.").format(value / 100);
        String cents = new DecimalFormat("00").format(value % 100);
        return dollars + cents;
    }
}
