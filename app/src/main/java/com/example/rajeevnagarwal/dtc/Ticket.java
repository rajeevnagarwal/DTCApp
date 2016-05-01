package com.example.rajeevnagarwal.dtc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ticket extends AppCompatActivity {

    public TextView Cost1;
    public Button pay1;

    public static int cost=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        pay1  = (Button)findViewById(R.id.payticket);
        Intent i = getIntent();
        Integer C = (i.getExtras().getInt("Cost"));
        Cost1 = (TextView)findViewById(R.id.Cost);
        Cost1.setText("Your total fare is Rs."+C.toString());

        Ticket.cost=C;

        pay1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
     /*           Payment obj = new Payment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content,obj).commit();

       */     }

        });


    }

}
