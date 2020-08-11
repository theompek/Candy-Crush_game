package gr.auth.ee.dsproject.crush.node;


import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.board.Board;
import java.util.ArrayList;

public class Node8200_8561
{
// TODO Rename and fill the code

  private Node8200_8561 parent;                //� ������ ������� ��� ������
  private ArrayList<Node8200_8561> children;  //�� ������ ��� ������
  private int nodeDepth;					   //�� ����� ��� ������ ��� ������
  private int[] nodeMove;                      //� ������ ��� �������������� �� Node
  private Board nodeBoard;                     //�� ������ ��� ���������� ��� ���� ��� �����-������
  private double nodeEvaluation;               //� ���� ��� ���������� �����������

//----Constructors-----
//����� ����� ��������
public Node8200_8561()
  {
	this.children=new ArrayList();
	this.nodeDepth=-1;
    this.nodeMove=new int[3];	
	this.nodeBoard=new Board();	
	this.nodeEvaluation=0;	
	
  }
  
//���� constructor ��� ��� ��������� ��� �����
//� ���� ����������� ���� ������ ���������,��� ���� 
//������(parent) ���� ����������� �� ������ ������(nodeMove)    
public Node8200_8561(Board nodeBoard) 
  {
	this.children=new ArrayList();
	this.nodeDepth=0;
	this.nodeBoard=nodeBoard;	
	this.nodeEvaluation=0;	
	
  }
  
//Constructor �� ����� ��������� ������������	
public Node8200_8561(Node8200_8561 parent,int nodeDepth,int[] nodeMove,Board nodeBoard,double nodeEvaluation)
  {
	this.parent=parent;
	this.children=new ArrayList();
	this.nodeDepth=nodeDepth;
	this.nodeMove=nodeMove;	
	this.nodeBoard=nodeBoard;	
	this.nodeEvaluation=nodeEvaluation;	
	
  }
 
//����������� Set 
public void setParent(Node8200_8561 parent)
{ 
this.parent=parent;
}

public void setChildren(Node8200_8561 children)
{
this.children.add(children);
}

public void set�odeDepth(int nodeDepth)
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
 
//����������� Get
public Node8200_8561 getParent()
{
 return (this.parent);
}

//��������� ���� ��� �� ������ ������
public Node8200_8561 getChildren(int index)
{
 return (this.children.get(index));
}

//��������� ��������� ��� ������ ��� �������
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

//��������� �����������
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



//� ���� ��� � ������ ��� �� ����� �� ��������� �������� �� �� ����� �� ������ ����
if(move[2]==CrushUtilities.RIGHT) {neighborX=x+1; neighborY=y; neighborDir=CrushUtilities.LEFT;}
if(move[2]==CrushUtilities.LEFT)  {neighborX=x-1; neighborY=y; neighborDir=CrushUtilities.RIGHT;}
if(move[2]==CrushUtilities.UP)    {neighborX=x; neighborY=y+1; neighborDir=CrushUtilities.DOWN;}
if(move[2]==CrushUtilities.DOWN)  {neighborX=x; neighborY=y-1; neighborDir=CrushUtilities.UP;}


earnedPoints=numOfTilesWithSameColorWithMe(move[0],move[1],move[2],this.nodeBoard)+ //������ ��� ��� ���������� ��� ����� ��� ���������
	         numOfTilesWithSameColorWithMe(neighborX,neighborY,neighborDir,this.nodeBoard); //������ ��� �� ��������� �������� �� �� ����� �� ������ ����

//�������� �� ������� ����� ���������� ��� ��� ���������� �������� �� ����
//��� ������� ��� ���� ��� ����� ��� ������� ���� �� �������� ��� �� �������
//�� ����� ���� ��� ���������� ���� ��� ���� �� �������			 
//if((y+2)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS) maxDepthRows=y+2;

//��� ��������� ��� �� ����������� ��� ������
Board cloneBoard=CrushUtilities.boardAfterFirstCrush(this.nodeBoard,move,maxDepthRows);	
System.out.println("<---Rowss----->"+maxDepthRows);
do
{//������������ ���� ������� ��� ���������� ��������
numOfComb=earnedTilesFromCombine(cloneBoard,maxDepthRows);//��������� �������
earnedPoints+=numOfComb;
//System.out.println("<------------->"+earnedPoints);
cloneBoard=CrushUtilities.boardAfterDeletingNples(cloneBoard,maxDepthRows);//����� ���� ����������� 
}while(numOfComb!=0); //��������� ����� �� ��� ������ ������ �����������

//� ������ �� ����� ��� ������ ����� ��� ������ ���� ��� ������ ����
this.nodeBoard=CrushUtilities.boardAfterFullMove(this.nodeBoard,move);

if((this.nodeDepth&1) != 0){System.out.println("<------------->");}
	
return ((double) earnedPoints);
		
}

//��������� ��� ������� ��� ������ ��� ��������� �� �� ���� �����
//���� ��� ��� ��� ���� ������� ��� ���������� �������
int numOfTilesWithSameColorWithMe(int x,int y,int direction,Board board)
{
	int tilesWithSameColor=0;
	int neighborX=0;
	int neighborY=0;
	
//���������� ��������-�����
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
   
    //��������� �� ����� ��� ��� ��� -����- ���������
	if((y+1)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)
	{
		if(board.giveTileAt(neighborX, y+1).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((y+2)<CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)&& //�� ��� ������ ���� ��� �� ���� ���
			   (board.giveTileAt(neighborX, y+2).getColor()==board.giveTileAt(x, y).getColor()) //������ �� ���� ����� ���������
			    ){
					tilesWithSameColor++;
				}else if((y-1<0)||(board.giveTileAt(neighborX, y-1).getColor()!=board.giveTileAt(x, y).getColor())){
					tilesWithSameColor--;
				}
			
		}	
	}
	
	 //��������� �� ����� ��� ��� ��� -ʢ��- ���������
	if((y-1)>=0)
	{
		if(board.giveTileAt(neighborX, y-1).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((y-2)>=0)&& //�� ��� ������ ���� ��� �� ���� ���
			   (board.giveTileAt(neighborX, y-2).getColor()==board.giveTileAt(x, y).getColor()) //������ �� ���� ����� ���������
			    ){
					tilesWithSameColor++;
				}else if((y+1>=CrushUtilities.NUMBER_OF_PLAYABLE_ROWS)||(board.giveTileAt(neighborX, y+1).getColor()!=board.giveTileAt(x, y).getColor())){
					tilesWithSameColor--;
				}
		}
    }
    
	
	
	return (tilesWithSameColor+1);
}

//���������� ����-����
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
   
    //��������� �� ����� ��� ��� ��� -����- ���������
	if((x+1)<CrushUtilities.NUMBER_OF_COLUMNS)
	{
		if(board.giveTileAt(x+1,neighborY).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((x+2)<CrushUtilities.NUMBER_OF_COLUMNS)&& //�� ��� ������ ���� ��� �� ���� ���
			   (board.giveTileAt(x+2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) //������ �� ���� ����� ���������
			    ){
					tilesWithSameColor++;
				}else if((x-1<0)||board.giveTileAt(x-1,neighborY).getColor()==board.giveTileAt(x, y).getColor()){
					tilesWithSameColor--;
				}
			
		}	
	}
	
	 //��������� �� ����� ��� ��� ��� -ʢ��- ���������
	if((x-1)>=0)
	{
		if(board.giveTileAt(x-1,neighborY).getColor()==board.giveTileAt(x, y).getColor())
		{
			tilesWithSameColor++;
			
			if(((x-2)>=0)&& //�� ��� ������ ���� ��� �� ���� ���
			   (board.giveTileAt(x-2,neighborY).getColor()==board.giveTileAt(x, y).getColor()) //������ �� ���� ����� ���������
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

//��������� ��� ������� ��� ������ ��� ���������� ��� �������� ��� ������
int earnedTilesFromCombine(Board board,int nuOfRows){

int earnedPoints=0;

//������� ������ ��� ����������� ���� ������	
  for(int y=0;y<nuOfRows;y++) //������
	{
	int previousColor=board.giveTileAt(0,y).getColor();//To ����� �������� ���� ������
	int count=0;

    for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //������
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

//������� ������ ��� ����������� ���� ������
 for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //������
  {
    int previousColor=board.giveTileAt(x,0).getColor();//To ����� �������� ���� ������
	int count=0;
  for(int y=0;y<nuOfRows;y++) //������
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


 for(int x=0;x<CrushUtilities.NUMBER_OF_COLUMNS;x++) //������
 {
  for(int y=0;y<nuOfRows;y++) //������
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
