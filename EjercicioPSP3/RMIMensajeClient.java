
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;



public class RMIMensajeClient {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RMIMSJInterfaz  msj = null;

        try {
            System.out.println("Localizando registro de objetos remotos");
            Registry registry = LocateRegistry.getRegistry("localhost",5555);
            
            System.out.println("Obteniendo el stub del objeto remoto...");
            msj = (RMIMSJInterfaz) registry.lookup("Mensajito de texto");

        } catch (Exception e) {
            System.out.println("ERROR: No se pudo concetar con el servidor RMI");
            e.printStackTrace();
        }

        if(msj != null){
            System.out.println("Realizando operaciones de enviar mensaje con el objeto remoto...");
            System.out.println("Escriba el mensaje:");
            String mensaje = sc.nextLine();

            try {
                System.out.println("El mensaje es: "+msj.mensaje(mensaje));
            } catch (Exception e) {
                System.out.println("ERROR: Fallo en la ejecucion del mensaje");
                e.printStackTrace();
            }
            System.out.println("Terminado");
        }else{
            System.out.println("ERROR: No se pudo obtener la referencia del objeto remoto");
        }
    }
}
