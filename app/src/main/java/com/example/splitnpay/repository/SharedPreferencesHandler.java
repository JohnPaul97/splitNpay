package com.example.splitnpay.repository;

import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.splitnpay.domain.Bill;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

public class SharedPreferencesHandler {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void rehidrateLocalCache(final SharedPreferences sharedPreferences) {
        final Gson gson = new Gson();
        final List<Bill> bills = sharedPreferences.getAll()
                .keySet()
                .stream()
                .map(o -> sharedPreferences.getString(o, ""))
                .peek(System.out::println)
                .map(obj -> gson.fromJson(obj, Bill.class))
                .collect(Collectors.toList());

        BillRepository.getInstance().fetchListFromCache(bills);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static void flushLocalCacheToAppCache(final SharedPreferences sharedPreferences) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();
//
//        BillRepository.getInstance().getBills().getValue()
//                .forEach(bill -> saveBill(editor, bill));
//    }

    public static void saveBill(final SharedPreferences sharedPreferences, final Bill bill) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bill);

        editor.remove(bill.getName()).commit(); //remove old entry if exists
        editor.putString(bill.getName(), json);
        editor.commit();
    }
}
