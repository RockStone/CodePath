package com.codepath.assignment.tipcalc;

import java.text.NumberFormat;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class TipCalcActivity extends Activity {

	final static NumberFormat formatter = NumberFormat.getCurrencyInstance();

	private EditText etBillAmount;
	private EditText etTipOther;
	private RadioGroup rgTipPercentage;
	private Button btnCalculate;
	private Button btnReset;
	private TextView tvTipAmount;
	private TextView tvTotalAmount;

	private int radioCheck = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calc);

		etBillAmount = (EditText) findViewById(R.id.etBillAmount);
		etBillAmount.requestFocus();

		etTipOther = (EditText) findViewById(R.id.etTipOther);
		etTipOther.setEnabled(false);

		rgTipPercentage = (RadioGroup) findViewById(R.id.rgTipPercentage);

		btnCalculate = (Button) findViewById(R.id.btnCalculate);
		btnCalculate.setEnabled(false);

		btnReset = (Button) findViewById(R.id.btnReset);

		tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);

		rgTipPercentage
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkId) {
						if (checkId == R.id.rbTip10 || checkId == R.id.rbTip15
								|| checkId == R.id.rbTip20) {
							etTipOther.setEnabled(false);
							btnCalculate.setEnabled(etBillAmount.getText()
									.length() > 0);
						} else if (checkId == R.id.rbOther) {
							etTipOther.setEnabled(true);
							etTipOther.requestFocus();
							btnCalculate.setEnabled(etBillAmount.getText()
									.length() > 0
									&& etTipOther.getText().length() > 0);
						}
						radioCheck = checkId;
					}
				});

		etBillAmount.setOnKeyListener(keyListener);
		etTipOther.setOnKeyListener(keyListener);

		btnCalculate.setOnClickListener(clickListener);
		btnReset.setOnClickListener(clickListener);
	}

	private OnKeyListener keyListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			switch (v.getId()) {
			case R.id.etBillAmount:
			case R.id.etTipOther:
				btnCalculate.setEnabled(etBillAmount.getText().length() > 0
						&& etTipOther.getText().length() > 0);
				break;
			}
			return false;
		}
	};

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btnCalculate) {
				calculate();
			} else {
				reset();
			}
		}
	};

	private void reset() {
		tvTipAmount.setText("");
		tvTotalAmount.setText("");
		etBillAmount.setText("");
		etTipOther.setText("");
		rgTipPercentage.clearCheck();
		etBillAmount.requestFocus();
	}

	private void calculate() {
		Double billAmount = Double.parseDouble(etBillAmount.getText()
				.toString());
		Double percentage = null;
		boolean isError = false;
		if (billAmount < 1.0) {
			showAlert("Enter a valid Bill Amount please.", etBillAmount.getId());
			isError = true;
		}

		if (radioCheck == -1) {
			radioCheck = rgTipPercentage.getCheckedRadioButtonId();
		}

		if (radioCheck == R.id.rbTip10) {
			percentage = 10.00;
		} else if (radioCheck == R.id.rbTip15) {
			percentage = 15.00;
		} else if (radioCheck == R.id.rbTip20) {
			percentage = 20.00;
		} else if (radioCheck == R.id.rbOther) {
			percentage = Double.parseDouble(etTipOther.getText().toString());
			if (percentage < 1.0) {
				showAlert("Enter a valid Tip Percentage please.",
						etTipOther.getId());
				isError = true;
			}
		}
		if (!isError) {
			double tipAmount = ((billAmount * percentage) / 100);
			double totalAmount = billAmount + tipAmount;

			tvTipAmount.setText(formatter.format(tipAmount));
			tvTotalAmount.setText(formatter.format(totalAmount));
		}
	}

	private void showAlert(String errorMessage, final int fieldId) {
		new AlertDialog.Builder(this)
				.setTitle("Error")
				.setMessage(errorMessage)
				.setNeutralButton("Close",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								findViewById(fieldId).requestFocus();
							}
						}).show();
	}

}
