package EjerciciosPSP1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class claseCliente {
    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 12345;

        try {
            Socket socket = new Socket(servidor,puerto);
            InputStream entrada = socket.getInputStream();
            OutputStream salida = socket.getOutputStream();

            Scanner sc = new Scanner(System.in);
            System.out.println("Numero de documento:");
            String numDoc = sc.nextLine();
            salida.write(numDoc.getBytes());
            salida.flush();

            byte[] buffer = new byte [1000];
            int byteLeidos;
            while((byteLeidos = entrada.read(buffer)) != -1){
                System.out.write(buffer, 0 ,byteLeidos);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
