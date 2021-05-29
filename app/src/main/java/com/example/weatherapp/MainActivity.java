package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //http://api.openweathermap.org/data/2.5/weather?q=Kanpur&appid=d3079bea2fd16071432de8069a5ed7d0

    EditText Searchbar;
    ImageView Searchicon;
    TextView temp;
    TextView Pressure;
    TextView Humidity;
    TextView Weathertype;
    String r;

    List<Weather> weatheratt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Searchbar=(EditText) findViewById(R.id.Search);
        Searchicon=(ImageView) findViewById(R.id.searchicon);
        temp=(TextView) findViewById(R.id.temp);
        Pressure=(TextView) findViewById(R.id.pressure);
        Humidity=(TextView) findViewById(R.id.humidity);
        Weathertype=(TextView) findViewById(R.id.weather);



        Searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name=Searchbar.getText().toString();
                Log.e(TAG, "onClick: "+name );
                getWeatherData(name);
            }
        });



    }
    public void getWeatherData(String s)
        {
            Log.e(TAG, "getWeatherData: in");

                Retrofit retrofit=new Retrofit.Builder().baseUrl("http://api.openweathermap.org/data/2.5/")
                        .addConverterFactory(GsonConverterFactory.create()).build();

            Api api=retrofit.create(Api.class);
            Call<Data> call=api.getdata(s);

            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {

                    if (response.isSuccessful()) {

                      Data data=response.body();
                      weatheratt=new ArrayList<>(Arrays.asList(data.getWeather()));

                      for(Weather w:weatheratt)
                      {
                           r=w.getDescription();
                      }

                        double k=response.body().getmain().getTemp();
                         int y= (int) (k-273);
                        temp.setText("Temperature is "+y+" *Celcius");
                        Pressure.setText("Pressure is "+response.body().getmain().getPressure()+" hpa");
                        Humidity.setText("Humidity is "+response.body().getmain().getHumidity()+" g/m^3");
                        Weathertype.setText("Weather Type: "+r);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"City Not Found",Toast.LENGTH_SHORT).show();
                    }

                    }
                @Override
                public void onFailure(Call<Data> call, Throwable t) {

                }
            });

}
}