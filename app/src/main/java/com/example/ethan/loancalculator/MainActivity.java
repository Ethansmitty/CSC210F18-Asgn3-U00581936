package com.example.ethan.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.text.TextWatcher;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double loanAmount = 0.0;
    private double percent = 0.15;
    private TextView interestAmountBox, paymentView1, paymentView2, paymentView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interestAmountBox = findViewById(R.id.interestAmountBox);
        paymentView1 = findViewById(R.id.paymentView1);
        paymentView2 = findViewById(R.id.paymentView2);
        paymentView3 = findViewById(R.id.paymentView3);

        interestAmountBox.setText(percentFormat.format(percent));
        paymentView1.setText(currencyFormat.format(0));
        paymentView2.setText(currencyFormat.format(0));
        paymentView3.setText(currencyFormat.format(0));

        EditText loanAmountBox = findViewById(R.id.loanAmountBox);
        loanAmountBox.addTextChangedListener(loanAmountBoxWatcher);
        SeekBar interestSeekBar = findViewById(R.id.interestSeekBar);
        interestSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }

    private void calc() {
        interestAmountBox.setText(percentFormat.format(percent));
        double monthlyPercent = percent / 12;
        double payment5yr, payment10yr, payment15yr;

        if (monthlyPercent != 0) {
            payment5yr = ((monthlyPercent * loanAmount) / (1 - (Math.pow(1 + monthlyPercent, -5))));
            payment10yr = ((monthlyPercent * loanAmount) / (1 - (Math.pow(1 + monthlyPercent, -10))));
            payment15yr = ((monthlyPercent * loanAmount) / (1 - (Math.pow(1 + monthlyPercent, -15))));
        } else {
            payment5yr = (loanAmount/ 60);
            payment10yr = (loanAmount/ 120);
            payment15yr = (loanAmount/ 180);
        }
        paymentView1.setText(currencyFormat.format(payment5yr));
        paymentView2.setText(currencyFormat.format(payment10yr));
        paymentView3.setText(currencyFormat.format(payment15yr));
    }

    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0;
            calc();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private final TextWatcher loanAmountBoxWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                loanAmount = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                loanAmount = 0.0;
            }
            calc();
        }

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };

}
