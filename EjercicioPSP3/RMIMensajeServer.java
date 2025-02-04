import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIMensajeServer implements RMIMSJInterfaz {

    @Override
    public String mensaje(String msj) throws RemoteException {
        
        System.out.println(" Objeto remoto: enviando mensaje ...");
        return msj;
    
    }

    public static void main(String[] args) {
        System.out.println("Creando el registro de objetos remotos...");

        Registry reg = null;
        try {
            reg = LocateRegistry.createRegistry(5555);
            System.out.println("Registro de objetos remotos creado en el puerto 5555");
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido crear el registro");
            e.printStackTrace();
            return;
        }
        System.out.println("Creando el objeto servidor e inscribiéndolo en el registro...");

        RMIMensajeServer serverObject = new RMIMensajeServer();

        try {
            RMIMSJInterfaz stub = (RMIMSJInterfaz) UnicastRemoteObject.exportObject(serverObject, 0);

            reg.rebind("Mensajito de texto", stub);
            System.out.println("Servidor RMI listo y registrado con el nombre de 'Mensajito de texto'");

        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido inscribir el objeto servidor");
            e.printStackTrace();
        }
    }
    
}
