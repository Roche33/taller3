import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Simulacion{

    static Queue<Cliente>[] cola;
    static boolean[] ocupada;
    static int[] atendidos;
    static int[] encola;
    static int[] espera;
    static int[] ultimo;
    static Cliente cliente_arribando;
    static Cliente[] cliente_atendiendo;
    static int[] salidas;

    public static void main(String[] args) {
        cola = new Queue[3];
        for(int i = 0; i < 3; i++){
            cola[i] = new LinkedList<>();
        }

        ocupada = new boolean[3];
        atendidos = new int[3];
        atendidos = new int[3];
        encola = new int[3];
        espera = new int[3];
        ultimo = new int[3];
        cliente_atendiendo = new Cliente[3];
        salidas = new int[3];

        Random r = new Random();

  
        int llegada = r.nextInt(2) + 3;// 0+3 o 1+3 representa la llegada en 3 o 4 minutos
        cliente_arribando = new Cliente(r.nextInt(60000) + 1, llegada);

        System.out.println("Probabilidad de llegada del primer cliente a las:  "+ reloj(llegada));

        for (int minuto = 0; minuto <= 720; minuto++) {
            System.out.println(reloj(minuto));
            if (llegada == minuto) {
                System.out.println("Llegando el cliente " + cliente_arribando);
                int colaMasCorta = encontrarColaMasCorta();
                if (cola[colaMasCorta].size() < 10) {
                    cola[colaMasCorta].offer(cliente_arribando);
                    System.out.println("El cliente " + cliente_arribando + " se forma en la cola " + (colaMasCorta + 1));
                } else {
                    System.out.println("El cliente " + cliente_arribando + " se retira del lugar");
                }
                llegada = minuto + r.nextInt(2) + 2; // 2 o 3 minutos
                cliente_arribando = new Cliente(r.nextInt(60000) + 1, llegada);
                System.out.println("Probabilidad de llegada de un nuevo cliente a las: " + reloj(llegada));
            }
            for (int i = 0; i < 3; i++) {
                if (minuto == salidas[i]) {
                    System.out.println("Se termina la atención del cliente " + cliente_atendiendo[i]);
                    ocupada[i] = false;
                    atendidos[i]++;
                    if (!cola[i].isEmpty()) {
                        cliente_atendiendo[i] = cola[i].poll();
                        System.out.println("Se manda llamar a un cliente de la cola " + (i + 1) + " … " + cliente_atendiendo[i]);
                        int min_arribo = cliente_atendiendo[i].getArribo();
                        espera[i] += (minuto - min_arribo);
                        if ((minuto - min_arribo) != 0) encola[i]++;
                        ocupada[i] = true;
                        salidas[i] = minuto + r.nextInt(6) + 5; // 5 a 11 minutos
                        System.out.println("Atendiendo al cliente " + cliente_atendiendo[i] + " que estuvo formado " + (minuto - min_arribo) + " minutos y saldra en " + (salidas[i] - minuto) + " minutos");
                        ultimo[i] = minuto - min_arribo;
                    }
                }
                if (!ocupada[i] && !cola[i].isEmpty()) {
                    cliente_atendiendo[i] = cola[i].poll();
                    System.out.println("Se manda llamar a un cliente de la cola " + (i + 1) + " … " + cliente_atendiendo[i]);
                    int min_arribo = cliente_atendiendo[i].getArribo();
                    espera[i] += (minuto - min_arribo);
                    if ((minuto - min_arribo) != 0) encola[i]++;
                    ocupada[i] = true;
                    salidas[i] = minuto + r.nextInt(6) + 5; // 5 a 11 minutos
                    System.out.println("Atendiendo al cliente " + cliente_atendiendo[i] + " que estuvo formado " + (minuto - min_arribo) + " minutos y saldra en " + (salidas[i] - minuto) + " minutos");
                    ultimo[i] = minuto - min_arribo;
                }
            }
        }
        System.out.println("\n\n");
        System.out.println("Hora de atención de 8:00 a 20:00");
        for (int i = 0; i < 3; i++) {
            System.out.println("Cantidad de clientes atendidos en la caja " + (i + 1) + ": " + atendidos[i]);
            System.out.printf("Promedio de espera en la cola " + (i + 1) + ": %.2f minutos\n", (1.0 * espera[i] / encola[i]));
        }
        System.out.println("Cantidad de clientes que se retiraron: " + (cliente_arribando.getId() - (atendidos[0] + atendidos[1] + atendidos[2])));
    }


    static int encontrarColaMasCorta() {
        int colaMasCorta = 0;
        int tamanoMinimo = cola[0].size();
        for (int i = 1; i < 3; i++) {
            if (cola[i].size() < tamanoMinimo) {
                tamanoMinimo = cola[i].size();
                colaMasCorta = i;
            }
        }
        return colaMasCorta;
    }
    
    static String reloj(int min) {
        return (8 + (min / 60)) + ":" + ((min % 60 <= 9) ? "0" : "") + (min % 60);
    }
}
