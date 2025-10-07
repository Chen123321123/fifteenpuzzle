package fifteenpuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//import fifteenpuzzle.Point;

public class Solver {
	
	public static int[][] ReadTheBoard(String fileName) throws FileNotFoundException{
		int[][] arr;
		File f;
		Scanner r;
		Scanner r2;
		Scanner r3;
		int size=0;
		int num=0;
		int blankColumn=0;
		int blankRow=0;
		String findBlank=null;
		//System.out.println(fileName);
		f=new File(fileName);
		if(!f.exists()) {
			FileNotFoundException ex= new FileNotFoundException(" file not found");
			throw ex;
		}
		r=new Scanner(f);
		r2=new Scanner(f);
		r3=new Scanner(f);
		boolean ans=false;
		/*while(r.hasNextLine()) {
			findBlank=r.nextLine();
			size++;                                 //Determine size of the board
		}*/
		size=r.nextInt();
		r2.next();
		r3.nextLine();
		arr=new int[size][size];
		//System.out.println(size);
		for(int i=0;i<size;i++) {
			String line = r3.nextLine();              //After knowing the size of the board, find the blank space
			for(int j=0;j<size;j++) {
				if(line.charAt(3*j+1)==' ') {
					arr[i][j]=0;                       //when encounter the blank space, set 0 to it
					blankColumn=i;
					blankRow=j;
					ans=true;
					break;
				}
			}
			if(ans==true)
				break;
		}
		r.close();
		r3.close();
		//arr=new int[size][size];
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(i==blankColumn&&j==blankRow) {
					continue;                            //already find the blank space, skip the loop
				}
				num=Integer.parseInt(r2.next());         //read the numbers in the file to the board
				arr[i][j]=num;
			}
		}
		r2.close();
		return arr;
	}
	

	public static LinkedList<Point> findNumberRoute(Point start, Point end){
		LinkedList<Point> route=new LinkedList<Point>();
		int diff_column=start.getColumn()-end.getColumn();
		int diff_row=start.getRow()-end.getRow();
		boolean k=false;
		Point temp=new Point(0,0);
		//move horizontally
		if(diff_row>=0) {
			for(int i=1;i<=diff_row;i++) {
				temp= new Point(start.getColumn(),start.getRow()-i);
				route.add(temp);
				k=true;                 /////////means the temp has initialize
			}
		}
		else {
			for(int i=1;i<=Math.abs(diff_row);i++) {
				temp= new Point(start.getColumn(),start.getRow()+i);
				route.add(temp);
				k=true;
			}
		}
		//temp is waiting to move vertically
		//move vertically
		/*for(int i=1;i<=diff_column;i++) {                              ------------------------
			if(k==true)
				temp=new Point(temp.getColumn()-1,temp.getRow());
			else
				temp=new Point(start.getColumn()-i,start.getRow());
			//System.out.println("here"+" "+diff_column);
			//System.out.println(temp.getColumn()+" "+temp.getRow());
			route.add(temp);                                               ---------------------------*/
		if(diff_column>=0) {
			for(int i=1;i<=diff_column;i++) {
				if(k==true)
					temp=new Point(temp.getColumn()-1,temp.getRow());
				else
					temp=new Point(start.getColumn()-i,start.getRow());
				route.add(temp);
			}
		}
		else if(diff_column<0) {                                               //this is for moving the last two columns
			for(int i=1;i<=Math.abs(diff_column);i++) {
				if(k==true)
					temp=new Point(temp.getColumn()+1,temp.getRow());
				else
					temp=new Point(start.getColumn()+i,start.getRow());
				route.add(temp);
			}
		}
		
		
		return route;
	}
	
	public static LinkedList<Point> findBlankSpaceRoute(Point start,Point end){
		LinkedList<Point> route=new LinkedList<Point>();
		int diff_column=start.getColumn()-end.getColumn();
		int diff_row=start.getRow()-end.getRow();
		Point temp=start;
		if(diff_column>0&&diff_row<0) {    /////////starting point is in the left-down (3) position of the end point
			for(int i=1;i<=Math.abs(diff_row);i++) {
				temp= new Point(start.getColumn(),start.getRow()+i);
				route.add(temp);
			}
			for(int i=1;i<Math.abs(diff_column);i++) {
				temp=new Point(temp.getColumn()-1,temp.getRow());
				route.add(temp);
			}
		}
		else if(diff_column<0&&diff_row>0) {    ////////starting point is in the up-right (1) position of the end point
			for(int i=1;i<=Math.abs(diff_column);i++) {
				temp=new Point(temp.getColumn()+1,temp.getRow());
				route.add(temp);
			}
			for(int i=1;i<Math.abs(diff_row);i++) {
				temp= new Point(temp.getColumn(),start.getRow()-i);
				route.add(temp);
			}
		} 
		else if(diff_column>0&&diff_row>0) { ////////starting point is in the right-down (4) position of the end point
			for(int i=1;i<=Math.abs(diff_row);i++) {
				temp= new Point(start.getColumn(),start.getRow()-i);
				route.add(temp);
			}
			for(int i=1;i<Math.abs(diff_column);i++) {
				temp=new Point(temp.getColumn()-1,temp.getRow());
				route.add(temp);
			}
		}
		else if(diff_column<0&&diff_row<0) 
			{                                       ///////starting point is in the up-left (2) position of the end point
			for(int i=1;i<=Math.abs(diff_row);i++) {
				temp= new Point(start.getColumn(),start.getRow()+i);
				route.add(temp);
			}
			for(int i=1;i<Math.abs(diff_column);i++) {
				temp=new Point(temp.getColumn()+1,temp.getRow());
				route.add(temp);
			}
		}  
		else if(diff_column==0&&diff_row<0) {             /////////starting point is in the left of the end point
			for(int i=1;i<Math.abs(diff_row);i++) {
				temp= new Point(start.getColumn(),start.getRow()+i);
				route.add(temp);
			}
		}
		else if(diff_column==0&&diff_row>0) {               /////starting point is in the right of the end point
			for(int i=1;i<Math.abs(diff_row);i++) {
				temp= new Point(start.getColumn(),start.getRow()-i);
				route.add(temp);
			}
		}
		else if(diff_column<0&&diff_row==0) {    /////starting point is in the upward of the end point
			for(int i=1;i<Math.abs(diff_column);i++) {
				temp=new Point(temp.getColumn()+1,temp.getRow());
				route.add(temp);
			}
		}
		else {              /////starting point is in the downward of the end point
			for(int i=1;i<Math.abs(diff_column);i++) {
				temp=new Point(temp.getColumn()-1,temp.getRow());
				route.add(temp);
			}
		}            
	return route;
	}
	
	private static boolean canMove(Point c,int direction,int[][] arr) {/////move the blank space to the corresponding position on the route of
		//the moving number
		switch(direction) {
		case 0:
			if(c.getRow()>0)
				return (arr[c.getColumn()][c.getRow()-1]!=99);
			else
				return false;
		case 1:
			if(c.getRow()<arr.length-1)//depended on how large the board is
				return (arr[c.getColumn()][c.getRow()+1]!=99);
			else
				return false;
		case 2:
			if(c.getColumn()>0)
				return (arr[c.getColumn()-1][c.getRow()]!=99);
			else
				return false;
		case 3:
			if(c.getColumn()<arr.length-1)//depended on how large the board is
				return (arr[c.getColumn()+1][c.getRow()]!=99);
			else
				return false;
		}
		return false;  //////////Also need to set the moving numebr to be 99
		//return true;
	}
	
	public static LinkedList<Point> rightRotate(Point blank,Point number,Point end,int[][]arr){
		int position;
		Point temp=blank;
		LinkedList<Point> record=new LinkedList<Point>();
		//System.out.println(blank.getColumn()+" "+blank.getRow()+"''''''''''''''''");
		/*if(temp.getColumn()==number.getColumn()) {   ///blank and number are in the same column
			if(temp.getRow()<number.getRow()) {
				position=7;
			}
			else
				position=3;
		}
		else if(temp.getRow()==number.getRow()) {
			if(temp.getColumn()<number.getColumn()) {
				position=1;
			}
			else
				position=5;
		}
		else if(temp.getRow()<number.getRow()){
			if(temp.getColumn()<number.getColumn()) {
				position=8;
			}
			else
				position=6;
		}
		else {
			if(temp.getColumn()<number.getColumn()) {
				position=2;
			}
			else
				position=4;
		}*/
		//System.out.println(temp.getColumn()+" "+temp.getRow()+"888888");
		//System.out.println(end.getColumn()+" "+end.getRow());
		//int count=0;
		while(temp.getColumn()!=end.getColumn()||temp.getRow()!=end.getRow()) {
			//System.out.println(temp.getColumn()+" "+temp.getRow());
			if(temp.getColumn()==number.getColumn()) {   ///blank and number are in the same column
				if(temp.getRow()<number.getRow()) {
					position=7;
				}
				else
					position=3;
			}
			else if(temp.getRow()==number.getRow()) {
				if(temp.getColumn()<number.getColumn()) {
					position=1;
					//System.out.println(position);
				}
				else {
					position=5;
					//System.out.println("here");
				}
			}
			else if(temp.getRow()<number.getRow()){
				if(temp.getColumn()<number.getColumn()) {
					position=8;
				}
				else
					position=6;
			}
			else {
				if(temp.getColumn()<number.getColumn()) {
					position=2;
				}
				else
					position=4;
			}
			switch (position) {
			case 1:           ////move right
				if(canMove(temp,1,arr)) {
					 temp=new Point(temp.getColumn(),temp.getRow()+1);
					// System.out.println(temp.getColumn()+" "+temp.getRow()+"here");
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			case 2:     /////move down
				if(canMove(temp,3,arr)) {
					temp=new Point(temp.getColumn()+1,temp.getRow());
					record.add(temp);
					//System.out.println("ture");
					break;
				}
				else {
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			case 3:      ////move down
				if(canMove(temp,3,arr)) {
					temp=new Point(temp.getColumn()+1,temp.getRow());
					record.add(temp);
					//System.out.println(temp.getColumn()+" "+temp.getRow());
					break;
				}
				else {
					//System.out.println(temp.getColumn()+" "+temp.getRow());
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			case 4:          ////move left
				if(canMove(temp,0,arr)) {
					temp=new Point(temp.getColumn(),temp.getRow()-1);
					record.add(temp);
					//System.out.println("ture");
					break;
				}
				else {
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			case 5:        /////move left
				if(canMove(temp,0,arr)) {
					temp=new Point(temp.getColumn(),temp.getRow()-1);
					record.add(temp);
					//System.out.println("ture");
					break;
				}
				else {
					//System.out.println("hereeeeeeeeee");
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			case 6:         ///////move up
				if(canMove(temp,2,arr)) {
					temp=new Point(temp.getColumn()-1,temp.getRow());
					record.add(temp);
					//System.out.println("ture");
					break;
				}
				else {
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			case 7:       ////move up
				if(canMove(temp,2,arr)) {
					temp=new Point(temp.getColumn()-1,temp.getRow());
					record.add(temp);
					//System.out.println("ture");
					break;
				}
				else {
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			case 8:           /////move right
				if(canMove(temp,1,arr)) {
					temp=new Point(temp.getColumn(),temp.getRow()+1);
					record.add(temp);
					//System.out.println("ture");
					break;
				}
				else {
					record.removeAll(record);
					return leftRotate(blank,number,end,arr);
				}
			}
			//System.out.println(temp.getColumn()+" "+temp.getRow());
		}
		//record.add(temp);
		return record;
	}
	public static LinkedList<Point> leftRotate(Point blank,Point number,Point end,int[][] arr){
		int position;
		Point temp=blank;
		LinkedList<Point> record=new LinkedList<Point>();
		/*if(temp.getColumn()==number.getColumn()) {   ///blank and number are in the same column
			if(temp.getRow()<number.getRow()) {
				position=7;
			}
			else
				position=3;
		}
		else if(temp.getRow()==number.getRow()) {
			if(temp.getColumn()<number.getColumn()) {
				position=1;
			}
			else
				position=5;
		}
		else if(temp.getRow()<number.getRow()){
			if(temp.getColumn()<number.getColumn()) {
				position=8;
			}
			else
				position=6;
		}
		else {
			if(temp.getColumn()<number.getColumn()) {
				position=2;
			}
			else
				position=4;
		}*/
		while(temp.getColumn()!=end.getColumn()||temp.getRow()!=end.getRow()) {
			//System.out.println(temp.getColumn()+" "+temp.getRow());
			if(temp.getColumn()==number.getColumn()) {   ///blank and number are in the same column
				if(temp.getRow()<number.getRow()) {
					position=7;
				}
				else
					position=3;
			}
			else if(temp.getRow()==number.getRow()) {
				if(temp.getColumn()<number.getColumn()) {
					position=1;
				}
				else
					position=5;
			}
			else if(temp.getRow()<number.getRow()){
				if(temp.getColumn()<number.getColumn()) {
					position=8;
				}
				else
					position=6;
			}
			else {
				if(temp.getColumn()<number.getColumn()) {
					position=2;
				}
				else
					position=4;
			}
			switch (position) {
			case 1:        ///move left
				if(canMove(temp,0,arr)) {
					 temp=new Point(temp.getColumn(),temp.getRow()-1);
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			case 2:       ///move left
				if(canMove(temp,0,arr)) {
					temp=new Point(temp.getColumn(),temp.getRow()-1);
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			case 3:          ////move up
				if(canMove(temp,2,arr)) {
					temp=new Point(temp.getColumn()-1,temp.getRow());
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			case 4:        ///move up
				if(canMove(temp,2,arr)) {
					temp=new Point(temp.getColumn()-1,temp.getRow());
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			case 5:        ////move right
				if(canMove(temp,1,arr)) {
					temp=new Point(temp.getColumn(),temp.getRow()+1);
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			case 6:       ///move right
				if(canMove(temp,1,arr)) {
					temp=new Point(temp.getColumn(),temp.getRow()+1);
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			case 7:           //////move down
				if(canMove(temp,3,arr)) {
					temp=new Point(temp.getColumn()+1,temp.getRow());
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			case 8:        //////move down
				if(canMove(temp,3,arr)) {
					temp=new Point(temp.getColumn()+1,temp.getRow());
					record.add(temp);
					break;
				}
				else {
					record.removeAll(record);
					return rightRotate(blank,number,end,arr);
				}
			}
		}
		return record;
	}

	//Find the current position to the particular number or blank space;
		public static Point Find(int n,int[][] arr) {
			Point ans=new Point(0,0);
			for(int i=0;i<arr.length;i++) {
				for(int j=0;j<arr.length;j++) {
					if(arr[i][j]==n) {
						ans=new Point(i,j);
						break;
					}
				}
			}
			return ans;
		}
		//Find the correct position for a number
		public static Point findRightPosition(int n,int[][] arr) {
			int column=(n-1)/arr.length;
			int row=(n-1)%arr.length;
			Point ans=new Point(column,row);
			return ans;
		}
		
		//Move a basic number in the board to the right place
		public static LinkedList<Point> moveTheBasicNumber(int n,int[][] arr, int m){
			LinkedList<Point> record;
			LinkedList<Point> temp;
			LinkedList<Point> temp2;
			Point startNum=Find(n,arr);
			//System.out.println(startNum.getColumn()+" "+startNum.getRow());
			Point blank=Find(0,arr);            /////blank is 0
			//System.out.println(blank.getColumn()+" "+blank.getRow());
			Point end=findRightPosition(m,arr);
			//System.out.println(end.getColumn()+" "+end.getRow());
			temp=findNumberRoute(startNum,end);
			//Point p=temp.removeFirst();
			//System.out.println(p.getColumn()+"    "+p.getRow());
			int size=temp.size();
			//System.out.println(temp.size()+"size");
			/*for(int i=0;i<size;i++) {
				Point te=temp.removeFirst();
				System.out.println(te.getColumn()+" "+te.getRow()+"here");	
			}*/
				
			if(startNum.getColumn()==end.getColumn()&&startNum.getRow()==end.getRow()) {
				//System.out.print("here------------");
				return new LinkedList<Point>();
			}
			//System.out.println(blank.getColumn()+" "+blank.getRow()+'d'+startNum.getColumn()+" "+startNum.getRow());
			record=findBlankSpaceRoute(blank,startNum); ////////now the blank space is near the moving number
			//System.out.println(record.getLast().getColumn()+" "+record.getLast().getRow()+"213213213213213213213213213");
			//System.out.println("here");
			
			//int size2=record.size();
			//System.out.println(size2);
			
			/*for(int i=0;i<size2;i++) {
				Point te=record.removeFirst();
				System.out.println(te.getColumn()+" "+te.getRow());	
			}*/
			
			if(!record.isEmpty()) {
				blank=record.getLast();
				//System.out.println(blank.getColumn()+" "+blank.getRow()+"{{{{{{{{{{{{{");
			}
			for(int i=0;i<size;i++) {        //After the temp.size is empty, the number has finished its moving and is placed in the roght place
				Point te=temp.removeFirst();
				//System.out.println(blank.getColumn()+" "+blank.getRow()+"---------blank------");
				//System.out.println(startNum.getColumn()+" "+startNum.getRow()+"---------startNum-------");
				//System.out.println(te.getColumn()+" "+te.getRow()+"---------te------");
				temp2=rightRotate(blank,startNum,te,arr);//get the blank space to the first point that the numebr should  move to
				if(temp2.isEmpty()) {
					//System.out.println("here");
					
					Point t=blank;
					blank=startNum;
					startNum=t;
					record.add(blank);
					continue;
				}
				record.addAll(temp2);                //add the blank space movements to the record
				blank=startNum;						 //move the number
				startNum=record.getLast();           //move the number and update the blank, number position
				record.add(blank);            //add the number movement to the record;
				
				//System.out.println(blank.getColumn()+" "+blank.getRow()+"---------blank------");
				//System.out.println(startNum.getColumn()+" "+startNum.getRow()+"---------startNum-------");
			}
			return record;
		}
		
		public static void changing2DArray(int[][] arr) {
			arr[0][0]=2;
		}
		public static void swap(int x,int y,int[][] arr) {
			Point a=Find(x,arr);
			Point b=Find(y,arr);
			Point temp;
			temp=a;
			a=b;
			b=temp;
		}
		
		public static void MovingTheBoard(int[][] arr, LinkedList<Point> record,FileWriter myWriter) throws IOException {
			Point blank;
			blank=Find(0,arr);
			Point temp;
			/*for(int i=0;i<arr.length;i++) {
				for(int j=0;j<arr.length;j++) {
					System.out.print(arr[i][j]+" ");
				}
				System.out.println();
			}
			System.out.println("------------------------------");*/
			while(!record.isEmpty()) {
				temp=record.removeFirst();
				//System.out.println(temp.getColumn()+" "+temp.getRow()+"]]]]]]]]]]]]]]]]]");
				if(temp.getColumn()<blank.getColumn()) {
					int n=arr[temp.getColumn()][temp.getRow()];
					myWriter.write(n+" D ");
					myWriter.write("\n");
				}
				else if(temp.getColumn()>blank.getColumn()) {
					int n=arr[temp.getColumn()][temp.getRow()];
					myWriter.write(n+" U ");
					myWriter.write("\n");
				}
				else if(temp.getRow()<blank.getRow()) {
					int n=arr[temp.getColumn()][temp.getRow()];
					myWriter.write(n+" R ");
					myWriter.write("\n");
				}
				else {
					int n=arr[temp.getColumn()][temp.getRow()];
					myWriter.write(n+" L ");
					myWriter.write("\n");
				}
				int tem=arr[temp.getColumn()][temp.getRow()];
				arr[temp.getColumn()][temp.getRow()]=0;
				arr[blank.getColumn()][blank.getRow()]=tem;
				blank=temp;                                               //needs check
				for(int i=0;i<arr.length;i++) {
					for(int j=0;j<arr.length;j++) {
						System.out.print(arr[i][j]+" ");
					}
					System.out.println();
				}
				System.out.println("------------------------------");
			}
		}     
		
		
		public static LinkedList<Point> moveTheLastTwoRow(int n,int[][] arr,int m,FileWriter myWriter) throws IOException{     //still needs to consider more cases!!!
			LinkedList<Point> record=new LinkedList<Point>();
			record=moveTheBasicNumber(n,arr,m);          //move n to "m" (n's next number) first
			if(record.isEmpty()) {
				//System.out.println("hereeeee");
				record=new LinkedList<Point>();
			}
			LinkedList<Point> temp;
			Point nCorrectPoint=findRightPosition(n,arr);
			Point mCurrentPoint=Find(m,arr);
			Point nCurrentPoint=Find(n,arr);
			Point mCorrectPoint=findRightPosition(m,arr);
			Point blank;
			blank=Find(0,arr);
			//System.out.println(blank.getColumn()+" "+blank.getRow()+"[[[[[[[[[");
			/*if(nCurrentPoint.isEqual(mCorrectPoint)&&mCurrentPoint.getColumn()==mCorrectPoint.getColumn()+1
					&&mCurrentPoint.getRow()==mCorrectPoint.getRow()&&blank.isEqual(nCorrectPoint))
				System.out.println("true");*/
			/*System.out.println(nCorrectPoint.getColumn()+" "+nCorrectPoint.getRow()+"---"
					+nCurrentPoint.getColumn()+" "+nCurrentPoint.getRow()+"---"
					+mCorrectPoint.getColumn()+" "+mCorrectPoint.getRow()+"---"
					+mCurrentPoint.getColumn()+" "+mCurrentPoint.getRow()+"hereeeee"
					+blank.getColumn()+" "+blank.getRow());*/
			if(mCurrentPoint.isEqual(mCorrectPoint)&&nCorrectPoint.isEqual(nCurrentPoint)) {System.out.print("here1");return new LinkedList<Point>();}          //no need to move
			if(nCorrectPoint.isEqual(nCurrentPoint)&&blank.isEqual(mCorrectPoint)&&mCurrentPoint.getColumn()==mCorrectPoint.getColumn()+1
					&&mCurrentPoint.getRow()==mCorrectPoint.getRow()) {                           //only one move
				System.out.print("here2");
				record.removeAll(record);
				Point t1=new Point(mCurrentPoint.getColumn(),mCurrentPoint.getRow());
				record.add(t1);
				return record;
			}
			if(nCurrentPoint.isEqual(mCorrectPoint)&&mCurrentPoint.getColumn()==mCorrectPoint.getColumn()+1
					&&mCurrentPoint.getRow()==mCorrectPoint.getRow()&&blank.isEqual(nCorrectPoint)){        //only need two move
				System.out.print("here3");
				//System.out.println("hereeeee");
				record.removeAll(record);
				Point t1=new Point(nCurrentPoint.getColumn(),nCurrentPoint.getRow());
				Point t2=new Point(mCurrentPoint.getColumn(),mCurrentPoint.getRow());
				record.add(t1);
				record.add(t2);
				return record;
			}
			
			if(!record.isEmpty()) {
				MovingTheBoard(arr,record,myWriter);
				mCurrentPoint=Find(m,arr);
				nCurrentPoint=Find(n,arr);
				blank=Find(0,arr);     
			}                             
			
			/*if(record!=null) {
				System.out.println("here*");
				for(int i=0;i<record.size();i++) {
					Point a=record.removeFirst();
					System.out.print(a.getColumn()+" "+a.getRow());
				}
				MovingTheBoard(arr,record);
			}*/
			//System.out.print("here===============");
			/*mCurrentPoint=Find(m,arr);
			nCurrentPoint=Find(n,arr);
			blank=Find(0,arr);*/
			//System.out.println(mCurrentPoint.getColumn()+" "+mCurrentPoint.getRow()+" "+nCurrentPoint.getColumn()+" "+nCurrentPoint.getRow()+" "+
			//															blank.getColumn()+" "+blank.getRow());
			if(nCorrectPoint.getColumn()==mCurrentPoint.getColumn()&&nCorrectPoint.getRow()==mCurrentPoint.getRow()) {             //special case
				//blank=Find(0,arr);
				System.out.println("here4");
				Point t=new Point(mCorrectPoint.getColumn()+1,mCorrectPoint.getRow());
				record.addAll(findNumberRoute(blank,t));
				MovingTheBoard(arr,record,myWriter);
				blank=Find(0,arr);
				//System.out.println(blank.getColumn()+" 00000000 "+blank.getRow());
				//System.out.println(t.getColumn()+" "+t.getRow()+";;;;;;;;;;;");
				LinkedList<Integer> specialCase=new LinkedList<>(List.of(0,2,1,3,0,3,1,2,2,0,3,1,3));   //should be OK(special solution to this case)
				record.addAll(movementOfBlankSpace(specialCase,blank));
			}
			else if(blank==nCorrectPoint && mCurrentPoint.getColumn()==blank.getColumn()+1 && mCurrentPoint.getRow()==blank.getRow()) {//m is under blank 
				System.out.print("here5");
				//MovingTheBoard(arr,record);
				LinkedList<Integer> specialCase=new LinkedList<>(List.of(1,3,0,3,1,2,2,0,3,1,3));   //should be OK(special solution to this case 2)
				record.addAll(movementOfBlankSpace(specialCase,blank));
			}
			
			else {
				
				System.out.print("here6");
				temp=moveTheBasicNumber(m,arr,m+arr.length);                     //in case record is null!!!
				if(!temp.isEmpty())
				record.addAll(temp);
				//System.out.println(record.size()+"sizeeeeeeeeeeeeeeeeeeeeee");
			}
			MovingTheBoard(arr,record,myWriter);
			mCurrentPoint=Find(m,arr);
			blank=Find(0,arr);
			Point t=new Point(mCurrentPoint.getColumn(),mCurrentPoint.getRow()-1);
			record.addAll(findNumberRoute(blank,t));
			//System.out.println(record.size()+"sizeeeeeeeeeeeeeeeeeeeeee");
			/*if(blank.getRow()==mCurrentPoint.getRow()) {               //the blank space is under number m
				//System.out.println("here7");
				Point tem=new Point(blank.getColumn(),blank.getRow()-1);
				Point tem2=new Point(blank.getColumn()-1,blank.getRow()-1);
				record.add(tem);
				record.add(tem2);
			}*/
			if(!record.isEmpty())
				blank=record.getLast();
			LinkedList<Integer> direction=new LinkedList<Integer>();
			direction.add(2);
			direction.add(1);
			direction.add(3);                             //move the blank space to finish moving n and m to their right position (Last Step)
			record.addAll(movementOfBlankSpace(direction,blank));
			
			return record;
		}
		
		//Helper function: needs a LinkedList of the blank space movement directions and the initial position,
		//return the record(LinkedList) of it's movements
		public static LinkedList<Point> movementOfBlankSpace(LinkedList<Integer> m, Point blank)throws IOException{
			LinkedList<Point> record=new LinkedList<Point>();          ///should be fine, first initialize with zero element, then add element.
			Point temp=blank;
			Point tem;
			int direction;
			while(!m.isEmpty()) {
				direction=m.removeFirst();
				switch(direction) {
				case 0:                      //move left
					//System.out.println("here((((((");
					tem=new Point(temp.getColumn(),temp.getRow()-1);
					record.add(tem);
					temp=tem;
					break;
				case 1:                      //move right
					tem=new Point(temp.getColumn(),temp.getRow()+1);
					record.add(tem);
					temp=tem;
					break;
				case 2:                      //move up
					tem=new Point(temp.getColumn()-1,temp.getRow());
					record.add(tem);
					temp=tem;
					break;
				case 3:                      //move down
					tem=new Point(temp.getColumn()+1,temp.getRow());
					record.add(tem);
					temp=tem;
					break;
				}
			}
			return record;
		}
		
		//Helper Function: Move the blank space in the last two column
		public static LinkedList<Point> MoveBlankSpaceInTheLastTwoColumn(Point blank,Point end,int[][] arr){
			int diff_column;
			int diff_row;
			LinkedList<Point> record=new LinkedList<Point>();
			diff_column=blank.getColumn()-end.getColumn();
			diff_row=blank.getRow()-end.getRow();
			int position;
			if(diff_row<0) {                               //blank space is on the left of the end point
				while(Math.abs(diff_row)!=0) {
					Point p=new Point(blank.getColumn(),blank.getRow()+1);
					if(arr[p.getColumn()][p.getRow()]!=99) {
						record.add(p);
						blank=p;
						diff_row++;                             //since diff_row is < 0 at first
					}
					else {
						Point b=new Point(blank.getColumn(),blank.getRow()+2);
						record.addAll(rightRotate(blank,p,b,arr));
						blank=b;
						diff_row=blank.getRow()-end.getRow();
					}
				}
			}
			else if(diff_row>0) {                
				while(Math.abs(diff_row)!=0) {                  //blank space is on the right of the point
					Point p=new Point(blank.getColumn(),blank.getRow()-1);
					if(arr[p.getColumn()][p.getRow()]!=99) {
						record.add(p);
						blank=p;
						diff_row--;                             //since diff_row is > 0 at first
					}
					else {
						Point b=new Point(blank.getColumn(),blank.getRow()-2);
						record.addAll(rightRotate(blank,p,b,arr));
						blank=b;
						diff_row=blank.getRow()-end.getRow();
					}
				}
			}                                                     //now the blank space and the end point is in the same row
			diff_row=blank.getRow()-end.getRow();
			if(diff_column>0) {                                      //blank space is above the end point
				//Point p1=new Point(end.getColumn()+1,end.getRow());
				record.add(end);
			}
			else if(diff_column<0) {
				//Point p1=new Point(end.getColumn()-1,end.getRow());
				record.add(end);
			}
			return record;
		}
		
		//Helper Function: Move the two pair Point to be in the same row
		public static LinkedList<Point> MoveToTheSameRow(Point x,Point y,int[][] arr,FileWriter myWriter) throws IOException{                          //seems ok
			LinkedList<Point> record=new LinkedList<Point>();
			int diff_row=x.getRow()-y.getRow();
			int numX=arr[x.getColumn()][x.getRow()];
			int numY=arr[y.getColumn()][y.getRow()];
			int temp1;
			int temp2;
			Point blank=Find(0,arr);
			Point right=x.getRow()>=y.getRow() ? x : y;
			Point left=x.getRow()<y.getRow() ? x : y;
			System.out.println(right.getColumn()+" "+right.getRow());
			System.out.println(left.getColumn()+" "+left.getRow());                        //OK
			if(x.getRow()==y.getRow()) {
				System.out.println("here+++++++++++");
				return new LinkedList<Point>();
			}
			if(Math.abs(diff_row)>=2){                                    //two points are seperated
				//Point right=x.getRow()>=y.getRow() ? x : y;
				//Point left=x.getRow()<y.getRow() ? x : y;
				if(right.getColumn()==left.getColumn()) {
					System.out.println("here123123123");
					if(right.getColumn()==arr.length-1) {                  //both points are in the bottom line of the board
						Point p=new Point(right.getColumn()-1,right.getRow());
						if(blank.getColumn()==left.getColumn()){
							Point t=new Point(blank.getColumn()-1,blank.getRow());
							record.add(t);
							blank=t;
						}
						temp1=arr[right.getColumn()][right.getRow()];
						temp2=arr[left.getColumn()][left.getRow()];
						arr[right.getColumn()][right.getRow()]=99;
						arr[left.getColumn()][left.getRow()]=99;
						record.addAll(MoveBlankSpaceInTheLastTwoColumn(blank,p,arr));           //OK so far
						arr[right.getColumn()][right.getRow()]=temp1;
						arr[left.getColumn()][left.getRow()]=temp2;
						record.add(right);
						blank=right;
						right=p;                                           //move the left point to the different column
					}
					else {                                                //two Points are in the n-1 column on the board
						Point p=new Point(left.getColumn()+1,left.getRow());
						if(blank.getColumn()==left.getColumn()){
							Point t=new Point(blank.getColumn()+1,blank.getRow());
							record.add(t);
							blank=t;
						}
						temp1=arr[right.getColumn()][right.getRow()];
						temp2=arr[left.getColumn()][left.getRow()];
						arr[right.getColumn()][right.getRow()]=99;
						arr[left.getColumn()][left.getRow()]=99;
						record.addAll(MoveBlankSpaceInTheLastTwoColumn(blank,p,arr));           //OK so far
						arr[right.getColumn()][right.getRow()]=temp1;
						arr[left.getColumn()][left.getRow()]=temp2;
						record.add(left);
						blank=left;
						left=p;
					}
				}
				MovingTheBoard(arr,record,myWriter);
				blank=Find(0,arr);
				x=Find(numX,arr);
				y=Find(numY,arr);
				right=x.getRow()>=y.getRow() ? x : y;
				left=x.getRow()<y.getRow() ? x : y;
				int l=arr[left.getColumn()][left.getRow()];
				int r=arr[right.getColumn()][right.getRow()];                     //the number on the current point
				System.out.println(right.getColumn()+" "+right.getRow()+"-------");
				
				
				//Now the two points are in the different column                     with the shape like * * * * ...2
				                                                                                      // 1 * * * ...
				
				
				Point p1=new Point(right.getColumn(),left.getRow()+1);               //this is the position we should move the right point to
				LinkedList<Point> temp=new LinkedList<Point>();
				temp.addAll(findNumberRoute(right,p1));
				Point p2=new Point(right.getColumn(),right.getRow()-1);
				temp1=arr[right.getColumn()][right.getRow()];
				temp2=arr[left.getColumn()][left.getRow()];
				arr[right.getColumn()][right.getRow()]=99;
				arr[left.getColumn()][left.getRow()]=99;
				record.addAll(MoveBlankSpaceInTheLastTwoColumn(blank,p2,arr));           //OK so far
				arr[right.getColumn()][right.getRow()]=temp1;
				arr[left.getColumn()][left.getRow()]=temp2;          //seems ok
				blank=p2;
		    	int a=p1.getColumn()*arr.length+p1.getRow()+1;                                  //this is the correct number in this point
		    	System.out.println(r+"------------");
		    	System.out.println(a+"----------");
		    	System.out.println(p1.getColumn()+"      "+p1.getRow()+"----------");
				MovingTheBoard(arr,record,myWriter);
				blank=Find(0,arr);
				x=Find(numX,arr);
				y=Find(numY,arr);
				right=x.getRow()>=y.getRow() ? x : y;
				left=x.getRow()<y.getRow() ? x : y;
				System.out.println(r+"------------====================");
		    	record.addAll(moveTheBasicNumber(r,arr,a));                          //Now the two pair poionts are like:
																					 //     * * 1 *       or       * 1 * *
																					 //		* 2 *                  * * 2 *
				//System.out.println(record.getLast().getColumn()+" "+record.getLast().getRow()+"========="+record.size());
				MovingTheBoard(arr,record,myWriter);
				blank=Find(0,arr);
				x=Find(numX,arr);
				y=Find(numY,arr);
				right=x.getRow()>=y.getRow() ? x : y;
				left=x.getRow()<y.getRow() ? x : y;
				
			}
			
			if(right.getColumn()==left.getColumn()) {
				System.out.println("heeeeere");
				if(blank.getColumn()==right.getColumn()) {
					if(right.getColumn()==arr.length-2) {
						Point t=new Point(blank.getColumn()+1,blank.getRow());
						record.add(t);
						blank=t;
					}
					else {
						Point t=new Point(blank.getColumn()-1,blank.getRow());
						record.add(t);
						blank=t;
					}
				}
				Point p=new Point(blank.getColumn(),right.getRow());
				temp1=arr[right.getColumn()][right.getRow()];
				temp2=arr[left.getColumn()][left.getRow()];
				arr[right.getColumn()][right.getRow()]=99;
				arr[left.getColumn()][left.getRow()]=99;
				record.addAll(MoveBlankSpaceInTheLastTwoColumn(blank,p,arr));           //OK so far
				arr[right.getColumn()][right.getRow()]=temp1;
				arr[left.getColumn()][left.getRow()]=temp2;
				record.add(right);
			}
			MovingTheBoard(arr,record,myWriter);
			blank=Find(0,arr);
			x=Find(numX,arr);
			y=Find(numY,arr);
			right=x.getRow()>=y.getRow() ? x : y;
			left=x.getRow()<y.getRow() ? x : y;
			if(blank.getRow()==right.getRow()&&blank.getColumn()==left.getColumn()) {
				record.add(left);
				return record;
			}
			else if(blank.getRow()==left.getRow()&&blank.getColumn()==right.getColumn()) {
				record.add(right);
				return record;
			}
			Point p4=new Point(left.getColumn(),right.getRow());
			Point p5=new Point(right.getColumn(),left.getRow());
			if(blank.getColumn()==right.getColumn()) {
				if(blank.getRow()>right.getRow()) {
					System.out.println("here1");
					Point p3=new Point(left.getColumn(),blank.getRow());
					record.add(p3);
					record.addAll(findNumberRoute(p3,p4));
					//System.out.println("here1");
				}	
				else {
					System.out.println("here2");
					record.addAll(findNumberRoute(blank,p5));
					//System.out.println("here1");
				}
			}
			else {
				if(blank.getRow()<left.getRow()) {
					System.out.println("here3");
					Point p3=new Point(right.getColumn(),blank.getRow());
					record.add(p3);
					record.addAll(findNumberRoute(p3,p5));
				}	
				else {
					System.out.println("here4");
					record.addAll(findNumberRoute(blank,p4));
				}
			}
			MovingTheBoard(arr,record,myWriter);
			blank=Find(0,arr);
			x=Find(numX,arr);
			y=Find(numY,arr);
			right=x.getRow()>=y.getRow() ? x : y;
			left=x.getRow()<y.getRow() ? x : y;
			if(blank.getRow()==right.getRow()) {
				record.add(left);
			}
			else
				record.add(right);
			return record;
		}
		
		//Moving the number in last two column( moving in pairs)
		public static LinkedList<Point> MovingTheLastTwoColumnNumber(int n,int m,int[][] arr,FileWriter myWriter) throws IOException{            //we assume m > n
			//Point p1=Find(n,arr);
			//Point p2=Find(m,arr);
			LinkedList<Point> record=new LinkedList<Point>();
			/*record=moveTheBasicNumber(n,arr,m);          //move n to "m" (n's next number) first
			if(record==null) {
				//System.out.println("hereeeee");
				record=new LinkedList<Point>();
			}*/
			LinkedList<Point> temp;
			Point nCorrectPoint=findRightPosition(n,arr);
			Point mCurrentPoint=Find(m,arr);
			Point nCurrentPoint=Find(n,arr);
			Point mCorrectPoint=findRightPosition(m,arr);
			Point blank=Find(0,arr);
			System.out.println("moving here1");
			//System.out.println(nCorrectPoint.getColumn()+" "+nCorrectPoint.getRow());
			//System.out.println(mCorrectPoint.getColumn()+" "+mCorrectPoint.getRow());
			//System.out.println(nCurrentPoint.getColumn()+" "+nCurrentPoint.getRow());
			//System.out.println(mCurrentPoint.getColumn()+" "+mCurrentPoint.getRow());
			//System.out.println(blank.getColumn()+" "+blank.getRow());
			if(nCurrentPoint.isEqual(nCorrectPoint)&&mCurrentPoint.isEqual(mCorrectPoint)) {return new LinkedList<Point>();}         //case 1
			else if(mCorrectPoint.isEqual(mCurrentPoint)&&blank.isEqual(nCorrectPoint)&&nCurrentPoint.getColumn()==nCorrectPoint.getColumn()
					&&nCurrentPoint.getRow()==nCorrectPoint.getRow()+1) {                             //case 2
				System.out.println("here1");
				record.removeAll(record);
				Point t1=new Point(nCurrentPoint.getColumn(),nCurrentPoint.getRow());
				record.add(t1);
				return record;
			}
			else if(mCurrentPoint.isEqual(nCorrectPoint)&&nCurrentPoint.getColumn()==nCorrectPoint.getColumn()
						&&nCurrentPoint.getRow()==nCorrectPoint.getRow()+1&&blank.isEqual(mCorrectPoint)) {          //case 3
				System.out.println("here2");
				record.removeAll(record);
				Point t1=new Point(mCurrentPoint.getColumn(),mCurrentPoint.getRow());
				Point t2=new Point(nCurrentPoint.getColumn(),nCurrentPoint.getRow());
				record.add(t1);
				record.add(t2);
				return record;
			}
			else if(nCorrectPoint.isEqual(nCurrentPoint)&&blank.isEqual(mCorrectPoint)&&mCurrentPoint.getColumn()==mCorrectPoint.getColumn()
					&&mCurrentPoint.getRow()==mCorrectPoint.getRow()+1){                 //case 4
				System.out.println("here3");
				record.removeAll(record);
				Point t1=new Point(mCurrentPoint.getColumn(),mCurrentPoint.getRow());
				record.add(t1);
				return record;
				}
			else if(mCorrectPoint.isEqual(nCurrentPoint)&&blank.isEqual(nCorrectPoint)&&mCurrentPoint.getColumn()==mCorrectPoint.getColumn()
					&&mCurrentPoint.getRow()==mCorrectPoint.getRow()+1) {                   //case 5
				System.out.println("here4");
				record.removeAll(record);
				Point t1=new Point(nCurrentPoint.getColumn(),nCurrentPoint.getRow());
				Point t2=new Point(mCurrentPoint.getColumn(),mCurrentPoint.getRow());
				record.add(t1);
				record.add(t2);
				return record;
			}                                                    // These are the cases that don't need much movements            <<<OK>>>
			
			record.addAll(MoveToTheSameRow(nCurrentPoint,mCurrentPoint,arr,myWriter));
			MovingTheBoard(arr,record,myWriter);
			mCurrentPoint=Find(m,arr);
			nCurrentPoint=Find(n,arr);
			blank=Find(0,arr);
			Point u=mCurrentPoint.getColumn()<nCurrentPoint.getColumn() ? mCurrentPoint : nCurrentPoint;
			Point d=mCurrentPoint.getColumn()>=nCurrentPoint.getColumn() ? mCurrentPoint : nCurrentPoint;
			System.out.println(u.getColumn()+" "+u.getRow()+" U "+d.getColumn()+" "+d.getRow()+" D "+blank.getColumn()+" "+blank.getRow()+" blank ");
			if(nCurrentPoint.getRow()==arr.length-1) {
				//System.out.println("here-----------");
				record.addAll(MoveTheTwoPairPointsToAnotherRow(u,d,nCurrentPoint.getRow(),nCurrentPoint.getRow()-2,arr,myWriter));
			}
			else if(nCurrentPoint.getRow()==arr.length-2) {
				//System.out.println("here============");
				record.addAll(MoveTheTwoPairPointsToAnotherRow(u,d,nCurrentPoint.getRow(),nCurrentPoint.getRow()-1,arr,myWriter));
			}                                                                                                                     //seems ok
			//Now the two numbers are in the same row
			MovingTheBoard(arr,record,myWriter);
			mCurrentPoint=Find(m,arr);
			nCurrentPoint=Find(n,arr);
			blank=Find(0,arr);
			LinkedList<Integer> specialCase;
			System.out.println("moving here2");
			if(mCurrentPoint.isEqual(mCorrectPoint)&&nCurrentPoint.isEqual(nCorrectPoint)) {return new LinkedList<Point>();}
			if(mCurrentPoint.getColumn()==nCurrentPoint.getColumn()-1&&mCurrentPoint.getRow()==nCurrentPoint.getRow()) {      
																															   //13
				System.out.println("hereeeeeeeee");                                                                            // 9
				if(blank.getRow()<mCurrentPoint.getRow()) {             //blank is on the left
					/*if(blank.getColumn()>mCurrentPoint.getColumn()) {     //blank is on the left-down
						Point p=new Point(blank.getColumn()-1,blank.getRow());
						record.add(p);
						blank=p;
					}*/
					//MovingTheBoard(arr,record);                      //update the blank space
					record.addAll(MoveTheTwoPairPointsToAnotherRow(mCurrentPoint,nCurrentPoint,mCurrentPoint.getRow(),nCorrectPoint.getRow(),arr,myWriter));
					return record;
				}
				else {                                                              //blank is on the right
					if(blank.getColumn()+1==nCurrentPoint.getColumn()) {            //blank is on the right-up                           
						record.addAll(findNumberRoute(blank,mCurrentPoint));
						if(!record.isEmpty())
							record.removeLast();
						if(!record.isEmpty())
							blank=record.getLast();
						specialCase=new LinkedList<>(List.of(3,0));   //should be OK(special solution to this case)
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					if(blank.getColumn()==nCurrentPoint.getColumn()) {                       
						record.addAll(findNumberRoute(blank,nCurrentPoint));
						if(!record.isEmpty())
							record.removeLast();
						if(!record.isEmpty())
							blank=record.getLast();
						specialCase=new LinkedList<>(List.of(0));   //should be OK(special solution to this case)
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					specialCase=new LinkedList<>(List.of(2,1,3,1,2,0,0,3,1,2,1));
					if(!record.isEmpty())
						blank=record.getLast();
					record.addAll(movementOfBlankSpace(specialCase,blank));
					/*MovingTheBoard(arr,record);
					mCurrentPoint=Find(m,arr);
					nCurrentPoint=Find(n,arr);
					blank=Find(0,arr);
					record.addAll(BigRotate(mCurrentPoint,nCurrentPoint,mCurrentPoint.getRow(),arr));*/
				}
			}
			else {                                                                                                              // 9
				       //System.out.println("here11");                                                                            //13
				if(blank.getRow()>mCurrentPoint.getRow()) {                  //blank is on the right
					//System.out.println("here11");
					if(blank.getColumn()<mCurrentPoint.getColumn()) {            //blank is on the right-up
						//System.out.println("here11");
						Point p=new Point(blank.getColumn()+1,blank.getRow());
						record.add(p);
						blank=p;
					}
					MovingTheBoard(arr,record,myWriter);                                  //update the blank space
					record.addAll(MoveTheTwoPairPointsToAnotherRow(nCurrentPoint,mCurrentPoint,mCurrentPoint.getRow(),nCorrectPoint.getRow(),arr,myWriter));
					return record;
				}
				else {
					if(blank.getColumn()+1==mCurrentPoint.getColumn()) {                                              
						record.addAll(findNumberRoute(blank,nCurrentPoint));
						if(!record.isEmpty())
							record.removeLast();
						if(!record.isEmpty())
							blank=record.getLast();
						specialCase=new LinkedList<>(List.of(3,1,2,0,3));   //should be OK(special solution to this case)
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					if(blank.getColumn()==mCurrentPoint.getColumn()) {
						record.addAll(findNumberRoute(blank,mCurrentPoint));
						if(!record.isEmpty())
							record.removeLast();
						if(!record.isEmpty())
							blank=record.getLast();
						specialCase=new LinkedList<>(List.of(1,2,0,3));   //should be OK(special solution to this case)
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					specialCase=new LinkedList<>(List.of(2,1,3,1,2,0,0,3,1,2,0,1,1));
					if(!record.isEmpty())
						blank=record.getLast();
					record.addAll(movementOfBlankSpace(specialCase,blank));
				}
			}
				MovingTheBoard(arr,record,myWriter);
				mCurrentPoint=Find(m,arr);
				nCurrentPoint=Find(n,arr);
				blank=Find(0,arr);
				if(nCurrentPoint.getColumn()==arr.length-2) {
					record.addAll(BigRotate(mCurrentPoint,nCurrentPoint,mCurrentPoint.getRow()-mCorrectPoint.getRow(),arr));
					if(!record.isEmpty())
						blank=record.getLast();
					specialCase=new LinkedList<>(List.of(3,0,0,2,1));   //special case
					record.addAll(movementOfBlankSpace(specialCase,blank));
				}
				else {
					record.addAll(BigRotate(nCurrentPoint,mCurrentPoint,nCurrentPoint.getRow()-nCorrectPoint.getRow(),arr));
					if(!record.isEmpty())
						blank=record.getLast();
					specialCase=new LinkedList<>(List.of(2,0,0,3,1));   //special case
					record.addAll(movementOfBlankSpace(specialCase,blank));
				}
			return record;
		}
		
		//Helper Function: Move the pair two points from a row to another row
		public static LinkedList<Point> MoveTheTwoPairPointsToAnotherRow(Point x,Point y,int r1,int r2,int[][] arr,FileWriter myWriter)throws IOException{     //we assume r1 > r2, x is above y
			LinkedList<Point> record=new LinkedList<Point>();
			int diff_row=r1-r2;
			Point blank=Find(0,arr);
			Point f=x;
			Point b=y;
			int numX=arr[x.getColumn()][x.getRow()];
			int numY=arr[y.getColumn()][y.getRow()];
			/*if(blank.getRow()<x.getRow()) {                            //blank is on the left side of the numbers
				diff_row--;
				f=x;
			}*/
			if(blank.getColumn()==x.getColumn()) {
				record.addAll(findBlankSpaceRoute(blank,x));
				record.add(x);
				record.add(y);
				MovingTheBoard(arr,record,myWriter);
				x=Find(numX,arr);
				y=Find(numY,arr);
				blank=Find(0,arr);
				//System.out.println(x.getColumn()+" "+x.getRow()+" X "+y.getColumn()+" "+y.getRow()+" Y "+blank.getColumn()+" "+blank.getRow()+" blank ");
				if(blank.getRow()>x.getRow()) {                                     //     * * x y
					                                                                //     * * * * 
					//Point te=new Point(x.getColumn(),x.getRow()-1);
					f=x;
					b=y;
					diff_row--;
					if(diff_row==0) {
						//System.out.println("<<<<<<<<>>>>>");
						LinkedList<Integer> specialCase=new LinkedList<>(List.of(0,2,1));   //special case
						record.addAll(movementOfBlankSpace(specialCase,blank));
						return record;
					}
					MovingTheBoard(arr,record,myWriter);
					record.addAll(BigRotate(f,b,diff_row,arr));
				}
				//MovingTheBoard(arr,record);
				else{                                                                //   * * y x
					                                                                 //   * * * *
					//Point te=new Point(x.getColumn(),x.getRow()+1);
					f=y;
					b=x;
					MovingTheBoard(arr,record,myWriter);
					record.addAll(BigRotate(f,b,diff_row,arr));
				}
				MovingTheBoard(arr,record,myWriter);
				blank=Find(0,arr);
				LinkedList<Integer> specialCase=new LinkedList<>(List.of(3,0,0,2,1));   //special case
				record.addAll(movementOfBlankSpace(specialCase,blank));
			}
			else {                                                                   
				record.addAll(findBlankSpaceRoute(blank,y));
				record.add(y);
				record.add(x);
				MovingTheBoard(arr,record,myWriter);
				x=Find(numX,arr);
				y=Find(numY,arr);
				blank=Find(0,arr);
				if(blank.getRow()>y.getRow()) {                                      //   * * * *
					                                                                 //   * * y x
					//Point te=new Point(y.getColumn(),y.getRow()-1);
					f=y;
					b=x;
					diff_row--;
					if(diff_row==0) {
						LinkedList<Integer> specialCase=new LinkedList<>(List.of(0,3,1));   //special case
						record.addAll(movementOfBlankSpace(specialCase,blank));
						return record;
					}
					MovingTheBoard(arr,record,myWriter);
					record.addAll(BigRotate(f,b,diff_row,arr));
				}
				else{                                                                  //   * * * *
					                                                                   //   * * x y
					//Point te=new Point(y.getColumn(),y.getRow()+1);
					f=x;
					b=y;
					MovingTheBoard(arr,record,myWriter);
					record.addAll(BigRotate(f,b,diff_row,arr));
				}
				MovingTheBoard(arr,record,myWriter);
				blank=Find(0,arr);
				LinkedList<Integer> specialCase=new LinkedList<>(List.of(2,0,0,3,1));   //special case
				record.addAll(movementOfBlankSpace(specialCase,blank));
			}
			return record;
		}
		
		//Helper Function: The rotation on the last two column
		public static LinkedList<Point> BigRotate(Point front,Point back,int num,int[][] arr)throws IOException{              //this time the two numbers are in the same column  Assume the blank space
			                                                                      //is on the right side of the moving numbers
			LinkedList<Point> record=new LinkedList<Point>();
			Point f=front;
			Point b=back;
			//Point en=new Point(f.getColumn(),f.getRow()-1);
			Point blank=Find(0,arr);
			//System.out.println("moving here");
			if(f.getColumn()==arr.length-2) {       //in the n-1 column
				while(num!=0) {
					Point s=blank;
					if(s.getColumn()==f.getColumn()&&s.getRow()==b.getRow()+1) {                           // blank is on the left side of the back number
						Point t=new Point(blank.getColumn()+1,blank.getRow());
						record.add(t);
						s=t;
					}
					/*else if(s.getColumn()==f.getColumn()&&s.getRow()==f.getRow()-1) {
						record.add(f);
						record.add(b);
						Point e=new Point(f.getColumn(),f.getRow()-1);
						b=f;
						f=e;
						num--;
						continue;
					}*/
				//Point t=new Point(blank.getColumn()+1,blank.getRow());
				Point e=new Point(f.getColumn(),f.getRow()-1);
				//record.add(t);
				record.addAll(findNumberRoute(s,e));
				record.add(f);
				record.add(b);
				blank=b;
				b=f;
				f=e;
				num--;
				}
			}
			else {                                                                     //in the last column
				while(num!=0) {
				Point s=blank;
				//System.out.println("moving here");
				//System.out.println(s.getColumn()+" "+s.getRow());
				if(s.getColumn()==f.getColumn()&&s.getRow()==b.getRow()+1) {
					Point t=new Point(blank.getColumn()-1,blank.getRow());
					record.add(t);
					s=t;
				}
				/*else if(s.getColumn()==f.getColumn()&&s.getRow()==f.getRow()-1) {
					record.add(f);
					record.add(b);
					Point e=new Point(f.getColumn(),f.getRow()-1);
					b=f;
					f=e;
					num--;
					continue;
				}*/
				Point e=new Point(f.getColumn(),f.getRow()-1);
				//record.add(t);
				record.addAll(findNumberRoute(s,e));
				record.add(f);
				record.add(b);
				blank=b;
				b=f;
				f=e;
				num--;
				}
			}
			return record;
		}
		
		public static void MovingTheFinalThreeNumbers(int[][] arr,int n1,int n2,int n3,FileWriter myWriter)throws IOException {          //assume n1 < n2 < n3
			LinkedList<Point> record=new LinkedList<Point>();
			LinkedList<Point> temp=new LinkedList<Point>();
			Point blank=Find(0,arr);
			Point p1=Find(n1,arr);
			Point p2=Find(n2,arr);
			Point p3=Find(n3,arr);
			Point c1=findRightPosition(n1,arr);
			Point c2=findRightPosition(n2,arr);
			Point c3=findRightPosition(n3,arr);
			if(blank.isEqual(c1)) {
				while(c1.isEqual(p1)==false||c2.isEqual(p2)==false||c3.isEqual(p3)==false) {
					if(record.isEmpty()) {
						LinkedList<Integer> specialCase=new LinkedList<>(List.of(1,3,0,2));   //special case
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					temp.add(record.removeFirst());
					MovingTheBoard(arr,temp,myWriter);
					blank=Find(0,arr);
					p1=Find(n1,arr);
					p2=Find(n2,arr);
					p3=Find(n3,arr);
					//temp.removeFirst();
				}
			}
			else if(blank.isEqual(c2)){
				while(c1.isEqual(p1)==false||c2.isEqual(p2)==false||c3.isEqual(p3)==false) {
					if(record.isEmpty()) {
						LinkedList<Integer> specialCase=new LinkedList<>(List.of(3,0,2,1));   //special case
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					temp.add(record.removeFirst());
					MovingTheBoard(arr,temp,myWriter);
					blank=Find(0,arr);
					p1=Find(n1,arr);
					p2=Find(n2,arr);
					p3=Find(n3,arr);
					//temp.removeFirst();
				}
				}
			else if(blank.isEqual(c3)) {
				while(c1.isEqual(p1)==false||c2.isEqual(p2)==false||c3.isEqual(p3)==false) {
					if(record.isEmpty()) {
						LinkedList<Integer> specialCase=new LinkedList<>(List.of(2,1,3,0));   //special case
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					temp.add(record.removeFirst());
					MovingTheBoard(arr,temp,myWriter);
					blank=Find(0,arr);
					p1=Find(n1,arr);
					p2=Find(n2,arr);
					p3=Find(n3,arr);
					//temp.removeFirst();
				}
			}
			else {
				while(c1.isEqual(p1)==false||c2.isEqual(p2)==false||c3.isEqual(p3)==false) {
					if(record.isEmpty()) {
						LinkedList<Integer> specialCase=new LinkedList<>(List.of(0,2,1,3));   //special case
						record.addAll(movementOfBlankSpace(specialCase,blank));
					}
					temp.add(record.removeFirst());
					MovingTheBoard(arr,temp,myWriter);
					blank=Find(0,arr);
					p1=Find(n1,arr);
					p2=Find(n2,arr);
					p3=Find(n3,arr);
					//temp.removeFirst();
				}
			}
			}
		
		
		public static void Solver(String input, String output) throws IOException {
				
				//String numberOfSlover="1";
				//String numberOfBoard="01";	
			
			int co=0;
			Point s=new Point(0,0);
			Point e=new Point(2,1);
			Point n=new Point(1,1);
			int [][]arr=ReadTheBoard(input);
			File file=new File(output);
			FileWriter myWriter = null;
			int count=0;
			try {
			      myWriter = new FileWriter(file);
			      //myWriter.write("test");
			      //myWriter.write("Files in Java might be tricky, but it is fun enough!");
			      //myWriter.close();
			      //System.out.println("Successfully wrote to the file.");
			    } catch (IOException k) {
			      System.out.println("An error occurred.");
			      //k.printStackTrace();
			    }		
			for(int i=0;i<arr.length;i++) {
				for(int j=0;j<arr.length;j++) {
					//arr[i][j]=count;
					//count++;
					//System.out.print(arr[i][j]+" ");
				}
				//System.out.println();
			}
			//changing2DArray(arr);
			//swap(arr[0][0],arr[0][1]);
			//System.out.println(arr[0][1]);
			/*arr[3][0]=1;                    //needs fix
			arr[0][1]=12;                   // needs fix
			arr[0][2]=13;
			arr[3][1]=2;*/
			LinkedList<Point> r=new LinkedList<Point>();
			//r=findNumberRoute(arr[3][1],arr[0][2]);
			//System.out.println(s.getColumn()+" "+s.getRow()+"3333333");
			//long start= System.currentTimeMillis();
			/*r=moveTheBasicNumber(1,arr,1);
			co+=r.size();
			MovingTheBoard(arr,r);
			arr[0][0]=99;
			
			r=moveTheBasicNumber(2,arr,2);
			co+=r.size();
			MovingTheBoard(arr,r);
			arr[0][1]=99;
			
			r=moveTheBasicNumber(3,arr,3);
			co+=r.size();
			MovingTheBoard(arr,r);
			arr[0][2]=99;
			
			r=moveTheBasicNumber(4,arr,4);
			co+=r.size();
			MovingTheBoard(arr,r);
			arr[0][3]=99;*/
			
			for(int i=0;i<arr.length;i++) {
				for(int j=0;j<arr.length;j++) {
					//arr[i][j]=count;
					//count++;
					System.out.print(arr[i][j]+" ");
				}
				System.out.println();
			}
			System.out.println("------------------");
			
			for(int a=0;a<arr.length-2;a++) {                          ////////////-----------------------------
				int i;
				for(i=arr.length*a;i<arr.length*(a+1)-2;i++) {
					r=moveTheBasicNumber(i+1,arr,i+1);
					if(r!=null)
					{
						co+=r.size();
						MovingTheBoard(arr,r,myWriter);
					}
					//MovingTheBoard(arr,r);
					//System.out.println(i+"index---------    "+a);
					//System.out.println(r.size()+"sizeeeeeeeeee");
					arr[a][i%arr.length]=99;
					//System.out.println(i+"index---------    "+a);
					/*for(int b=0;b<arr.length;b++) {
						for(int j=0;j<arr.length;j++) {
							//arr[i][j]=count;
							//count++;
							System.out.print(arr[b][j]+" ");
						}
						System.out.println();
					}*/
					//MovingTheBoard(arr,r);
				}
				//System.out.println(i+"index---------    "+a);
				//System.out.println(r.size()+"sizeeeeeeeeee");
				r=moveTheLastTwoRow(i+1,arr,i+2,myWriter);
				//for(int i=0;i<r.size();i++)
				if(!r.isEmpty()) {
					co+=r.size();
					MovingTheBoard(arr,r,myWriter);
				}
				arr[a][i%arr.length]=99;
				arr[a][(i%arr.length)+1]=99;                      //////////----------------
			}    
			MovingTheBoard(arr,r,myWriter);
			for(int i=0;i<arr.length-2;i++) {
				r=MovingTheLastTwoColumnNumber((arr.length-2)*arr.length+i+1,(arr.length-1)*arr.length+i+1,arr,myWriter);
				co+=r.size();
				MovingTheBoard(arr,r,myWriter);
				arr[arr.length-2][i]=99;
				arr[arr.length-1][i]=99;
			}
			System.out.println();
			
			MovingTheFinalThreeNumbers(arr,arr.length*(arr.length-1)-1,arr.length*(arr.length-1),arr.length*arr.length-1,myWriter);
			System.out.println();
			
			System.out.printf("File is located at %s%n", file.getAbsolutePath());
			myWriter.close();
			/*for(int i=0;i<arr.length;i++) {
				for(int j=0;j<arr.length;j++) {
					//arr[i][j]=count;
					//count++;
					System.out.print(arr[i][j]+" ");
				}
				System.out.println();
			}*/
			
			//arr[0][i]=99;
			/*for(int i=0;i<arr.length-2;i++) {
				int j;
				for(j=i*arr.length;j<(i+1)*arr.length-2;j++) {
					r=moveTheBasicNumber(j,arr,j);
					co+=r.size();
					MovingTheBoard(arr,r);
					arr[j][j]=99;
				}
				r=moveTheLastTwoRow(j,arr,j+1);
				co+=r.size();
				MovingTheBoard(arr,r);
				arr[j][j]=99;
			}*/
			/*for(int i=0;i<arr.length;i++) {
				for(int j=0;j<arr.length;j++) {
					System.out.print(arr[i][j]+" ");
				}
				System.out.println();
			}*/
			/*r=moveTheLastTwoRow(3,arr,4);
			co+=r.size();
			MovingTheBoard(arr,r);
			arr[0][2]=99;
			arr[0][3]=99;
			System.out.println("--------------------------------------------------------");
			r=moveTheBasicNumber(5,arr,5);
			co+=r.size();
			MovingTheBoard(arr,r);
			System.out.println("break");
			for(int i=0;i<r.size();i++) {
				Point temp=r.removeFirst();
				System.out.println(temp.getColumn()+" "+temp.getRow());
			}
			System.out.println("break+");
			r=moveTheBasicNumber(6,arr,6);
			co+=r.size();
			MovingTheBoard(arr,r);
			r=moveTheLastTwoRow(7,arr,8);
			MovingTheBoard(arr,r);
			
			long end= System.currentTimeMillis();
			int size=r.size();
			for(int i=0;i<size;i++) {
				Point temp=r.removeFirst();
				System.out.println(temp.getColumn()+" "+temp.getRow());
			}*/
			//int [][]ar=new int[4][4];
			int num=1;
			/*for(int i=0;i<ar.length;i++) {                    ///////////////////////////
				for(int j=0;j<ar.length;j++) {
					ar[i][j]=num;
					num++;
				}
			}
			//ar[3][3]=0;
			for(int i=0;i<ar.length-2;i++) {
				for(int j=0;j<ar.length;j++) {
					ar[i][j]=99;
				}
			}
			ar[2][0]=11;
			ar[3][0]=13;
			ar[3][3]=0;
			ar[2][2]=9;
			ar[2][3]=9;
			ar[2][2]=12;
			ar[2][0]=13;
			ar[3][0]=11;
			ar[3][3]=9;
			ar[2][3]=0;
			ar[3][2]=9;
			ar[3][3]=15;
			ar[3][1]=9;
			ar[3][2]=14;
			ar[3][0]=9;
			ar[3][1]=11;
			ar[2][1]=13;
			ar[2][0]=10;
			ar[3][3]=13;
			ar[2][1]=15;
			ar[2][3]=13;
			ar[3][3]=0;
			ar[2][0]=9;
			ar[3][0]=10;
			ar[2][1]=13;
			ar[2][3]=15;
			ar[2][0]=10;
			ar[3][0]=9;
			ar[2][1]=11;
			ar[3][1]=13;
			ar[2][3]=9;
			ar[3][3]=13;
			ar[3][0]=15;
			ar[3][1]=0;
			ar[2][3]=12;
			ar[3][3]=14;
			ar[2][2]=9;
			ar[3][2]=13;
			ar[2][2]=13;
			ar[3][2]=9;
			ar[3][3]=0;
			ar[3][1]=14;
			ar[3][3]=12;
			ar[2][3]=0;
			ar[2][1]=0;
			ar[2][3]=11;
			ar[2][1]=14;
			ar[3][1]=0;
			ar[2][0]=0;
			ar[3][1]=10;
			ar[3][0]=0;
			ar[2][0]=15;
			ar[2][3]=13;
			ar[3][3]=9;
			ar[2][2]=11;
			ar[3][2]=12;
			ar[2][0]=0;
			ar[3][0]=15;
			ar[2][2]=0;
			ar[2][0]=11;
			ar[3][2]=0;
			ar[2][2]=12;
			ar[2][3]=9;
			ar[3][3]=13;
			ar[2][2]=0;
			ar[3][2]=12;
			ar[2][1]=0;
			ar[2][2]=14;
			ar[3][1]=0;
			ar[2][1]=10;
			ar[2][2]=9;
			ar[3][2]=13;
			ar[2][3]=14;
			ar[3][3]=12;
			ar[2][1]=0;
			ar[3][1]=10;
			ar[2][0]=0;
			ar[2][1]=11;
			ar[3][0]=0;
			ar[2][0]=15;                                                  //  the last two row of the same row of both  
			                                                              //  sides of the blank has been tested to be ok
			ar[3][3]=0;
			ar[3][0]=12;
			ar[2][3]=0;
			ar[3][3]=14;
			
			ar=new int[5][5];
			num=1;
			for(int i=0;i<ar.length;i++) {
				for(int j=0;j<ar.length;j++) {
					ar[i][j]=num;
					num++;
				}
			}
			//ar[3][3]=0;
			for(int i=0;i<ar.length-2;i++) {
				for(int j=0;j<ar.length;j++) {
					ar[i][j]=99;
				}
			}
			
			ar[4][4]=0;
			ar[3][2]=16;
			ar[3][0]=18;
			ar[4][2]=21;
			ar[4][0]=23;
			ar[4][4]=20;
			ar[3][4]=0;
			ar[3][3]=0;
			ar[3][4]=19;
			ar[4][3]=0;
			ar[3][3]=24;
			ar[4][1]=0;
			ar[4][3]=22;
			ar[3][1]=0;
			ar[4][1]=17;
			ar[3][0]=0;
			ar[3][1]=18;
			ar[4][0]=0;
			ar[3][0]=23;
			ar[3][3]=16;
			ar[4][3]=21;
			ar[3][2]=24;
			ar[4][2]=22;
			ar[4][4]=0;
			ar[4][0]=20;
			ar[4][3]=16;
			ar[3][3]=21;
			ar[3][2]=21;
			ar[4][2]=16;
			ar[3][3]=24;
			ar[4][3]=22;
			ar[4][0]=0;
			ar[4][4]=20;
			ar[3][0]=0;
			ar[4][0]=23;
			ar[3][1]=0;
			ar[3][0]=18;
			ar[4][1]=0;
			ar[3][1]=17;
			ar[3][3]=0;
			ar[4][1]=24;
			ar[4][3]=0;
			ar[3][3]=22;
			ar[3][4]=0;
			ar[4][3]=19;
			ar[4][4]=0;
			ar[3][4]=20;
			ar[3][3]=21;
			ar[3][2]=22;
			ar[3][1]=16;
			ar[4][2]=17;
			ar[3][4]=0;
			ar[4][4]=20;
			ar[3][0]=0;
			ar[3][4]=18;
			ar[4][0]=0;
			ar[3][0]=23;
			ar[4][1]=0;
			ar[4][0]=24;
			ar[4][2]=0;
			ar[4][1]=17;
			ar[4][3]=0;
			ar[4][2]=19;
			ar[3][2]=0;
			ar[4][3]=22;
			ar[4][1]=16;
			ar[3][1]=17;
			ar[3][3]=22;
			ar[4][3]=21;
			ar[3][1]=0;
			ar[3][2]=17;
			ar[3][0]=0;
			ar[3][1]=23;
			ar[4][0]=0;
			ar[3][0]=24;
			ar[3][4]=0;
			ar[4][0]=18;
			ar[4][4]=0;
			ar[3][4]=20;
			ar[4][2]=0;
			ar[4][4]=19;
			ar[3][2]=0;
			ar[4][2]=17;
			
			for(int i=0;i<ar.length;i++) {
				for(int j=0;j<ar.length;j++) {
					System.out.print(ar[i][j]+" ");
				}
				System.out.println();
			}
			System.out.println("------------------------");
			LinkedList<Point> record=new LinkedList<Point>();
			Point a=new Point(2,2);
			Point b=new Point(3,2);
			record.addAll(MovingTheLastTwoColumnNumber(16,21,ar));
			//record.addAll(MovingTheLastTwoColumnNumber(9,13,ar));
			MovingTheBoard(ar,record);
			for(int i=0;i<ar.length;i++) {
				for(int j=0;j<ar.length;j++) {
					System.out.print(ar[i][j]+" ");
				}
				System.out.println();
			}*/
			
			
			
		}
	
	public static void main(String[] args) throws IOException {
//		System.out.println("number of arguments: " + args.length);
//		for (int i = 0; i < args.length; i++) {
//			System.out.println(args[i]);
//		}

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}
		Solver(args[0],args[1]);
		
		// TODO
		//File input = new File(args[0]);
		// solve...
		//File output = new File(args[1]);
		
		

	}
}
