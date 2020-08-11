package gr.auth.ee.dsproject.crush.node82008561;

import gr.auth.ee.dsproject.crush.board.Board;
import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.defplayers.AbstractPlayer;
import gr.auth.ee.dsproject.crush.node82008561.Node82008561;

import java.util.ArrayList;

public class Player82008561 implements AbstractPlayer
{
  int score;
  int id;
  String name;

  public Player82008561 (Integer pid)
  {
    id = pid;
    score = 0;
    
  }

  public String getName ()
  {

    return "8200_8561";

  }

  public int getId ()
  {
    return id;
  }

  public void setScore (int score)
  {
    this.score = score;
  }

  public int getScore ()
  {
    return score;
  }

  public void setId (int id)
  {
    this.id = id;
  }

  public void setName (String name)
  {
    this.name = name;
  }

public int[] getNextMove (ArrayList<int[]> availableMoves, Board board)
{
// TODO Fill the code
		
		
//Φτιάξε αντίγραφο του board
Board cloneBoard=CrushUtilities.cloneBoard(board);

//Δημιούργησε ενα node που αντιστοιχεί στην ρίζα
Node82008561 root=new Node82008561(cloneBoard);

//-------Ορίζουμε το μέγιστο βάθος του δέντρου-------
int MAX_DEPTH=3;

if(availableMoves.size()>8) 
{
	MAX_DEPTH=3;
}else if(availableMoves.size()>5) 
{
	MAX_DEPTH=4;
}else if(availableMoves.size()>1) 
{
	MAX_DEPTH=5;
}else if(availableMoves.size()==1) 
{
	MAX_DEPTH=6;
}
//---------------------------------------------------

//Καλεσαι την createMySubThree για την δημιουργία υποδέντρου
createSubTree(root,MAX_DEPTH);


//Κάλεσαι chooseMinMaxMove(Node root) για την επιλογή της καλύτερης κίνησης index
int indexBest = chooseMinMaxMove(root,MAX_DEPTH);


//Καλεσαι την calculateNextMove(index) για να επιστρέψει τις συντεταγμένες int[]
int[] bestMove = root.getChildren(indexBest).getNodeMove();

return CrushUtilities.calculateNextMove(bestMove);
    
}//Τέλος getNextMove


private void createSubTree (Node82008561 parent, int MaxDepth)
{
	
//Βρες τις επιτρεπτές κοινήσεις απο το board του πατέρα
ArrayList<int[]> availableMoves=CrushUtilities.getAvailableMoves(parent.getNodeBoard());

int listSize=availableMoves.size();

//==========Δημιουργούμε τους κόμβους παιδιά του τρεχον κόμβου=========

//Για κάθε κίνηση:
//Δημιούργησε κόμβο και
//υπολόγισε την συνατρηση αξιολόγησης
for(int i=0;i<listSize;i++)	
{
 //Δημιουργησε ένα node σαν παιδί του πατρικού node
 //και πρόσθεσαι το παιδί στο arrayOfChildren του πατέρα	
 parent.setChildren(new Node82008561(
                                      parent, //O γονέας του node-παιδιού
 									  parent.getNodeDepth()+1, //Το βάθος του node-παιδιού
 									  availableMoves.get(i), //Η κίνηση του αντιστοιχεί στο node-παιδί
   									  CrushUtilities.cloneBoard(parent.getNodeBoard()), //Αντίγραφο απο το ταμπλό του πατέρα
									  parent.getNodeEvaluation() //Δώσε στο παιδί την τιμή αξιολόγησης του πατέρα
									  )                          //για να την συνυπολογίσει στην δικιά του αξιολόγηγη
			        );
	
 //Υπολόγισαι την τιμή αξιολόγησης του παιδιού και την
 //νέα κατάσταση του ταμπλό για την κίνηση αυτή
parent.getChildren(i).moveEvaluation(getId());
}
//==============================

//===========Δημιουργία υποδέντρου για τα παιδιά=======

//Για την δικιά μου κίνηση ψάξε ολες τις περιπτώσεις
if((parent.getNodeDepth()&1) == 0){
	
for(int i=0;i<listSize;i++)	
{
 //Κάλεσαι την createSubTree() αν δεν έχουμε
 //φτάσει μέρχι τα φύλλα του δέντρου	
 if(((parent.getNodeDepth()+1)!=MaxDepth)&&(parent.getChildren(i).getNodeEvaluation()<500))
 {
  createSubTree(parent.getChildren(i),MaxDepth);//Δημιούργησε νέο υποδέντρο και υπολόγισε την αξιολόγηση των παιδιών
 }
}

}else{//Του αντιπάλου μόνο την καλύτερη του(χειροτερη γία εμένα δηλαδή getMin())

if(listSize!=0){
	
	int bestMove=0;
	double[] Array=new double[2];
	Array=getMin(parent.getChildren());
	 bestMove=(int) Array[1]; //Την καλύτερη κίνηση
	 
	 
	 
	//Κάλεσαι την createSubTree() αν δεν έχουμε
	 //φτάσει μέρχι τα φύλλα του δέντρου	
	 if(((parent.getNodeDepth()+1)!=MaxDepth)&&(parent.getChildren(bestMove).getNodeEvaluation()>-500))
	 {
	//Μόνο ομως για την καλύτερη του κίνηση	 
	 createSubTree(parent.getChildren(bestMove),MaxDepth);
	 }
 }

}
//=====================================================

}//Τελος createSubTree


  private int chooseMinMaxMove (Node82008561 root,int MaxDepth)
  {
    
	return( getNode(root,MaxDepth) );
	  
  }

//Η συνάρτηση εκτελεί τα min-max  
 int getNode(Node82008561 parentNode,int MaxDepth) 
 {
	double bestMove=0;
	double[] Array=new double[2];

//=========Προχωράμε μέσα στα υποδέντρα μέχρι να βρούμε τα φύλλα========= 	
	
//Αν έχω παιδιά,δηλαδή δεν είμαι φύλλο	 
if(parentNode.getChildren().size() > 0){	

	//Ξανά καλώ την ίδια συνάρτηση μεχρι να φτάσω ένα επίπεδο πρίν τα φύλλα(προτελευταίο επίπεδο)
	 if(parentNode.getNodeDepth()<MaxDepth-1)		                                     
	 {
	  for(int i=0;i<parentNode.getChildren().size();i++)
	  {
	   getNode(parentNode.getChildren(i),MaxDepth);
	  }
		  
	 }
//========================================================================	 
	 
//========Υπολογίζουμε το μονοπάτι της καλύτερης κίνησης=======
	 
	 if((parentNode.getNodeDepth()&1) == 0){//Ειναι δικιά μου κίνηση(αρτιος) 
	  Array=getMax(parentNode.getChildren());
	  
	  double max=Array[0]; //Υπολόγησε την μέγαλύτερη τιμή αξιολόγησης απο τα παιδιά 
	  bestMove=Array[1]; //Την καλύτερη κίνηση
	  
	  parentNode.setNodeEvaluation(max); //Βάλε την τιμή που βρίκες στο πατρικό node
	  	  
	 } else{//Αντίπαλος
		 
		  Array=getMin(parentNode.getChildren());
		  
		  double min=Array[0]; //Υπολόγησε την μικρότερη τιμή αξιολόγησης απο τα παιδιά 
		  bestMove=Array[1]; //Την καλύτερη κίνηση
		 
		  parentNode.setNodeEvaluation(min); //Βάλε την τιμή που βρίκες στο πατρικό node
		 } 
	 } 
	 return((int) bestMove);
	//==========================================================	 
 }
 
 public double[] getMax(ArrayList<Node82008561> list){
	    double[] output=new double[2];
	    output[0]=Integer.MIN_VALUE;
	   
	    for(int i=0; i<list.size(); i++){
	        if(list.get(i).getNodeEvaluation() > output[0]){
	        	output[0] = list.get(i).getNodeEvaluation();
	        	output[1]=i;
	        }
	    }
	    return (output);
	} 
  
 public double[] getMin(ArrayList<Node82008561> list){
	 double[] output=new double[2];
	    output[0]=Integer.MAX_VALUE;
	    
	    for(int i=0; i<list.size(); i++){
	        if(list.get(i).getNodeEvaluation() < output[0]){
	        	output[0] = list.get(i).getNodeEvaluation();
	        	output[1]=i;
	        }
	    }
	    return (output);
	} 
 
 

}
