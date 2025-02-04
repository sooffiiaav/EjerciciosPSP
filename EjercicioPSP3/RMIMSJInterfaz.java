
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIMSJInterfaz extends Remote{

    public String mensaje(String msj) throws RemoteException;
}