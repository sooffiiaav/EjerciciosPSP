package EjerciciosPSP2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClaseCliente {
    
    public static void main(String[] args) {
        String servidor = "localhost"; 
        int puerto = 12345;

        try (DatagramSocket socket = new DatagramSocket()) {
            String mensaje = "Identifícame";
            byte[] bufferEnvio = mensaje.getBytes();

            InetAddress direccionServidor = InetAddress.getByName(servidor);
            DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, direccionServidor, puerto);
            socket.send(paqueteEnvio); 
            
            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
            socket.receive(paqueteRespuesta); 

            String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
            System.out.println("Respuesta del servidor: " + respuesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
