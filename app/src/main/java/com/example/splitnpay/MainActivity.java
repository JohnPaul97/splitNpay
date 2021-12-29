package com.example.splitnpay;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splitnpay.ui.main.AddBillItemActivity;
import com.example.splitnpay.ui.main.BillItemAdapter;
import com.example.splitnpay.ui.main.MainFragment;
import com.example.splitnpay.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel = null;
    private ListView listView;
    private BillItemAdapter billItemAdapter;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        listView = findViewById(R.id.bills_list);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        billItemAdapter = new BillItemAdapter(this, R.layout.support_simple_spinner_dropdown_item, mainViewModel.getBillsList().getValue());

        listView.setAdapter(billItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Intent intent = new Intent(getApplicationContext(), AddBillItemActivity.class);

                intent.putExtra("bill", billItemAdapter.getItem(position));
                startActivity(intent);
            }
        });
//        mainViewModel.getBillsList().observe(this, this::outputBills);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void outputBills(final List<Bill> bills) {
//        setContentView(R.layout.main_activity);
//        ListView listView = (ListView) findViewById(R.id.bills_list);
//
//        ArrayAdapter<Bill> billArrayAdapter = new ArrayAdapter<Bill>(
//                this, R.layout.support_simple_spinner_dropdown_item, bills);
//        listView.setAdapter(billArrayAdapter);
//    }
}