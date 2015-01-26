package remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import puzzlesolver.piece.Piece;
import remote.RemoteSolver;

public class ConcreteRemoteSolver extends UnicastRemoteObject implements RemoteSolver {

	protected ConcreteRemoteSolver() throws RemoteException { }

	@Override
	public Piece[][] sortPuzzle(List<Piece> puzzle) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
