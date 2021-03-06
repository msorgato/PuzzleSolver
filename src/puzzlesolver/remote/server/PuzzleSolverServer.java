package puzzlesolver.remote.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PuzzleSolverServer {

	public static void main(String[] args) {
		//controlli degli argomenti di invocazione del main di PuzzleSolverServer
		if(args.length == 0) {
			System.out.println("Manca l'argomento di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolverServer "
					+ "nome_del_server");
			return;
		}
		if(args.length > 1) {
			System.out.println("Sono presenti troppi argomenti di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolverServer "
					+ "nome_del_server");
			return;
		}
		if(args[0].isEmpty()) {
			System.out.println("Il nome del server non deve essere vuoto.");
			return;
		}		
		
		//creazione dell'istanza di ConcreteRemoteSolver
		ConcreteRemoteSolver solver = null;
		try {
			solver = new ConcreteRemoteSolver();
		} catch (RemoteException e) {
			System.out.println("Non e' stato possibile creare il risolutore remoto.");
			e.printStackTrace();
		}
		
		if(solver == null)
			return;
		
		//pubblicazione sul registro rmi del riferimento all'oggetto remoto del Server
		try {
			Naming.rebind(args[0], solver);
		} catch (RemoteException e) {
			System.out.println("Si sono riscontrati problemi nella pubblicazione del riferimento remoto.");
			return;
		} catch (MalformedURLException e) {
			System.out.println("L'URL specificata nell'argomento di invocazione non e' nel formato corretto.");
			return;
		}
		
		//stampa di conferma della pubblicazione del riferimento del Server
		System.out.println("Server Partito!");

	}

}
