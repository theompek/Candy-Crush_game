package gr.auth.ee.dsproject.crush.board;

public class Tile
{

  protected int id;
  private int x;
  private int y;
  private int color;
  private boolean mark;

   //Constructor
  public Tile() {
	  
	this.id=-1;
	this.x=-1;
	this.y=-1;
	this.color=-1;
	this.mark=false;
	
  }
   //Constructor
  public Tile(int id,int x,int y,int color,boolean mark) {
  
	  this.id=id;
	  this.x=x;
	  this.y=y;
	  this.color=color;
	  this.mark=mark;
	  
  }
//Setters  
public void setId(int id){
	 this.id=id;
  }

public void setX(int x){
	this.x=x;
}
  
public void setY(int y){
	this.y=y;
}

public void setColor(int color){
	this.color=color;
}
  
public void setMark(boolean mark){
	this.mark=mark;
} 
  
 //Getters 
public int getId(){
return this.id;
}

public int getX(){
return this.x;
}

public int getY(){
return this.y;
}

public int getColor(){
return this.color;
}

public boolean getMark(){
return this.mark;
} 


}
