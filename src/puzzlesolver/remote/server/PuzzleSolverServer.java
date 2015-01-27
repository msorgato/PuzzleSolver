package puzzlesolver.remote.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PuzzleSolverServer {

	public static void main(String[] args) {
		ConcreteRemoteSolver solver = null;
		try {
			solver = new ConcreteRemoteSolver();
		} catch (RemoteException e) {
			System.out.println("Non crea il solver remoto.");
			e.printStackTrace();
		}
			try {
				Naming.rebind("Razorback", solver);
			} catch (RemoteException e) {
				System.out.println("Lancia RemoteException");
				e.printStackTrace();
			} catch (MalformedURLException e) {
				System.out.println("Lancia MalformedURLException");
				e.printStackTrace();
			}
		System.out.println("Server Partito!");

	}

}
