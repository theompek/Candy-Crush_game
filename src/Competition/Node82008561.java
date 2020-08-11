package gr.auth.ee.dsproject.crush.node82008561;

import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.board.Board;
import java.util.ArrayList;

public class Node82008561
{
// TODO Rename and fill the code

  private Node82008561 parent;                 //Ο κόμβος πατέρας του κόμβου
  private ArrayList<Node82008561> children;    //Τα παιδιά του κόμβου
  private int nodeDepth;					   //Το βάθος του κόμβου στο δέντρο
  private int[] nodeMove;                      //Η κίνηση που αντιπροσωπεύει το Node
  private Board nodeBoard;                     //Το ταμπλό του παιχνιδιού για αυτό τον κόμβο-κίνηση
  private double nodeEvaluation;               //Η τιμή της συνάρτησης αξιολόγησης
  private int flag=0;
  
//----Constructors-----
//Απλός χωρίς ορίσματα
public Node82008561()
  {
	this.children=new ArrayList<Node82008561>();
	this.nodeDepth=-1;
    this.nodeMove=new int[3];	
	this.nodeBoard=new Board();	
	this.nodeEvaluation=0;	
	this.flag=0;
  }
  
//Ενας constructor για την περίπτωση της ρίζας
//Η ρίζα αντιστοιχεί στην τωρινή κατάσταση,δεν έχει 
//πατέρα(parent) ούτε αντιστοιχεί σε κάποια κίνηση(nodeMove)    
public Node82008561(Board nodeBoard) 
  {
	this.children=new ArrayList<Node82008561>();
	this.nodeDepth=0;
	this.nodeBoard=nodeBoard;	
	this.nodeEvaluation=0;	
	this.flag=0;
  }
  
//Constructor με άμεσο καθοριζμό παραραμέτρων	
public Node82008561(Node82008561 parent,int nodeDepth,int[] nodeMove,Board nodeBoard,double nodeEvaluation)
  {
	this.parent=parent;
	this.children=new ArrayList<Node82008561>();
	this.nodeDepth=nodeDepth;
	this.nodeMove=nodeMove;	
	this.nodeBoard=nodeBoard;	
	this.nodeEvaluation=nodeEvaluation;	
	this.flag=0;
  }
 
//Συναρτήσεις Set 
public void setParent(Node82008561 parent)
{ 
this.parent=parent;
}

public void setChildren(Node82008561 children)
{
this.children.add(children);
}

public void setΝodeDepth(int nodeDepth)
{
this.nodeDepth=nodeDepth;
}

public void setNodeMove(int[] nodeMove)
{
this.nodeMove=nodeMove;
}

public void setNodeBoard(Board nodeBoard)
{
this.nodeBoard=nodeBoard;	
}

public void setNodeEvaluation(double nodeEvaluation)
{
this.nodeEvaluation=nodeEvaluation;		
}
 
//Συναρτήσεις Get
public Node82008561 getParent()
{
 return (this.parent);
}

//Επιστροφή ενός απο τα παιδιά κόμβου
public Node82008561 getChildren(int index)
{
 return (this.children.get(index));
}

//Επιστροφή ολόκληρης της λίστας των παιδιών
public ArrayList<Node82008561> getChildren()
{
 return (this.children);
}

public int getNodeDepth()
{
 return (this.nodeDepth);
}

public int[] getNodeMove()
{
 return (this.nodeMove);
}

public Board getNodeBoard()
{
 return (this.nodeBoard);
}

public double getNodeEvaluation()
{
 return (this.nodeEvaluation);
}

//Συνάρτηση αξιολόγησης
public double moveEvaluation (int PlayerId){

this.flag++;	
		
int numOfComb=0;
int earnedPoints=0;
int pointsFromRepeat=0;
boolean playAgainFlag=false;
int[] move=this.nodeMove;
int maxDepthRows=CrushUtilities.NUMBER_OF_PLAYABLE_ROWS;

int neighborX=0;
int neighborY=0;
int neighborDir=0;
int x=move[0];
int y=move[1];



//Η θέση και η κίνηση που θα κάνει το γειτονικό πλακίδιο με το οποίο θα αλλάξω θέση
if(move[2]==CrushUtilities.RIGHT) {neighborX=x+1; neighborY=y; neighborDir=CrushUtilities.LEFT;}
if(move[2]==CrushUtilities.LEFT)  {neighborX=x-1; neighborY=y; neighborDir=CrushUtilities.RIGHT;}
if(move[2]==CrushUtilities.UP)    {neighborX=x; neighborY=y+1; neighborDir=CrushUtilities.DOWN;}
if(move[2]==CrushUtilities.DOWN)  {neighborX=x; neighborY=y-1; neighborDir=CrushUtilities.UP;}

do{//Αν βρώ πεντάρι ξανα παίζω και συνυπολογίζω και τους νέους πόντους

playAgainFlag=false;	

if(earnedPoints==0)//Αν παίζω μία φορά(δηλαδή δεν έχω βρεί ακόμα πεντάρι)
{
//Πόντοι απο την μετακίνηση του δικού μου πλακιδίου	
if(5==(pointsFromRepeat=numOfTilesWithSameColorWithMe(move[0],move[1],move[2],this.nodeBoard))) 
{
	playAgainFlag=true;
}

earnedPoints+=pointsFromRepeat;


//Πόντοι απο το γειτονικό πλακίδιο με το οποίο θα αλλάξω θέση
earnedPoints+= numOfTilesWithSameColorWithMe(neighborX,neighborY,neighborDir,this.nodeBoard); 


}else{//Αν ξανα παίξω,δηλαδή βρώ πεντάρι

//Βρες τις επιτρεπτές κοινήσεις απο το board 
ArrayList<int[]> availableMoves=CrushUtilities.getAvailableMoves(this.nodeBoard);

//-----Υπολόγισε την καλύτερη μου κίνηση και πρόσθεσε την ----
//----------στους πόντους της κίνησης του κόμβου--------------
int[] mv=this.nodeMove;
double[] bestMove=new double[availableMoves.size()];

for(int i=0;i<availableMoves.size();i++){
	
this.nodeMove=availableMoves.get(i);
bestMove[i]=moveEvaluation(PlayerId);

}

this.nodeMove=mv;

double Max=0;
for(int i=0;i<availableMoves.size();i++){
		
	if(Max<bestMove[i]){
		Max=bestMove[i];
	}

}

earnedPoints+=Max;

break;
//------------------------------------------------------------
}

//Ένα αντίγραφο απο το στιγμιότυπο του ταμπλό
Board cloneBoard=CrushUtilities.boardAfterFirstCrush(this.nodeBoard,move,maxDepthRows);	//<-------

//Συντελεστής πολλαπλασιασμού των αλυσιδωτών κινήσεων
double multFactor=0.5;

do
{
multFactor+=0.5;	
//Υπολογίζουμε τους πόντους των αλυσιδωτών κινήσεων
if(5==(numOfComb=earnedTilesFromCombine(cloneBoard,maxDepthRows))) 
{
 playAgainFlag=true;	
}
earnedPoints+=multFactor*numOfComb;

cloneBoard=CrushUtilities.boardAfterDeletingNples(cloneBoard,maxDepthRows);//Σπάσε τους συνδιασμόυς <--------
}while(numOfComb!=0); //Έπανάληψη μέχρι να μην έχουμε αλλους συνδιασμούς

if(flag==1){
//Ο κόμβος θα πάρει την τελική μορφή του ταμπλό μετά την κίνηση αυτή
this.nodeBoard=CrushUtilities.boardAfterFullMove(this.nodeBoard,move);
}else{
	if(this.flag>1){
		break;
	}
	
}
}while(playAgainFlag);

//----Αν με την κίνηση ξεπεράσει τους 500 πόντους----
//----------ο αντίστοιχος παίκτης κερδίζει-----------
int MyScore=CrushUtilities.getOpponentsScore(PlayerId+1);
int OpponentsScore=CrushUtilities.getOpponentsScore(PlayerId);

if(((MyScore+earnedPoints)>=500)&&((this.nodeDepth&1) != 0))
{
 earnedPoints=1000;
}

if(((OpponentsScore+earnedPoints)>=500)&&((this.nodeDepth&1) == 0))
{
 earnedPoints=1000;
}
//---------------------------------------------------

//Ανάλογα με το αν είναι δικιά μου η κίνηση ή του αντιπάλου
//θα προσθέσουμε το αποτέλεσμα στην τιμή evaluation του πατέρα 
//ή θα την αφαιρέσουμε αντίστοιχα και θα εισαγουμε το αποτέλεσμα 
//στην τιμή evaluation του τρέχοντως κόμβου
//========================================================
if(flag==1){
if((this.nodeDepth&1) != 0)//Οταν βρίσκομαι σε επίπεδο δικό μου(περιττος)
{
 this.nodeEvaluation+=earnedPoints;
}
else{//Αντίπαλος
 this.nodeEvaluation-=earnedPoints;
}

}
//========================================================
this.flag--;

return ((double) earnedPoints);
		
}


//Συνάρτηση που μετράει τον αριθμό των πλακιδίων με το ίδιο χρώμα
//γύρο απο την νέα θέση ανάλογα την κατεύθυνση κίνησης
int numOfTilesWithSameColorWithMe(int x,int y,int direction,Board board) 
{
	int tilesWithSameColor=0;
	int neighborX=0;
	int neighborY=0;
		
//Κατεύθυνση αριστερά-δεξιά
if(direction==CrushUtilities.RIGHT||direction==CrushUtilities.LEFT)
{	
   if(direction==CrushUtilities.RIGHT){
	   neighborX=x+1;
       neighborY=y;
      
	   if( ((x+3)<CrushUtilities.NUMBER_OF_COLUMNS)&&
	       (board.giveTileAt(x+3,neighborY).getColor()==board.giveTileAt(x, y).getColor())&&
	       (board.giveTileAt(x+2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) ) 
		   {
		   tilesWithSameColor+=2;
		   
		   }
		   
	   }
   else{
	    neighborX=x-1;
		neighborY=y;
		
	   if( ((x-3)>=0)&&
	       (board.giveTileAt(x-3,neighborY).getColor()==board.giveTileAt(x, y).getColor())&&
	       (board.giveTileAt(x-2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) ) 
	   {
		   tilesWithSameColor+=2;
		   
	   }	   
	   }
   
    //Ελέγχουμε το χρώμα των δύο απο -ΠΑΝΩ- πλακιδίων
	if((y+1)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)
	{
	
		if(board.giveTileAt(neighborX, y+1).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((y+2)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)&& //Αν δεν έχουμε βγει απο τα όρια και
			   (board.giveTileAt(neighborX, y+2).getColor()==board.giveTileAt(x, y).getColor()) //εχουμε το ίδιο χρώμα πλακιδίων
			    ){
					tilesWithSameColor++; 
				}else if((y-1<0)||(board.giveTileAt(neighborX, y-1).getColor()!=board.giveTileAt(x, y).getColor())){
					tilesWithSameColor--; 
				}
			
		}	
	}
	
	 //Ελέγχουμε το χρώμα των δύο απο -ΚAΤΩ- πλακιδίων
	if((y-1)>=0)
	{
	
		if(board.giveTileAt(neighborX, y-1).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((y-2)>=0)&& //Αν δεν έχουμε βγει απο τα όρια και
			   (board.giveTileAt(neighborX, y-2).getColor()==board.giveTileAt(x, y).getColor()) //εχουμε το ίδιο χρώμα πλακιδίων
			    ){
					tilesWithSameColor++;
				}else if((y+1>=CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)||(board.giveTileAt(neighborX, y+1).getColor()!=board.giveTileAt(x, y).getColor())){
					tilesWithSameColor--;
				}
		}
    }
    
	
	if(tilesWithSameColor==0) return (tilesWithSameColor);  //Αν ειναι το γειτονικό μπορεί να μην εχει συνδιασμο
	
	return (tilesWithSameColor+1);
}

//Κατεύθυνση πάνω-κάτω
 if(direction==CrushUtilities.UP||direction==CrushUtilities.DOWN)
{	
   if(direction==CrushUtilities.UP){
	   neighborY=y+1;
	   neighborX=x;
	   
	   if( ((y+3)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)&&
	       ((y+2)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)&&
	       (board.giveTileAt(neighborX, y+3).getColor()==board.giveTileAt(x, y).getColor())&&
	       (board.giveTileAt(neighborX, y+2).getColor()==board.giveTileAt(x, y).getColor()) ) tilesWithSameColor+=2;
	      
	   }
       else{
	   neighborY=y-1;
	   neighborX=x;
	  
	   if( ((y-3)>=0)&&
	       ((y-2)>0)&&
	       (board.giveTileAt(neighborX, y-3).getColor()==board.giveTileAt(x, y).getColor())&&
	       (board.giveTileAt(neighborX, y-2).getColor()==board.giveTileAt(x, y).getColor()) ) tilesWithSameColor+=2;
	   
	   }
   
    //Ελέγχουμε το χρώμα των δύο απο -ΤΑ ΔΕΞΙΑ- πλακιδίων
	if((x+1)<CrushUtilities.NUMBER_OF_COLUMNS)
	{
		if(board.giveTileAt(x+1,neighborY).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((x+2)<CrushUtilities.NUMBER_OF_COLUMNS)&& //Αν δεν έχουμε βγει απο τα όρια και
			   (board.giveTileAt(x+2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) //εχουμε το ίδιο χρώμα πλακιδίων
			    ){
					tilesWithSameColor++;
				}else if((x-1<0)||board.giveTileAt(x-1,neighborY).getColor()!=board.giveTileAt(x, y).getColor()){
					tilesWithSameColor--;
				}
			
		}	
	}
	
	 //Ελέγχουμε το χρώμα των δύο απο -ΤΑ ΑΡΙΣΤΕΡΑ- πλακιδίων
	if((x-1)>=0)
	{
		if(board.giveTileAt(x-1,neighborY).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((x-2)>=0)&& //Αν δεν έχουμε βγει απο τα όρια και
			   (board.giveTileAt(x-2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) //εχουμε το ίδιο χρώμα πλακιδίων
			    ){
					tilesWithSameColor++;
				 }else if((x+1>=CrushUtilities.NUMBER_OF_COLUMNS)||board.giveTileAt(x+1,neighborY).getColor()!=board.giveTileAt(x, y).getColor()){
					tilesWithSameColor--;
				}
		}	
	}
	
if(tilesWithSameColor==0)return (tilesWithSameColor); //Αν ειναι το γειτονικό μπορεί να μην εχει συνδιασμο
	
	return (tilesWithSameColor+1);
}
 
return(0); 
}


//Συνάρτηση που μετράει τον αριθμό των συνδιασμός που υπάρχουν στο ταμπλό
int earnedTilesFromCombine(Board board,int nuOfRows){

int earnedPoints=0;

//Κάνουμε έλεγχο για συνδιασμούς κατα σειρές	
for(int y=0;y<nuOfRows;y++) //Σειρές
	{
	int previousColor=board.giveTileAt(0,y).getColor();//To πρώτο στοιχείο κάθε σειράς
	int count=0;

  for(int x=1;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //Στήλες
   {
  	
	   if(previousColor==board.giveTileAt(x,y).getColor()) {
	   count++;
	   }
	   else{
	    previousColor=board.giveTileAt(x,y).getColor();
	    count=0;
	   }
		
	   if(count==2){
		  
		 board.giveTileAt(x-2,y).setMark(true);
		 board.giveTileAt(x-1,y).setMark(true);
       board.giveTileAt(x,y).setMark(true);		  
	   }
		
	   if(count>2){
		 board.giveTileAt(x,y).setMark(true);
		}	
		
	 }

	}

//Κάνουμε έλεγχο για συνδιασμούς κατα στήλες
for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //Στήλες
{
  int previousColor=board.giveTileAt(x,0).getColor();//To πρώτο στοιχείο κάθε σειράς
	int count=0;
for(int y=1;y<nuOfRows;y++) //Σειρές
	{
    if(previousColor==board.giveTileAt(x,y).getColor()) {
	   count++;
	   }
	   else{
	    previousColor=board.giveTileAt(x,y).getColor();
	    count=0;
	   }
		
	   if(count==2){
		 board.giveTileAt(x,y-2).setMark(true);
		 board.giveTileAt(x,y-1).setMark(true);
       board.giveTileAt(x,y).setMark(true);		  
	   }
		
	   if(count>2){
		 board.giveTileAt(x,y).setMark(true);
		}	
		
	 }
	
	}


for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //Στήλες
{
for(int y=0;y<nuOfRows;y++) //Σειρές
{
	
 if((board.giveTileAt(x,y).getMark()==true)&&(board.giveTileAt(x,y).getColor()!=(-1))) 
 {
	   
	  board.giveTileAt(x,y).setMark(false);
	  earnedPoints++;
 }  
}

}

return(earnedPoints);

}




public void printBoard(int[] move,Board board)
{
	System.out.println("================New Boar===============");
	 System.out.println();
	for(int y=CrushUtilities.NUMBER_OF_PLAYABLE_ROWS-1;y>=0;y--) //Σειρές
	{
		for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //Στήλες
	  {
		  if((x==move[0])&&(y==move[1])) 
			  {
			  System.out.print("(");
			  }else{
				  System.out.print(" ");
			  }
		  
		  System.out.print(board.giveTileAt(x, y).getColor());
			
		  if((x==move[0])&&(y==move[1])){
	    if(move[2]==CrushUtilities.RIGHT)	System.out.print("->");
		if(move[2]==CrushUtilities.LEFT)	System.out.print("<-");
		if(move[2]==CrushUtilities.DOWN)	System.out.print("__");
		if(move[2]==CrushUtilities.UP)	System.out.print("^^");
		  }else{
			  System.out.print("  ");
		  }
		  
		  if((x==move[0])&&(y==move[1])) 
			  {
			  System.out.print(")");
			  }else{
				  System.out.print(" ");
			  }
	   } 
	  System.out.println();
	  }
	System.out.println("=======================================");
	 System.out.println();
	 System.out.println();
	 System.out.println();
	
	
	
	
}


 
}
