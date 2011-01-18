package net.doorstop.metrocalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calculator extends Activity {

    private static final int FARE = 225;

    private static int TABLE[] = { 3575, 3360, 2935, 2720, 2505, 2290, 2075,
            1860, 1435, 1220, 1005, 1000, 4360, 3935, 3720, 3505, 3290, 3075,
            2860, 2435, 2220, 2005, 1790, 1575, 1360, 1145, 1140, 4290, 4075,
            3860, 3435, 3220, 3005, 2790, 2575, 2360, 1935, 1720, 1505, 1290,
            1075, 1070, 1065, 4005, 3790 };

    private static final int BONUS_MULTIPLIER = 7;
    private static final int BONUS_THRESHOLD = 1000;
    private int currentCents = 200;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        refresh();
    }

    public void addCents(View v) {
        addCents(Integer.valueOf((String) v.getTag()));
    }

    public void addCents(int n) {
        currentCents += n;
        currentCents = Math.max(0, currentCents);
        refresh();
    }

    public static int computeBonus(int amount) {
        if (amount < BONUS_THRESHOLD) {
            return 0;
        } else {
            return (amount * BONUS_MULTIPLIER + 50) / 100;
        }
    }

    private void refresh() {
        setTextViewText(R.id.currentValue, formatCents(currentCents));
        int suggestion = computeSuggestion(currentCents, 0);
        setTextViewText(R.id.suggestion, formatCents(suggestion));
        int bonus = computeBonus(suggestion);
        setTextViewText(R.id.bonus, formatCents(bonus));
        int newTotal = currentCents + suggestion + bonus;
        setTextViewText(R.id.newTotal, formatCents(newTotal));
    }

    public static int lookupSuggestion(int cents) {
        if (cents % 5 != 0) {
            throw new IllegalArgumentException(
                    "input must be nonnegative multiple of 5.");
        }
        int i = cents % FARE / 5;
        return TABLE[i];
    }
    
    public static int computeSuggestion(int cents, int wasteThreshold) {
        int result = BONUS_THRESHOLD;
        while ((cents + result + computeBonus(result)) % FARE > wasteThreshold) {
            result += 5;
        }
        return result;
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
