package EjerciciosPSP1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class claseServidor {
    
    public static void main(String[] args) {
        int puerto = 12345;
        

        try (ServerSocket servidor = new ServerSocket(puerto)){

            System.out.println("Servidor listo en el puerto 12345...");

            while (true) { 
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado");

                hiloClientes hc = new hiloClientes(cliente);
                Thread hilo = new Thread(hc);
                hilo.start();
                
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    class hiloClientes implements Runnable{
        private Socket cliente;

        public hiloClientes(Socket cliente){
            this.cliente = cliente;
        }

        public void run(){

          try {
              InputStream entrada = cliente.getInputStream();
              OutputStream salida = cliente.getOutputStream();

              String ruta = "C:\\sofi\\DAM2\\PSP\\ServicioCliente\\";
              byte[] buffer = new byte[1000];
              int byteLeidos;

              byteLeidos = entrada.read(buffer);
              String numDoc = new String(buffer,0,byteLeidos).trim();
              System.out.println("Cliente solicita el archivo numero: "+numDoc+ ".txt");

              String nombreDoc = "archivo" + numDoc + ".txt";
              File archivo = new File(ruta + nombreDoc);

              if(archivo.exists()){
                try (FileInputStream fis = new FileInputStream(archivo)) {   
                    while ((byteLeidos = fis.read(buffer)) != -1) {
                        salida.write(buffer, 0, byteLeidos); 
                    }
                    salida.flush();
                    System.out.println("Archivo enviado exitosamente al cliente.");
                } catch (IOException ex) {
                  }
            } else {
                String mensajeError = "ERROR: El archivo no existe.";
                salida.write(mensajeError.getBytes());
                salida.flush();
                System.out.println("El archivo solicitado no existe.");
            }
              }
              catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    cliente.close();
                    System.out.println("Cliente desconectado.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                 
            }
        }
        }
    


