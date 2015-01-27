package remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import puzzlesolver.piece.Piece;
import puzzlesolver.sort.ConcurrentSort;
import puzzlesolver.sort.ISort;
import remote.RemoteSolver;

public class ConcreteRemoteSolver extends UnicastRemoteObject implements RemoteSolver {

	protected ConcreteRemoteSolver() throws RemoteException { }

	@Override
	public Piece[][] sortPuzzle(List<Piece> puzzle) throws RemoteException {
		ISort sorter = new ConcurrentSort();
		return sorter.sortPuzzle(puzzle);
	}

}
