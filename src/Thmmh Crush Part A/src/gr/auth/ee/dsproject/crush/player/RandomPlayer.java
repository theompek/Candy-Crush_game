package gr.auth.ee.dsproject.crush.player;

import gr.auth.ee.dsproject.crush.CrushUtilities;
import gr.auth.ee.dsproject.crush.board.Board;
import java.util.ArrayList;

public class RandomPlayer implements AbstractPlayer
{
  private int id;
  private String name;
  private int score;
  
  //Constructor
 public RandomPlayer(Integer pid){
	 this.id=pid;
	 this.name="Unknown";
	 this.score=0;
 }

 //Constructor
 public RandomPlayer(Integer pid,String name ,int score){
	 this.id=pid;
	 this.name=name;
	 this.score=score;
	 
 }
  
  
  // TODO
  public int[] getNextMove(ArrayList<int[]> availableMoves, Board board)
  {
		
   int[] arrayMoves; //Αποθήκευση των αποτελεσμάτων της getRandomMove
   int index; 
   int x2=0;
   int y2=0;
   
   //Παράγεται τυχαία μία τιμή στο διάστημα [0,μήκος λίστας]
   index=(int)(Math.random()*availableMoves.size());
   
   //την οποία χρησιμοποιούμε για να πάρουμε το αντίστοιχο στοιχείο απο την λίστα
   arrayMoves=CrushUtilities.getRandomMove(availableMoves,index);
   
   //Αφού πάρουμε τις τιμές x y για την θέση του στοιχείου στο ταμπλό
   //ελέγχουμε την κατεύθυνση κατα την οποία θα κινηθεί και δημιουργούμε
   //τις μεταβλητές x2 y2 που περιέχουν την νέα θέση
	switch(arrayMoves[2])
	{
	case 0:
		x2=arrayMoves[0]-1;
		y2=arrayMoves[1];
		break;
	case 1:
		x2=arrayMoves[0];
		y2=arrayMoves[1]-1;
		break;
	case 2:
		x2=arrayMoves[0]+1;
		y2=arrayMoves[1];
		break;
	case 3:
		x2=arrayMoves[0];
		y2=arrayMoves[1]+1;
		break;
	default:
		break;
	}
	
   //Δημιουργούμε πίνακα για την επιστροφή των στοιχείων
	int[] returnMatrix=new int[4];
	
	returnMatrix[0]=arrayMoves[0];
	returnMatrix[1]=arrayMoves[1];
	returnMatrix[2]=x2;
	returnMatrix[3]=y2;
	
   //Δεδομένου οτι η θέση του στοιχείου που πέρνουμε απο την λίστα
   //και η κατεύθηνση κίνησης είναι καλά ορισμένες δεν χρειάζεται
   //έλεγχος για τα επιστρεφόμενα δεδομένα(θα είναι εντός ορίων)
	
	return returnMatrix;
  }


  
//Setters & Getters 
  
public void setId(int id) {
	this.id=id;
	
}


public int getId() {
	return this.id;
}


public void setName(String name) {
	this.name=name;
	
}


public String getName() {
	
	return this.name;
}


public void setScore(int score) {
	
	this.score=score;
}


public int getScore() {
	
	return this.score;
}

}