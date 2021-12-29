package com.example.splitnpay.ui.main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.splitnpay.R;
import com.example.splitnpay.domain.Bill;
import com.example.splitnpay.service.BillService;

import static java.util.Collections.singletonList;

/**
 * use for save/view/edit modes
 */
public class AddBillItemActivity extends Activity {

    private EditText billNameEditText;
    private EditText splitNameEditText;
    private EditText splitBuyersEditText;
    private EditText splitTotalEditText;
    private TextView paymentsTextView;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bill_item);

        billNameEditText = findViewById(R.id.editText_billName);
        splitNameEditText = findViewById(R.id.editText_splitName);
        splitBuyersEditText = findViewById(R.id.editText_splitBuyers);
        splitTotalEditText = findViewById(R.id.editText_splitTotal);
        paymentsTextView = findViewById(R.id.textView_payments);

        final Bill bill = (Bill) getIntent().getSerializableExtra("bill");
        if (bill != null) { //view or edit mode
            billNameEditText.setText(bill.getName());
            outputPaymentDetails(bill);
        } else {
            //todo
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void outputPaymentDetails(final Bill bill) {
        final String paymentsOutput = BillService.cashoutBills(singletonList(bill))
                .entrySet()
                .stream()
                .map(entry -> "Mister " + entry.getKey() + " are de plata " + entry.getValue() + " lei")
                .reduce("", (s1, s2) -> s1 + '\n' + s2);

        paymentsTextView.setText(paymentsOutput);
    }
}
