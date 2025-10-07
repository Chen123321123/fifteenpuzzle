package fifteenpuzzle;


public class Point {
	private int column;
	private int row;
	Point(int x,int y){
		this.column=x;
		this.row=y;
	}
	public int getColumn() {
		return column;
	}
	public int getRow() {
		return row;
	}
	boolean isEqual(Point p) {
		if(p.getColumn()==this.getColumn()&&p.getRow()==this.getRow()) {
			return true;
		}
		return false;
	}
}
