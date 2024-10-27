import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("test");

        String monedaOrigen;
        String monedaDestino;
        float valorAConvertir;

        Float tasaDeConversion;
        Float valorConvertido;

        Scanner scanner = new Scanner(System.in);

        // menú principal
        // EUR, BRL, ARS, USD, AUD
        System.out.println("""
                ===*===*===*===*===*===*===*===*
                Este conversor funcinona con:
                - ARS (pesos argentinos)
                - BRL (reales brasileros)
                - EUR (euros)
                - USD (dólares estadounidenses)
                - AUD (dólares australianos)
                ===*===*===*===*===*===*===*===*
                """);

        // se selecciona desde qué moneda se quiere convertir
        System.out.println("""
                ¿Desde qué moneda quiere convertir? (Escriba su código):
                """);
        monedaOrigen = scanner.nextLine();
        // FALTA CHEQUEAR QUE SEA CORRECTO

        // se selecciona a qué moneda se quiere convertir
        System.out.println("""
                ¿A qué moneda quiere convertir? (Escriba su código):
                """);
        monedaDestino = scanner.nextLine();
        // FALTA CHEQUEAR QUE SEA CORRECTO


        // se indica el valor que se quiere convertir
        System.out.println("""
                ¿Qué valor quiere convertir?:
                """);

        /*try {
            valorAConvertir = Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida, por favor ingrese un número.");
        }*/
        valorAConvertir = Float.parseFloat(scanner.nextLine());



        // acá se buscan los datos en la api
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://v6.exchangerate-api.com/v6/a75de60f08642d528e97876a/latest/" + monedaOrigen;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        ExchangeRatesResponse conversorDeOrigen = gson.fromJson(response.body(), ExchangeRatesResponse.class);
        ConversorDeMonedas conversor = new ConversorDeMonedas(conversorDeOrigen, monedaDestino);

        System.out.println(conversor.convertir(valorAConvertir));
    }
}
