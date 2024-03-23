public class Clase_Depositar {
    private double saldo;

    public Clase_Depositar(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public double consultarSaldo() {
        return saldo;
    }

    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Depósito realizado correctamente. Nuevo saldo: " + saldo);
        } else {
            System.out.println("Error: La cantidad a depositar debe ser mayor que cero.");
        }
    }

    public static void main(String[] args) {
        Clase_Depositar cajero = new Clase_Depositar(1000);
        System.out.println("Saldo inicial: " + cajero.consultarSaldo());

        cajero.depositar(500); // Depositar 500
        System.out.println("Nuevo saldo después del depósito: " + cajero.consultarSaldo());
    }
}






























    }

}
