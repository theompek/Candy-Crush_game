package gr.auth.ee.dsproject.crush.player;

import gr.auth.ee.dsproject.crush.board.Board;
import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.defplayers.AbstractPlayer;
import gr.auth.ee.dsproject.crush.node.Node8200_8561;

import java.util.ArrayList;

public class MinMaxPlayer implements AbstractPlayer
{
  // TODO Fill the class code.
  public static final int MAX_DEPTH=2;
  int score;
  int id;
  String name;

  public MinMaxPlayer (Integer pid)
  {
    id = pid;
    score = 0;
  }

  public String getName ()
  {

    return "MinMax";

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
Node8200_8561 root=new Node8200_8561(cloneBoard);
//System.out.println("<------------->");
//Καλεσαι την createMySubThree για την δημιουργία υποδέντρου
createSubTree(root,MAX_DEPTH);
//System.out.println("<------------->");
//Κάλεσαι chooseMinMaxMove(Node root) για την επιλογή της καλύτερης κίνησης index
int indexBest = chooseMinMaxMove(root,MAX_DEPTH);


//Καλεσαι την calculateNextMove(index) για να επιστρεψει τις συντεταγμένες int[]
int[] bestMove = availableMoves.get(indexBest);

return CrushUtilities.calculateNextMove(bestMove);
    
}

private void createSubTree (Node8200_8561 parent, int MaxDepth)
{
 // TODO Fill the code
//Βρες τις επιτρεπτές κοινήσεις απο το board του πατέρα
ArrayList<int[]> availableMoves=CrushUtilities.getAvailableMoves(parent.getNodeBoard());

int listSize=availableMoves.size();

//Για κάθε κίνηση:
for(int i=0;i<listSize;i++)	
{
 //System.out.println("listSize: "+listSize);
 //Δημιουργησε ένα node σαν παιδί του πατρικού node
 //και πρόσθεσαι το παιδί στο arrayOfChildren του πατέρα	
 parent.setChildren(new Node8200_8561(
                                      parent, //O γονέας του node-παιδιού
 									  parent.getNodeDepth()+1, //Το βάθος του node-παιδιού
 									  availableMoves.get(i), //Η κίνηση του αντιστοιχεί στο node-παιδί
   									  CrushUtilities.cloneBoard(parent.getNodeBoard()), //Αντίγραφο απο το ταμπλό του πατέρα
									  parent.getNodeEvaluation() //Δώσε στο παιδί την τιμή αξιολόγησης του πατέρα
									  )                          //για να την συνυπολογίσει στην δικιά του αξιολόγηγη
			        );
	//System.out.println("Depth: "+parent.getNodeDepth());
 //Υπολόγισαι την τιμή αξιολόγησης του παιδιού και την
 //νέα κατάσταση του ταμπλό για την κίνηση αυτή
 parent.getChildren(i).moveEvaluation();

 //Κάλεσαι την createSubTree() αν δεν έχουμε
 //φτάσει μέρχι τα φύλλα του δέντρου	
 if((parent.getNodeDepth()+1)!=MaxDepth)
 {
  createSubTree(parent.getChildren(i),MaxDepth);//Δημιούργησε νέο υποδέντρο και υπολόγισε την αξιολόγηση των παιδιών
 }

}
//System.out.println("getChildrenSize: "+parent.getChildren().size());  
}

/*  private void createOpponentSubTree (Node parent, int depth)
  {
// TODO Fill the code

//Αφού λαβει το node πατέρα και την κατασταση του ταμπλο υπολόγισε την νέα κατάσταση του μετα απο μία πλήρη κίνηση
//Βρες τις επιτρεπτές κοινήσεις
// Για κάθε κίνηση:
 //Φτιαξε το board μετα την εκτέλεση την κίνησης
 //Φιαξε ενα νεο node που αντιστοιχεί στην κίνηση
 //Κάνε αξιολόγηγη(με αρνητικό βάρος)
 //Πρόσθεσαι στην αξιολόγηση του πατέρα στη τωρινή αξιολόγηγη
 //Προσθεσαι το τρέχον τα newnode στο array του τρεχοντως-πατερα node 
  
	
		
  }
*/	
  private int chooseMinMaxMove (Node8200_8561 root,int MaxDepth)
  {
    // TODO Fill the code
	return( getNode(root,MaxDepth) );
	  
  }

//  
 int getNode(Node8200_8561 parentNode,int MaxDepth) 
 {
	double bestMove=0;
	double[] Array=new double[2];
	 if(parentNode.getNodeDepth()<MaxDepth-1)//Αν δεν είναι ένα επίπεδο πρίν το τελευταίο φύλλο		                                     
	 {
	  for(int i=0;i<parentNode.getChildren().size();i++)
	  {
	   getNode(parentNode.getChildren(i),MaxDepth);
	  }
		  
	 }
	 
	 if((parentNode.getNodeDepth()&1) != 0){//Ειναι δικιά μου κίνηση(περιττος)
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
	 
	 return((int) bestMove);
	 
 }
 
 public double[] getMax(ArrayList<Node8200_8561> list){
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
  
 public double[] getMin(ArrayList<Node8200_8561> list){
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
