import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class contrasena{

    private static final int NUM_HILOS = 3;
    private static final Pattern[] PATRONES = {
            Pattern.compile("^(?=.*[!@#$%^&*(),.?:{}|<>]).*$"), // Caracteres especiales
            Pattern.compile("^(?=.*[A-Z].*[A-Z]).*$"), // Al menos dos letras mayúsculas
            Pattern.compile("^(?=.*[a-z].*[a-z].*[a-z]).*$"), // Al menos tres letras minúsculas
            Pattern.compile("^(?=.*\\d).*$"), // Al menos un número
            Pattern.compile(".{8,}") // Longitud mínima de ocho caracteres

    };

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_HILOS);
        Scanner scanner = new Scanner(System.in);
        System.out.println("BIENVENIDO USUARIO");
        System.out.println("Ingrese su contraseña:");

        String contrasena = scanner.nextLine();

        for (Pattern patron : PATRONES) {
            executor.execute(new Validador(patron, contrasena));
        }

        executor.shutdown();
    }

    private static class Validador implements Runnable {
        private final Pattern patron;
        private final String contrasena;

        public Validador(Pattern patron, String contrasena) {
            this.patron = patron;
            this.contrasena = contrasena;
        }

        @Override
        public void run() {
            Matcher matcher = patron.matcher(contrasena);
            if (!matcher.matches()) {
                System.out.println("La contraseña no cumple con el requisito: " + patron.pattern());
                System.out.println("Por favor intentelo de nuevo--");
            }
        }
    }
}