package com.example.splitnpay.ui.main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.splitnpay.R;
import com.example.splitnpay.domain.Bill;

/**
 * use for save/view/edit modes
 */
public class AddBillItemActivity extends Activity {

    private TextView textView;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bill_item);

        textView = findViewById(R.id.textView_add_bill_name);
        final Bill bill = (Bill) getIntent().getSerializableExtra("bill");
        textView.setText(bill.getName());
    }
}
