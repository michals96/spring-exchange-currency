package com.currencyexchange.repositories;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;

@Repository
@Component("javaCurrencyExchangeApiRepository")
public class JavaCurrencyExchangeApiRepository implements CurrencyExchangeRepository{

    public String buildQuery(String sourceCurrency, String targetCurrency){
        return "https://api.exchangeratesapi.io/latest?base=" + sourceCurrency + "&symbols=" + targetCurrency;
    }

    @Override
    public Double calculate(String sourceCurrency, String targetCurrency) throws IOException, InterruptedException, JSONException {

        /* HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(buildQuery(sourceCurrency, targetCurrency)))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String jsonString = response.body();
        JSONObject obj = new JSONObject(jsonString);

        String courseStr = obj.getJSONObject("rates").getString(targetCurrency);

        return Double.parseDouble(courseStr);*/
        
        return 10.0;
    }
}
