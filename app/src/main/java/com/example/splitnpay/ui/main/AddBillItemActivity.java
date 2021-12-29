package com.example.splitnpay.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splitnpay.MainActivity;
import com.example.splitnpay.R;
import com.example.splitnpay.domain.Bill;
import com.example.splitnpay.domain.Split;
import com.example.splitnpay.service.BillService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static android.text.TextUtils.isEmpty;
import static java.util.Collections.singletonList;

/**
 * use for save/view/edit modes
 */
public class AddBillItemActivity extends AppCompatActivity {

    private EditText billNameEditText;
    private EditText splitNameEditText;
    private EditText splitBuyersEditText;
    private EditText splitTotalEditText;
    private TextView paymentsTextView;
    private Button saveBillButton;

    private MainViewModel mainViewModel;

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
        saveBillButton = findViewById(R.id.save_bill_button);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        saveBillButton.setOnClickListener(v -> {
            final String billName = billNameEditText.getText().toString();
            final String splitName = billNameEditText.getText().toString();
            final String splitBuyers = splitBuyersEditText.getText().toString();
            final String splitTotal = splitTotalEditText.getText().toString();

            if (validateForm(billName, splitName, splitBuyers, splitTotal)) {
                mainViewModel.addBill(billName, parseSplit(splitName, splitBuyers, splitTotal));

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this.getApplicationContext(), "error in form", Toast.LENGTH_LONG).show();
            }

        });

        final Bill bill = (Bill) getIntent().getSerializableExtra("bill");
        if (bill != null) { //view or edit mode
            saveBillButton.setVisibility(View.GONE);
            billNameEditText.setText(bill.getName());
            outputPaymentDetails(bill);
        } else {
            saveBillButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateForm(final String billName, final String splitName, final String splitBuyers, final String splitTotal) {
        if (isEmpty(billName) || isEmpty(splitName) || isEmpty(splitBuyers) || isEmpty(splitTotal)) {
            return false;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Split parseSplit(final String splitName, final String splitBuyers, final String splitTotal) {
        final List<String> buyers = Stream.of(splitBuyers.split("[,]"))
                .collect(Collectors.toList());
        final double total = Double.parseDouble(splitTotal);

        return new Split(splitName, buyers, total);
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
