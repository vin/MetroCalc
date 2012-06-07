package net.doorstop.metrocalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Calculator extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int FARE = 225;
    private static final int BONUS_MULTIPLIER = 7;
    private static final int BONUS_THRESHOLD = 1000;
    private int currentCents = 200;
    private SharedPreferences preferences;
    private int wasteThreshold;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        refresh();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.settings:
            startActivity(new Intent(this, Preferences.class));
            return true;
        case R.id.about:
            startActivity(new Intent(this, About.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        wasteThreshold = Integer.parseInt(preferences.getString("wasteThreshold", "0")); 
        setTextViewText(R.id.currentValue, formatCents(currentCents));
        int suggestion = computeSuggestion(currentCents, wasteThreshold);
        setTextViewText(R.id.suggestion, formatCents(suggestion));
        int bonus = computeBonus(suggestion);
        setTextViewText(R.id.bonus, formatCents(bonus));
        int newTotal = currentCents + suggestion + bonus;
        setTextViewText(R.id.newTotal, formatCents(newTotal));
        int rides = newTotal / 225;
        setTextViewText(R.id.rides, Integer.toString(rides));
        int waste = newTotal % 225;
        setTextViewText(R.id.waste, formatCents(waste));
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
