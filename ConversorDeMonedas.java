public class ConversorDeMonedas {
    String monedaOrigen;
    String monedaDestino;
    Float tasaDeConversion;

    ConversorDeMonedas(String monedaOrigen, String monedaDestino, Float tasaDeConversion) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.tasaDeConversion = tasaDeConversion;
    }

    ConversorDeMonedas(ExchangeRatesResponse APIResponse, String monedaDestino) {
        this.monedaOrigen = APIResponse.baseCode;
        this.monedaDestino = monedaDestino;
        this.tasaDeConversion = APIResponse.conversionRates.get(monedaDestino);
    }

    Float convertir(Float cantidad){
        return cantidad * tasaDeConversion;
    }
}
