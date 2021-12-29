package com.example.splitnpay.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.splitnpay.R;
import com.example.splitnpay.domain.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillItemAdapter extends ArrayAdapter<Bill> {

    private List<Bill> bills = new ArrayList<>();

    public BillItemAdapter(@NonNull final Context context, final int resource, @NonNull final List<Bill> objects) {
        super(context, resource, objects);

        bills = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(super.getContext()).inflate(R.layout.bill_item, parent, false);

        Bill currentBill = bills.get(position);

        TextView name = listItem.findViewById(R.id.textView_bill_name);
        name.setText(currentBill.getName());

        TextView release = listItem.findViewById(R.id.textView_bill_total);
        release.setText(String.valueOf(currentBill.getTotal()));

        return listItem;
    }
}
