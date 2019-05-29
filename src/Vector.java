
public class Vector {
	int x,y;
	Vector(){
		x=y=0;
	}
	Vector(int dir){
		//0 +x
		//1 +y
		//2 -x
		//3 -y
		//ccw from x on standard graph
		double a = ((Math.PI)*dir)/2;
		x = (int) Math.round(Math.cos(a));
		y = (int) Math.round(Math.sin(a));
	}
	Vector(int x, int y){
		this.x=x;
		this.y=y;
	}
	Vector copy() {
		return new Vector(x,y);
	}
	Vector plus(Vector v2) {
		return new Vector(x+v2.x,y+v2.y);
	}
	Vector sub(Vector v2){
		return new Vector(x-v2.x,y-v2.y);
	}
	Vector mult(int m){
		return new Vector(x*m,y*m);
	}
	Vector div(int d){
		return new Vector(x/d,y/d);
	}
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
