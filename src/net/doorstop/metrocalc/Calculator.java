package net.doorstop.metrocalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calculator extends Activity {

    private static int TABLE[] = {
        1370, 1170, 970, 2335, 2135, 1935,
        1735, 1535, 1335, 1135, 935, 2105,
        1905, 1705, 1505, 1305, 1105, 905,
        900, 2070, 1870, 1670, 1470,
        1270, 1070, 870, 2235, 2035,
        1835, 1635, 1435, 1235, 1035,
        835, 2005, 1805, 1605, 1405,
        1205, 1005, 805, 800, 1970,
        1770, 1570};

    private static final int RIDE_COST = 225;
    private static final int BONUS_MULTIPLIER = 15;
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
        int suggestion = computeSuggestionWithBonus(currentCents);
        setTextViewText(R.id.suggestion, formatCents(suggestion));
        int bonus = suggestion * BONUS_MULTIPLIER / 100;
        setTextViewText(R.id.bonus, formatCents(bonus));
        int newTotal = currentCents + suggestion + bonus;
        setTextViewText(R.id.newTotal, formatCents(newTotal));
    }

    public static int computeSuggestionWithBonus(int cents) {
        if (cents % 5 != 0) {
            throw new IllegalArgumentException("input must be nonnegative multiple of 5.");
        }
        int i = cents % 225 / 5;
        return TABLE[i];
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
