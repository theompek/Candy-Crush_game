package gr.auth.ee.dsproject.crush.player;

import gr.auth.ee.dsproject.crush.CrushUtilities;
import gr.auth.ee.dsproject.crush.board.Board;

import java.util.ArrayList;

public class HeuristicPlayer implements AbstractPlayer
{
  // TODO Fill the class code.
  public static final int DEPTH_TWO=2;
 
  int score;
  int id;
  String name;

  public HeuristicPlayer (Integer pid)
  {
    id = pid;
    score = 0;
  }

  @Override
  public String getName ()
  {

    return "evaluation";

  }

  @Override
  public int getId ()
  {
    return id;
  }

  @Override
  public void setScore (int score)
  {
    this.score = score;
  }

  @Override
  public int getScore ()
  {
    return score;
  }

  @Override
  public void setId (int id)
  {

    this.id = id;

  }

  @Override
  public void setName (String name)
  {

    this.name = name;

  }

  @Override
  public int[] getNextMove (ArrayList<int[]> availableMoves, Board board)
  {
    int depthTwo=DEPTH_TWO; //Μετρητής για να πάμε σε συνολικό βάθος 2 και να ελέγξουμε τις 
					//δυνατές κινήσεις του αντιπάλου μετά απο την δικιά μας κίνηση
					
    int[] move = availableMoves.get(findBestMoveIndex(availableMoves, board,depthTwo));

    return calculateNextMove(move);

  }

  int[] calculateNextMove (int[] move)
  {

    int[] returnedMove = new int[4];

    if (move[2] == CrushUtilities.UP) {
      // System.out.println("UP");
      returnedMove[0] = move[0];
      returnedMove[1] = move[1];
      returnedMove[2] = move[0];
      returnedMove[3] = move[1] + 1;
    }
    if (move[2] == CrushUtilities.DOWN) {
      // System.out.println("DOWN");
      returnedMove[0] = move[0];
      returnedMove[1] = move[1];
      returnedMove[2] = move[0];
      returnedMove[3] = move[1] - 1;
    }
    if (move[2] == CrushUtilities.LEFT) {
      // System.out.println("LEFT");
      returnedMove[0] = move[0];
      returnedMove[1] = move[1];
      returnedMove[2] = move[0] - 1;
      returnedMove[3] = move[1];
    }
    if (move[2] == CrushUtilities.RIGHT) {
      // System.out.println("RIGHT");
      returnedMove[0] = move[0];
      returnedMove[1] = move[1];
      returnedMove[2] = move[0] + 1;
      returnedMove[3] = move[1];
    }
    return returnedMove;
  }
  
  int findBestMoveIndex (ArrayList<int[]> availableMoves, Board board,int depthTwo)
  {	
     double bestMove=0;
	 double eval=0;
	 int bestTile=0;
	 int bestHeight=CrushUtilities.NUMBER_OF_PLAYABLE_ROWS;
	 int ArrayListSize=availableMoves.size();
	 
	 depthTwo--;
	 
	 //Loop μέσα στο οποίο καλόυμε την συνάρτηση moveEvaluation και
	 //βρίσκουμε την καλύτερη κίνηση απο το σύνολο των δυνατών κινήσεων
    for(int i=1;i<ArrayListSize;i++)
	{
		eval=moveEvaluation(availableMoves.get(i),board,depthTwo);
		
		if(eval>bestMove)
		{
			bestTile=i; //Το καλύτερο πλακίδιο
			bestMove=eval; //με το καλύτερο σκορ
			bestHeight=(availableMoves.get(i))[1]; //το ύψος του(συντεταγμένη y)
			
		//Αν έχουμε κινήσεις οι οποίες έχουν την ίδια αξιολόγηγη θα επιλέξουμε αυτή που βρίσκεται 
	    //πιο χαμηλά στο ταμλπό ώστε συνυπολογίζοντας και με την κίνηση του αντιπάλου να αλλάξει όσο 
		//γίνεται περισσότερο το ταμπλό και να δημιουργήσει περισσότερες ευκαιρίες για την επόμενη κίνηση μας
		}else if((eval==bestMove)&&(( availableMoves.get(i))[1] > bestHeight ))
			{
			 bestTile=i;
			 bestHeight=(availableMoves.get(i))[1];
			 
			}
	}
	 depthTwo=1;//Επαναφέρουμε τον δείκτη για τον έλεγχο των επόμενων κινήσεων μας
	  return (bestTile);
  }

  double moveEvaluation (int[] move, Board board,int depthTwo)
  {
	double MyEarnedPoints=0;
	double OpponentEarnedPoints=0;
	
	//Δημιουργία ενός αντιγράφου του ταμπλό
	Board clone=CrushUtilities.boardAfterFirstMove(board,move);	
	
	MyEarnedPoints=calculateEarnedPoints(move,clone);
	ArrayList<int[]> availableMoves=clone.checkForTriples(clone);
	
	if(depthTwo>0){ //Συνυπολογίζουμε και την καλύτερη επόμενη κίνηση του αντιπάλου
	OpponentEarnedPoints=(double) findBestMoveIndex(availableMoves,clone,depthTwo);
	}
	

	return (MyEarnedPoints-OpponentEarnedPoints);
  }

double calculateEarnedPoints(int[] move,Board board){
	
	int numOfTileToDelete=0;
	double earnedPoints=0;
	
			
	while(true){
	    numOfTileToDelete=board.findCreatedNples(board);//Μετράμε τον αριθμό των δυνατών συνδιασμών
		if(numOfTileToDelete==0){ break;}				//μέρχι να μήν υπάρχουν άλλοι συνδιασμοί
		earnedPoints+=(double) numOfTileToDelete; 		//Προσθέτουμε τον αριθμό των συνδιασμών που μετρήσαμε
	    board.removeMarkedTiles(board); 				//και διαγράφουμε τους συνδιασμούς
	   }												//Επαναλαμβάνουμε την διαδικασία για να δούμε αν δημιουργήθηκαν νέοι συνδιασμοί

	return (earnedPoints);
	
}
  
  
}
