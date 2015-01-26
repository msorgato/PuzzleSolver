package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import puzzlesolver.piece.Piece;

public interface RemoteSolver extends Remote {
	
	Piece[][] sortPuzzle(List<Piece> puzzle) throws RemoteException;
	
}
