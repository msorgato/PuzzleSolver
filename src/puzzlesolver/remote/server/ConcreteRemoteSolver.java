package puzzlesolver.remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import puzzlesolver.piece.Piece;
import puzzlesolver.sort.ConcurrentSort;
import puzzlesolver.sort.ISort;
import puzzlesolver.remote.RemoteSolver;

public class ConcreteRemoteSolver extends UnicastRemoteObject implements RemoteSolver {
	
	//costruttore vuoto con possibile lancio di RemoteException
	protected ConcreteRemoteSolver() throws RemoteException { }

	public Piece[][] sortPuzzle(List<Piece> puzzle) throws RemoteException {
		/*
		 * per ogni client che richiede l'esecuzione dell'ordinamento di un puzzle,
		 * viene creata una nuova istanza di ConcurrentSort per gestire la richiesta.
		 * Creando una nuova istanza per ogni richiesta, tutti i client vengono 
		 * serviti contemporaneamente senza rischio di race condition.
		 */
		ISort sorter = new ConcurrentSort();	
		return sorter.sortPuzzle(puzzle);
	}

}
