package com.example.makepayment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.makepayment.classes.CustomListItem;
import com.example.makepayment.helpers.CustomListAdapter;
import com.example.makepayment.models.History;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        ArrayList<History> transactHistory = (ArrayList<History>) intent.getSerializableExtra("transactHistory");

        // TODO: Use the transactHistory to display the transactions history
        // TODO: Before this save the history data into the database

        ArrayList<CustomListItem> customList = new ArrayList<>();

        SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            customList.add(new CustomListItem("8000 Rs.", R.drawable.bhim_upi, spf.parse("12/3/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.phonepe_upi, spf.parse("12/4/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.gpay_upi, spf.parse("12/5/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.amanonpay_upi, spf.parse("12/6/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.paytm_upi, spf.parse("12/7/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.whatsapp_upi, spf.parse("12/8/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.others_upi, spf.parse("12/9/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.bhim_upi, spf.parse("12/10/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.gpay_upi, spf.parse("12/11/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.paytm_upi, spf.parse("12/12/2022")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.amanonpay_upi, spf.parse("12/1/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.phonepe_upi, spf.parse("12/2/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.whatsapp_upi, spf.parse("12/3/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.others_upi, spf.parse("12/4/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.bhim_upi, spf.parse("12/5/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.gpay_upi, spf.parse("12/6/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.phonepe_upi, spf.parse("12/7/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.whatsapp_upi, spf.parse("12/8/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.amanonpay_upi, spf.parse("12/9/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.paytm_upi, spf.parse("12/10/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.others_upi, spf.parse("12/11/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.bhim_upi, spf.parse("12/12/2023")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.gpay_upi, spf.parse("12/1/2024")));
            customList.add(new CustomListItem("8000 Rs.", R.drawable.phonepe_upi, spf.parse("12/2/2024")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CustomListAdapter adapter=new CustomListAdapter(this, customList);
        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Toast.makeText(getApplicationContext(),"You have click on the item",Toast.LENGTH_SHORT).show();
            }
        });
    }
}