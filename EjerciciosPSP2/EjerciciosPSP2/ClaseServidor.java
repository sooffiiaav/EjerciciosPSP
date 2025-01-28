package EjerciciosPSP2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClaseServidor {
    public static void main(String[] args) {
        int puerto = 12345;

        System.out.println("Servidor de identificación IP iniciado en el puerto " + puerto);

        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecibido);

                Thread hiloCliente = new Thread(() -> {
                    try {
                        manejarCliente(paqueteRecibido, socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                hiloCliente.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void manejarCliente(DatagramPacket paqueteRecibido, DatagramSocket socket) {
        try {
            InetAddress direccionCliente = paqueteRecibido.getAddress();
            int puertoCliente = paqueteRecibido.getPort();

            String mensajeRespuesta = "Tu dirección IP es: " + direccionCliente.getHostAddress();
            byte[] respuesta = mensajeRespuesta.getBytes();

            DatagramPacket paqueteRespuesta = new DatagramPacket(respuesta, respuesta.length, direccionCliente, puertoCliente);
            socket.send(paqueteRespuesta);

            System.out.println("Respondido a cliente: " + direccionCliente.getHostAddress());
        } catch (Exception e) {
            System.out.println("Error al manejar cliente: " + e.getMessage());
        }
    }
}
