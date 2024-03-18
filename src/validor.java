import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validor {

    private static final int NUM_HILOS = 3;
    private static final Pattern[] PATRONES = {
            Pattern.compile("^(?=.*[!@#$%^&*(),.?:{}|<>]).*$"), // Caracteres especiales
            Pattern.compile("^(?=.*[A-Z].*[A-Z]).*$"), // Al menos dos letras mayúsculas
            Pattern.compile("^(?=.*[a-z].*[a-z].*[a-z]).*$"), // Al menos tres letras minúsculas
            Pattern.compile("^(?=.*\\d).*$"), // Al menos un número
            Pattern.compile(".{8,}") // Longitud mínima de ocho caracteres
    };

    private static final String ARCHIVO_REGISTRO = "registro.txt";

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_HILOS);
        Scanner scanner = new Scanner(System.in);
        System.out.println("BIENVENIDO USUARIO");
        System.out.println("Ingrese su contraseña:");

        String contrasena = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_REGISTRO, true))) {
            for (Pattern patron : PATRONES) {
                executor.execute(() -> {
                    boolean resultado = validarContraseña(patron, contrasena);
                    if (!resultado) {
                        String mensaje = "La contraseña no cumple con el requisito: " + patron.pattern() + "\n";
                        try {
                            writer.write(mensaje);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    private static boolean validarContraseña(Pattern patron, String contraseña) {
        Matcher matcher = patron.matcher(contraseña);
        return matcher.matches();
    }
}