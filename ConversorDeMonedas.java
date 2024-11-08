import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorDeMonedas {
    String monedaOrigen;
    String monedaDestino;
    Float tasaDeConversion;

    private static final String API_BASE_URL = "https://v6.exchangerate-api.com/v6/a75de60f08642d528e97876a/latest/";

    ConversorDeMonedas(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + monedaOrigen))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        ExchangeRatesResponse conversorDeOrigen = gson.fromJson(response.body(), ExchangeRatesResponse.class);

        this.monedaOrigen = conversorDeOrigen.baseCode;
        this.monedaDestino = monedaDestino;
        this.tasaDeConversion = conversorDeOrigen.conversionRates.get(monedaDestino);
    }

    Float convertir(Float monto){
        return monto * tasaDeConversion;
    }
}
