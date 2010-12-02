package net.doorstop.metrocalc.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;
import net.doorstop.metrocalc.Calculator;
import net.doorstop.metrocalc.R;

public class CalculatorTest extends
        ActivityInstrumentationTestCase2<Calculator> {

	private Calculator calculator;
	private TextView currentValue;
	private TextView newTotal;
    
	public CalculatorTest() {
        super("net.doorstop.metrocalc", Calculator.class);
    }
    
	@Override
	public void setUp() throws Exception {
		super.setUp();
		calculator = getActivity();
		currentValue = (TextView) calculator.findViewById(R.id.currentValue);
		newTotal = (TextView) calculator.findViewById(R.id.newTotal);
	}
	
	public void testPreconditions() {
		assertEquals("$2.00", currentValue.getText());
	}
		
	@UiThreadTest
	public void testAddCentsNegative() {
		calculator.addCents(-100);
		assertEquals("$1.00", currentValue.getText());
	}

	@UiThreadTest
	public void testAddCentsBelowZero() {
		calculator.addCents(-1000);
		assertEquals("$0.00", currentValue.getText());
	}
	
	private void assertIsEvenMultiple(String amount) {
		String centsStr = amount.replaceAll("[^0-9]", "");
		int cents = Integer.parseInt(centsStr);
		assertEquals(0, cents % 225);
	}
	
	@UiThreadTest
	public void testTotalIsEvenMultiple() {
		calculator.addCents(-200);
		for (int i = 0; i < 1000; i += 5) {
			assertIsEvenMultiple(newTotal.getText().toString());
			calculator.addCents(5);
		}
	}
}
