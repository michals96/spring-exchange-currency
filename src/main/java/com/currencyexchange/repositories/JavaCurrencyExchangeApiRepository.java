package com.currencyexchange.repositories;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;

@Repository
@Component("javaCurrencyExchangeApiRepository")
public class JavaCurrencyExchangeApiRepository implements CurrencyExchangeRepository{

    private HttpClient client;

    public JavaCurrencyExchangeApiRepository(){
        this.client = HttpClient.newHttpClient();
    }

    public String buildQuery(String sourceCurrency, String targetCurrency){
        return "https://api.exchangeratesapi.io/latest?base=" + sourceCurrency + "&symbols=" + targetCurrency;
    }

    @Override
    public NumberValue calculate(String sourceCurrency, String targetCurrency){
        try
        {
            java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(buildQuery(sourceCurrency, targetCurrency)))
                    .build();

            this.client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            this.client = null;

            String jsonString = response.body();
            JSONObject obj = new JSONObject(jsonString);

            String courseStr = obj.getJSONObject("rates").getString(targetCurrency);

            MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setNumber(Double.parseDouble(courseStr)).setCurrency(targetCurrency).create();

            return monetaryAmount.getNumber();
        }
        catch (IOException | InterruptedException | IllegalArgumentException | JSONException e )
        {
            e.printStackTrace();
        }

        return null;
    }
}
