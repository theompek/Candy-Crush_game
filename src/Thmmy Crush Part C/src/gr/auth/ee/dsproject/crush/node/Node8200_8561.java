package gr.auth.ee.dsproject.crush.node;


import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.board.Board;
import java.util.ArrayList;

public class Node8200_8561
{
// TODO Rename and fill the code

  private Node8200_8561 parent;                //Ο κόμβος πατέρας του κόμβου
  private ArrayList<Node8200_8561> children;  //Τα παιδιά του κόμβου
  private int nodeDepth;					   //Το βάθος του κόμβου στο δέντρο
  private int[] nodeMove;                      //Η κίνηση που αντιπροσωπεύει το Node
  private Board nodeBoard;                     //Το ταμπλό του παιχνιδιού για αυτό τον κόμβο-κίνηση
  private double nodeEvaluation;               //Η τιμή της συνάρτησης αξιολόγησης

//----Constructors-----
//Απλός χωρίς ορίσματα
public Node8200_8561()
  {
	this.children=new ArrayList();
	this.nodeDepth=-1;
    this.nodeMove=new int[3];	
	this.nodeBoard=new Board();	
	this.nodeEvaluation=0;	
	
  }
  
//Ενας constructor για την περίπτωση της ρίζας
//Η ρίζα αντιστοιχεί στην τωρινή κατάσταση,δεν έχει 
//πατέρα(parent) ούτε αντιστοιχεί σε κάποια κίνηση(nodeMove)    
public Node8200_8561(Board nodeBoard) 
  {
	this.children=new ArrayList();
	this.nodeDepth=0;
	this.nodeBoard=nodeBoard;	
	this.nodeEvaluation=0;	
	
  }
  
//Constructor με άμεσο καθοριζμό παραραμέτρων	
public Node8200_8561(Node8200_8561 parent,int nodeDepth,int[] nodeMove,Board nodeBoard,double nodeEvaluation)
  {
	this.parent=parent;
	this.children=new ArrayList();
	this.nodeDepth=nodeDepth;
	this.nodeMove=nodeMove;	
	this.nodeBoard=nodeBoard;	
	this.nodeEvaluation=nodeEvaluation;	
	
  }
 
//Συναρτήσεις Set 
public void setParent(Node8200_8561 parent)
{ 
this.parent=parent;
}

public void setChildren(Node8200_8561 children)
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
public Node8200_8561 getParent()
{
 return (this.parent);
}

//Επιστροφή ενός απο τα παιδιά κόμβου
public Node8200_8561 getChildren(int index)
{
 return (this.children.get(index));
}

//Επιστροφή ολόκληρης της λίστας των παιδιών
public ArrayList<Node8200_8561> getChildren()
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
public double moveEvaluation (){
	
int numOfComb=0;
int earnedPoints=0;
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


earnedPoints=numOfTilesWithSameColorWithMe(move[0],move[1],move[2],this.nodeBoard)+ //Πόντοι απο την μετακίνηση του δικού μου πλακιδίου
	         numOfTilesWithSameColorWithMe(neighborX,neighborY,neighborDir,this.nodeBoard); //Πόντοι απο το γειτονικό πλακίδιο με το οποίο θα αλλάξω θέση

//Ορίζουμε ως μέγιστο βάθος αναζήτησης για τις αλυσιδοτές κινήσεις το πολύ
//δυο γραμμές πιο κάτω απο αυτην της κίνησης αφού τα πλακίδια που θα σπάσουν
//θα είναι κατα την πλειοψηφία πάνω απο αυτό το επίπεδο			 
//if((y+2)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS) maxDepthRows=y+2;

//Ένα αντίγραφο απο το στιγμιότυπο του ταμπλό
Board cloneBoard=CrushUtilities.boardAfterFirstCrush(this.nodeBoard,move,maxDepthRows);	
System.out.println("<---Rowss----->"+maxDepthRows);
do
{//Υπολογίζουμε τους πόντους των αλυσιδωτων κινήσεων
numOfComb=earnedTilesFromCombine(cloneBoard,maxDepthRows);//Υπολόγισε πόντους
earnedPoints+=numOfComb;
//System.out.println("<------------->"+earnedPoints);
cloneBoard=CrushUtilities.boardAfterDeletingNples(cloneBoard,maxDepthRows);//Σπάσε τους συνδιασμόυς 
}while(numOfComb!=0); //Έπανάληψη μέχρι να μην έχουμε αλλους συνδιασμούς

//Ο κόμβος θα πάρει την τελική μορφή του ταμπλό μετά την κίνηση αυτή
this.nodeBoard=CrushUtilities.boardAfterFullMove(this.nodeBoard,move);

if((this.nodeDepth&1) != 0){System.out.println("<------------->");}
	
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
	       (board.giveTileAt(x+2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) ) tilesWithSameColor+=2;
		   
		   
	   }
   else{
	    neighborX=x-1;
		neighborY=y;
		
	   if( ((x-3)>=0)&&
	       (board.giveTileAt(x-3,neighborY).getColor()==board.giveTileAt(x, y).getColor())&&
	       (board.giveTileAt(x-2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) ) tilesWithSameColor+=2;
	       
			   
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
	
	 //Ελέγχουμε το χρώμα των δύο απο -Κ’ΤΩ- πλακιδίων
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
   
    //Ελέγχουμε το χρώμα των δύο απο -ΠΑΝΩ- πλακιδίων
	if((x+1)<CrushUtilities.NUMBER_OF_COLUMNS)
	{
		if(board.giveTileAt(x+1,neighborY).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((x+2)<CrushUtilities.NUMBER_OF_COLUMNS)&& //Αν δεν έχουμε βγει απο τα όρια και
			   (board.giveTileAt(x+2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) //εχουμε το ίδιο χρώμα πλακιδίων
			    ){
					tilesWithSameColor++;
				}else if((x-1<0)||board.giveTileAt(x-1,neighborY).getColor()==board.giveTileAt(x, y).getColor()){
					tilesWithSameColor--;
				}
			
		}	
	}
	
	 //Ελέγχουμε το χρώμα των δύο απο -Κ’ΤΩ- πλακιδίων
	if((x-1)>=0)
	{
		if(board.giveTileAt(x-1,neighborY).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((x-2)>=0)&& //Αν δεν έχουμε βγει απο τα όρια και
			   (board.giveTileAt(x-2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) //εχουμε το ίδιο χρώμα πλακιδίων
			    ){
					tilesWithSameColor++;
				 }else if((x+1>=CrushUtilities.NUMBER_OF_COLUMNS)||board.giveTileAt(x+1,neighborY).getColor()==board.giveTileAt(x, y).getColor()){
					tilesWithSameColor--;
				}
		}	
	}
	
	return (tilesWithSameColor);
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

    for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //Στήλες
     {
    	    	
	   if(previousColor==board.giveTileAt(x,y).getColor()) {
	   count++;
	   }
	   else{
	    previousColor=board.giveTileAt(x,y).getColor();
	    count=0;
	   }
		
	   if(count==3){
		 board.giveTileAt(x-2,y).setMark(true);
		 board.giveTileAt(x-1,y).setMark(true);
         board.giveTileAt(x,y).setMark(true);		  
	   }
		
	   if(count>3){
		 board.giveTileAt(x,y).setMark(true);
		}	
		
	 }
	
	}

//Κάνουμε έλεγχο για συνδιασμούς κατα στήλες
 for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //Στήλες
  {
    int previousColor=board.giveTileAt(x,0).getColor();//To πρώτο στοιχείο κάθε σειράς
	int count=0;
  for(int y=0;y<nuOfRows;y++) //Σειρές
	{
      if(previousColor==board.giveTileAt(x,y).getColor()) {
	   count++;
	   }
	   else{
	    previousColor=board.giveTileAt(x,y).getColor();
	    count=0;
	   }
		
	   if(count==3){
		 board.giveTileAt(x,y-2).setMark(true);
		 board.giveTileAt(x,y-1).setMark(true);
         board.giveTileAt(x,y).setMark(true);		  
	   }
		
	   if(count>3){
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


 
}
