import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HoraServer {

    public static void main(String[] args) {
        
        try (DatagramSocket serverSocket = new DatagramSocket(5555)) {  // try-with-resources para cerrar el socket automáticamente
            System.out.println("Servidor UDP listo en el puerto 5555...");
            
            while (true) { 
                byte[] buffer = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

                System.out.println("Esperando mensaje del cliente...");
                serverSocket.receive(receivePacket);

                String mensaje = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Mensaje recibido: " + mensaje);

                String respuesta = "";
                if (mensaje.equalsIgnoreCase("hora")) {
                    respuesta = java.time.LocalDateTime.now().toString();
                } else {
                    respuesta = "Mensaje no reconocido";
                }

                byte[] respuestaBytes = respuesta.getBytes();
                InetAddress clientInetAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(respuestaBytes, respuestaBytes.length, clientInetAddress, clientPort);
                serverSocket.send(sendPacket);
                System.out.println("Respuesta enviada: " + respuesta);
            }
            
        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
