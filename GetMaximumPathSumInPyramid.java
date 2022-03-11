package proj;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;



 class GetMaximumPathSumInPyramid {
	 
	//NOTE!= In the "numPyramid.txt" file, the pyramid given in the 3rd question exists.
	 
	 
	 
	static int xAxisNums=15; //for the max number amount in x axis
	static int yAxisNums=15; //for the max number amount in y axis 
	static int sizeVal=15;   //size value of the inputted pyramid (size of the pyramid in "numPyramid.txt")
    public static int[][] numArr = new int[xAxisNums][yAxisNums]; //the multidimensional array keeping the values of inputted pyramid
    
    private static class Location { //for keeping track of the locations of rows in pyramid
        
    	private int xLoc; 
        private int yLoc; 
        private int num; 

        //Constructor of Location class
        public Location(int xLoc, int yLoc, int num) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            this.num = num;
        }
    }
    
    public static int findMaxSum(String fileName, ArrayList<Location> pathNums) {
        readNums(numArr,fileName); //read numbers in the inputted txt file
        return findMaxSumRecursively(pathNums, 0, 0, sizeVal); //calling findMaxSumRecursively with size value 15.
    }
    
     private static boolean is_prime_number(int num) {
    	 
    	 //handling negative cases and base cases. 
    	 //if a number is smaller than or equal to 1, than this number is not prime
    	 if(num<=1) {
    		 return false;
    	 }

    	 int halfOfNum=num/2;
    	 
    	 
         for (int j = 2; j < halfOfNum; j++) {
        	 int rem=num%j; //take the remainder of the parameter number with the numbers up to that number
             if (rem == 0) { //if one of the number divides the parameter number
                 return false; //then the inputted number is not prime
             }
         }
         return true; //otherwise, the inputted number is prime 
     }

    public static int findMaxSumRecursively(ArrayList<Location> pathOfNumbers, int xLocation, int yLocation, int sizeValue) {

    	
    	ArrayList<Location> rightNumLis = new ArrayList<>();
        ArrayList<Location> leftNumLis = new ArrayList<>(); 
        
        int leftSummation = 0;
        int rightSummation= 0;
        
        if(sizeValue<0) {
        	return 0;         //If the size value is an invalid number, than return max sum as 0.
        }

        if(sizeValue==0) { //if there is no number in the inputted txt file, then return max sum as 0.
            return 0;
        } else if (xLocation+1==sizeValue) { //if we are at last row of pyramid (index==14)
        	Location loc=new Location(xLocation, yLocation, numArr[xLocation][yLocation]);  //create the location of that element
            pathOfNumbers.add(loc); //add the location of that number to the path keeping locations
            return numArr[xLocation][yLocation]; //return the current max path sum
        }

        if (is_prime_number(numArr[xLocation + 1][yLocation + 1])==false) {
            leftSummation = findMaxSumRecursively(leftNumLis,xLocation + 1, yLocation + 1, sizeValue); //left sum
        }
        
        
        if (is_prime_number(numArr[xLocation + 1][yLocation])==false) {
            rightSummation = findMaxSumRecursively(rightNumLis,xLocation + 1, yLocation, sizeValue); //right sum
        }
        
        
        if ( is_prime_number(numArr[xLocation + 1][yLocation + 1]) == true && 
        		is_prime_number(numArr[xLocation + 1][yLocation]) == true ) {
        	//checking the primeness of the number in the diagonal and of the number downwards
        	//if the numbers at downwards and at diagonal are prime
            return 0;
        }

        if(rightSummation>=leftSummation) { //if right sum > left sum
        	 pathOfNumbers.addAll(rightNumLis); 
             pathOfNumbers.add(new Location(xLocation, yLocation, numArr[xLocation][yLocation]));
             return numArr[xLocation][yLocation]+rightSummation; //add right sum to current sum
        } else { //otherwise
        	 pathOfNumbers.addAll(leftNumLis);
             pathOfNumbers.add(new Location(xLocation, yLocation, numArr[xLocation][yLocation]));
             return numArr[xLocation][yLocation]+leftSummation;  //add left sum to current sum
        }

    }

    public static void readNums(int[][] numArray,String txtFileToRead) {
       
            BufferedReader br = null; //initializing buffered reader object
			try {
				br = new BufferedReader(new FileReader(txtFileToRead));//creating buffered reader object 
			} catch (FileNotFoundException e) { //if file is not found
				// TODO Auto-generated catch block
				e.printStackTrace(); //handle the error
			}
			
            Scanner s = new Scanner(br); //scanner object 
            int countNum = 0;
            while (s.hasNextLine()) { //while txt file has a line to read
                String row = s.nextLine(); //reading the txt file line by line
                String[] nums_Arr = row.split("\\D+");
                int len=nums_Arr.length;
                for (int w= 0; w < len; w++) {
                    numArray[countNum][w] = Integer.parseInt(nums_Arr[w]); //putting the values in txt file to the multidimensional array

                }
                countNum++; //for going to the subsequent row in the inputted txt file
            }
        
    }
    public static void main(String[] args) {
        ArrayList<Location> pathOfNums = new ArrayList<>();
        findMaxSum("numPyramid.txt", pathOfNums);
        System.out.println();
        System.out.println("Valid Max Sum Path is :  ");
        System.out.println();
        int upperBound=pathOfNums.size()-1;
        for (int k = upperBound; k >= 0; k--) { 
        	int number=pathOfNums.get(k).num;
            System.out.print(number); //Printing each element of the valid maximum sum path
            if(k!=0) { //this check is for not printing the "->" symbol after the last number in path
            System.out.print("->");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        int maxSum=findMaxSum("numPyramid.txt", pathOfNums); //calling findMaxSum function with "numPyramid.txt" to find the valid max sum
        System.out.println();
        System.out.println();
        System.out.println("Sum of the Valid Max Sum Path is: "+maxSum); //printing valid maximum sum

    }
}
