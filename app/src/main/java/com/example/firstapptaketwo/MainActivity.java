package com.example.firstapptaketwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.carnival.sdk.Carnival;
import com.carnival.sdk.NotificationConfig;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {

    // What are the currency conversion rates?
    Map<String, Double> rates = new HashMap<>();

    // What is the selected currency?
    String selected = "USD";

    // What are the currencies displayed?
    ArrayList<Currency> currencyArray = new ArrayList<>();

    // What is the ListView's dataSource?
    ArrayAdapter<Currency> arrayAdapter;

    // Networking
    String url = "http://api.fixer.io/latest?base=USD";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect to Carnival.
        Carnival.startEngine(this, "6decb4a57343083c4eacd14f7edd38f9629075fb");

        // Configure notification icon.
        NotificationConfig config = new NotificationConfig();
        config.setSmallIcon(R.drawable.ic_stat_notification);
        Carnival.setNotificationConfig(config);

        // Configure text changed listener.
        EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(this);

        // Configure currency spinner.
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Rates are based on USD.
        rates.put(getString(R.string.usd), 1.0);

        // Add default values for the listView dataSource.
        Currency usd = new Currency(getString(R.string.usd), 1.0);
        Currency gbp = new Currency(getString(R.string.gbp), 1.0);
        Currency eur = new Currency(getString(R.string.eur), 1.0);
        Currency nzd = new Currency(getString(R.string.nzd), 1.0);

        currencyArray.add(usd);
        currencyArray.add(gbp);
        currencyArray.add(eur);
        currencyArray.add(nzd);

        // Customize arrayAdapter to provide special values for textfields.
        arrayAdapter = new ArrayAdapter<Currency>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, currencyArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                Double val = currencyArray.get(position).value;
                DecimalFormat formatter = new DecimalFormat("###,##0.00");
                formatter.setDecimalSeparatorAlwaysShown(true);
                text1.setText(formatter.format(val));
                text2.setText(currencyArray.get(position).name);
                return view;
            }
        };

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetInvalidated();

        getData();
    }

    protected void getData() {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(this.url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("rates");

                            String gbpKey = getString(R.string.gbp);
                            String eurKey = getString(R.string.eur);
                            String nzdKey = getString(R.string.nzd);

                            // Update rates based on API call.
                            Double gbp = obj.getDouble(gbpKey);
                            Double eur = obj.getDouble(eurKey);
                            Double nzd = obj.getDouble(nzdKey);

                            rates.put(gbpKey, gbp);
                            rates.put(eurKey, eur);
                            rates.put(nzdKey, nzd);

                            updateListView();
                        }
                        // Handle errors produced by JSON call.
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        requestQueue.add(req);
    }

    public void updateListView() {
        // Convert user input from string to decimal format.
        EditText editText = findViewById(R.id.editText);
        String text = editText.getText().toString();

        Double input = 1.0;
        if (!text.equals("")) {
            input = Double.parseDouble(text);
        }

        // Update the list view based on the selected currency and input values.
        for(int i = 0; i < currencyArray.size(); i++) {
            Currency _c = currencyArray.get(i);
            Double conversion = rates.get(_c.name) / rates.get(selected);
            Currency c = new Currency(_c.name, input * conversion);
            currencyArray.set(i, c);
        }

        arrayAdapter.notifyDataSetInvalidated();
    }

    // AdapterView.OnItemSelectedListener Implementation

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedCurrency = (String) parent.getItemAtPosition(pos);
        selected = selectedCurrency;
        updateListView();
    }

    public void onNothingSelected(AdapterView<?> parent) {}

    // TextWatcher Implementation

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        updateListView();
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}