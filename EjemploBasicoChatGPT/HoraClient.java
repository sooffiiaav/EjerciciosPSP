import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HoraClient {

    public static void main(String[] args) {
        
        try (DatagramSocket clientSocket = new DatagramSocket()) {  // try-with-resources para cerrar el socket automáticamente
            System.out.println("Cliente UDP iniciado...");

            // Cadena de texto que queremos enviar al servidor
            String mensaje = "hora";
            byte[] mensajeBytes = mensaje.getBytes();

            // Obtener la dirección de IP del servidor
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 5555;

            // Crear un paquete con los datos del mensaje, longitud, dirección IP y puerto
            DatagramPacket sendPacket = new DatagramPacket(mensajeBytes, mensajeBytes.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);
            System.out.println("Mensaje enviado: " + mensaje);

            // Almacenar la respuesta del servidor
            byte[] buffer = new byte[100];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            
            // Espera hasta recibir la respuesta del servidor
            clientSocket.receive(receivePacket);

            // Convertir los bytes en texto legible
            String respuesta = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Respuesta del servidor: " + respuesta);
            
        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
