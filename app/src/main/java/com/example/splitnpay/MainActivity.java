package com.example.splitnpay;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.splitnpay.domain.Bill;
import com.example.splitnpay.ui.main.MainFragment;
import com.example.splitnpay.ui.main.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel = null;




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

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getBillsList().observe(this, this::outputBills);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void outputBills(final List<Bill> bills) {
        setContentView(R.layout.main_activity);
        ListView listView = (ListView) findViewById(R.id.list);

        ArrayAdapter<Bill> billArrayAdapter = new ArrayAdapter<Bill>(
                this, R.layout.support_simple_spinner_dropdown_item, bills);
        listView.setAdapter(billArrayAdapter);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void outputBills() {
//        setContentView(R.layout.main_activity);
//        ListView listView = (ListView) findViewById(R.id.list);
//
//        Toast.makeText(this.getApplicationContext(), "should ouptut ", Toast.LENGTH_LONG).show();
//
//        ArrayAdapter<Bill> billArrayAdapter = new ArrayAdapter<Bill>(
//                this, R.layout.support_simple_spinner_dropdown_item, mainViewModel.getBillsList().getValue());
//        listView.setAdapter(billArrayAdapter);
//    }
}