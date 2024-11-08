import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static Set<String> CURRENCIES_SET = new HashSet<>(Set.of("EUR", "BRL", "ARS", "USD", "AUD"));

    public static void main(String[] args) {

        new Main().run();

    }

    private void run(){

        Scanner scanner = new Scanner(System.in);

        boolean quiereSalirDeAplicacion = false;
        while (!quiereSalirDeAplicacion) {

            System.out.println("""
                    ===*===*===*===*===*===*===*===*===
                    Conversor de monedas - Menú principal
                    1) Acceder a la aplicación
                    2) Salir
                    ===*===*===*===*===*===*===*===*===
                    Ingrese 1 o 2.
                    """);
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    ejecutarConversor(scanner);
                    break;
                case "2":
                    System.out.println("¡Suerte con todo!");
                    quiereSalirDeAplicacion = true;
                    break;
                default:
                    System.out.println("!!! Opción inválida, intente nuevamente.");
            }
        }
    }

    private void ejecutarConversor(Scanner scanner) {

        System.out.println("""
                    ===*===*===*===*===*===*===*===*===
                    Este conversor funcinona con:
                    - ARS (pesos argentinos)
                    - BRL (reales brasileros)
                    - EUR (euros)
                    - USD (dólares estadounidenses)
                    - AUD (dólares australianos)
                    ===*===*===*===*===*===*===*===*===
                    """);

        String monedaOrigen = obtenerMonedaOrigen(scanner);
        String monedaDestino = obtenerMonedaDestino(scanner, monedaOrigen);

        try {
            ConversorDeMonedas conversor = new ConversorDeMonedas(monedaOrigen, monedaDestino);
            realizarConversion(scanner, conversor, monedaOrigen, monedaDestino);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error al intentar acceder a la API. Intente más tarde.");
        }
    }

    private String obtenerMonedaOrigen(Scanner scanner) {
        String moneda;
        do {
            System.out.println("¿Desde qué moneda quiere convertir? (Escriba su código):");
            moneda = scanner.nextLine().trim();
            if (!CURRENCIES_SET.contains(moneda)) {
                System.out.println("!!! La moneda ingresada no es válida. Por favor, ingrese un código válido de la lista.");
            }
        } while (!CURRENCIES_SET.contains(moneda));
        return moneda;
    }

    private String obtenerMonedaDestino(Scanner scanner, String monedaOrigen) {
        String moneda;
        do {
            System.out.println("¿A qué moneda quiere convertir? (Escriba su código):");
            moneda = scanner.nextLine().trim();
            if (!CURRENCIES_SET.contains(moneda) || moneda.equals(monedaOrigen)) {
                System.out.println("!!! Moneda inválida. No puede ser la misma que la moneda de origen.");
            }
        } while (!CURRENCIES_SET.contains(moneda) || moneda.equals(monedaOrigen));
        return moneda;
    }

    private void realizarConversion(Scanner scanner, ConversorDeMonedas conversor, String monedaOrigen, String monedaDestino) {
        String valorAConvertir;
        while (true) {
            System.out.println("Ingrese un valor para convertir o ':menu' para volver al menú principal.");
            valorAConvertir = scanner.nextLine().trim();
            if (valorAConvertir.equals(":menu")) break;

            if (esNumeroValido(valorAConvertir)) {
                Float valorConvertido = conversor.convertir(Float.parseFloat(valorAConvertir));
                System.out.printf("""
                        ===*===*===*===*===*===*===*===*===*===
                        El valor de %f (%s) equivale a %f (%s)
                        ===*===*===*===*===*===*===*===*===*===
                        """, Float.parseFloat(valorAConvertir), monedaOrigen, valorConvertido, monedaDestino);
            } else {
                System.out.println("!!! Entrada inválida. Intente nuevamente.");
            }
        }
    }

    // Método auxiliar para validar formato numérico
    private static boolean esNumeroValido(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
