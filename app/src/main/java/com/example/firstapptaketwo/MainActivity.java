package com.example.firstapptaketwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String value;
    private String currency;

    String url = "http://api.fixer.io/latest?base=USD";
    String data = "";
    Map<String, Double> rates = new HashMap<>();

    ArrayList<Currency> currencyArray = new ArrayList<>();


    RequestQueue requestQueue;
    ArrayAdapter<Currency> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Currency usd = new Currency(getString(R.string.usd), 1.0);
        Currency gbp = new Currency(getString(R.string.gbp), 1.0);
        Currency eur = new Currency(getString(R.string.eur), 1.0);
        Currency nzd = new Currency(getString(R.string.nzd), 1.0);

        currencyArray.add(usd);
        currencyArray.add(gbp);
        currencyArray.add(eur);
        currencyArray.add(nzd);

        arrayAdapter = new ArrayAdapter<Currency>(this, android.R.layout.simple_list_item_2, android.R.id.text1, currencyArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text1.setText(currencyArray.get(position).value.toString());
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
        JsonObjectRequest obreq = new JsonObjectRequest(this.url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("rates");

                            String gbpKey = getString(R.string.gbp);
                            String eurKey = getString(R.string.eur);
                            String nzdKey = getString(R.string.nzd);

                            Double gbp = obj.getDouble(gbpKey);
                            Double eur = obj.getDouble(eurKey);
                            Double nzd = obj.getDouble(nzdKey);

                            rates.put(gbpKey, gbp);
                            rates.put(eurKey, eur);
                            rates.put(nzdKey, nzd);

                            for (int i = 0; i < currencyArray.size(); i++) {
                                Currency _c = currencyArray.get(i);
                                Double r = rates.get(_c.name);
                                if (r == null) {
                                    continue;
                                }
                                Currency c = new Currency(_c.name, r);
                                currencyArray.set(i, c);
                            }

                            arrayAdapter.notifyDataSetInvalidated();

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        requestQueue.add(obreq);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = (String) parent.getItemAtPosition(pos);
        this.currency = item;
        Log.d("DEBUG::", this.currency);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void convertDidTap(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        this.value = editText.getText().toString();
    }
}