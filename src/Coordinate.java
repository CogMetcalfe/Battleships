
public class Coordinate {

	public static char numToLetter(int i) {
		return (char)(i+65);
	}
	public static int letterToNum(char c) {
		return ((int)(c))-65;
	}
	public static String coordinateFromVector(Vector p) {
		return coordinateFromVector(p.x,p.y);
	}
	public static String coordinateFromVector(int x, int y) {
		return "" + numToLetter(x) + (y);
	}
	
	public static Vector coordinateStringToVector(String s) {
		s= s.toUpperCase();
		s = s.replace(",", "");
		s = s.replace(" ", "");
		Vector position = new Vector();
		int i=0;
		while(i<s.length()&&letterToNum(s.charAt(i))>=0 && letterToNum(s.charAt(i))<26) {
			i++;
		} // get all upper case letters
		if(i==1) {
			position.x=letterToNum(s.charAt(0));
		}else {
			position.x = position.y = -1;
			return position;
		}
		int start = i;
		int value = -1;
		i++;
		while(i<s.length()+1){
			try {
				value = Integer.parseInt(s.substring(start, i));
			}catch(NumberFormatException e) {
				break;
			}
			i++;
		}
		position.y = value;
		return position;
	}
}
