import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SolutionWithLog{

    public static void main(String[] args) throws FileNotFoundException {
    
    	String[] fileNames = new String[]{"a_example"};

    	for (String fileName : fileNames) {
        	File inFile = new File("inputs/" + fileName+".in");
        	
        	//Read file with scanner
            Scanner in = new Scanner(inFile);
            
            String[] line1 = in.nextLine().split(" ");
            int targetMaxNumber = Integer.parseInt(line1[0]);
            int typeCount = Integer.parseInt(line1[1]);
            
            String[] line2 = in.nextLine().split(" ");
            in.close(); 
            //close scanner instance
            
            int[] typeArr = new int[typeCount];
            for(int i =0; i<typeCount; i++) {
            	typeArr[i]= Integer.parseInt(line2[i]);
            }
            
            List<Integer> indexList = solveProblem(targetMaxNumber, typeArr);
            
            //write to file
            try {
            	File outFile = new File("outputs/" + fileName+".out");
            	if(!outFile.exists()){
            		outFile.createNewFile(); //create file if not found
            	}
            	
                FileWriter wr = new FileWriter(outFile);
            	
            	wr.write(indexList.size()+ "\n");
                for (Integer index : indexList) {
                	wr.write(index+ " ");
				}
            	
            	wr.flush();
            	wr.close(); 
            	
			} catch (IOException e) {
				System.out.println("An error occured when write to file: " + fileName 
						+ "  Excception:" + e.getMessage());
			}  
		}
    }
    
    public static List<Integer> solveProblem(int targetMaxNumber, int[] typeArr){
    	List<Integer> maxSumIndexList = new ArrayList<>(); //the most optimal solution's index list
    	List<Integer> maxSumValueList = new ArrayList<>(); //the most optimal solution's value list
    	List<Integer> currentIndexList = new ArrayList<>(); //current loop index list
    	List<Integer> currentValueList = new ArrayList<>(); //current loop value list

    	int startIndex = typeArr.length - 1; // start with biggest value(biggest index)
    	int maxSum = 0; //Store the most optimal solution    	
    	int sum = 0;
    	
    	System.out.println("targetMaxNumber: " + targetMaxNumber);
    	System.out.print("valueList: ");
    	for (int i : typeArr) {
    		System.out.print(i + " ");
    	}
    	System.out.println();
    	System.out.println();
    	
    	// Fistly we can start with the biggest item in array. 
    	// Our array is ordered, so that we can start last index in array 
    	while(true){
            System.out.println("startIndex: " + startIndex);
            
            for(int i=startIndex; i>-1; i--){
            	
                int currentValue = typeArr[i];

                int tempSum = sum + currentValue; 

                if (tempSum == targetMaxNumber){ //is tempSum ise equal to targetMaxNumber
                    sum = tempSum;
                    currentIndexList.add(i);
                    currentValueList.add(currentValue);
                    
                    System.out.println("*** Max Number is found. Break ***" );
                    System.out.println("sum: " + sum);
                    System.out.println("currentIndexList: " + currentIndexList);
                    System.out.println("currentValueList: " + currentValueList);
                    System.out.println("*** **** ***" );
                    
                    break;
                } else if (tempSum > targetMaxNumber){ //if the sum bigger than targetMaxNumber, no add the item current lists
                    continue;
                }else if(tempSum < targetMaxNumber){
                    sum = tempSum;
                    currentIndexList.add(i);
                    currentValueList.add(currentValue);
                    
                    System.out.println("--- Add value and index to currentIndexList and currentValueList. ---");
                    System.out.println("sum: " + sum);
                    System.out.println("currentIndexList: " + currentIndexList);
                    System.out.println("currentValueList: " + currentValueList);
                    System.out.println("--- ---- ---" );
                    continue;
                }
            }

            //maxSum is maximum total value for maxNumber
            if (maxSum < sum){
                maxSum = sum;
                //copy indexes and values to maxSum lists
                maxSumIndexList = new ArrayList<>(currentIndexList);
                maxSumValueList = new ArrayList<>(currentValueList);
                
                System.out.println("!!! The most optimal solution is found so far !!!" );
                System.out.println("maxSum: " + maxSum);
                System.out.println("maxSumIndexList: " + maxSumIndexList);
                System.out.println("maxSumValueList: " + maxSumValueList);
                System.out.println("!!! !!!! !!!" );
            }
            
            if (maxSum == targetMaxNumber){ //if the targetMaxNumber is found, problem is solved 
                break;
            }

            // if we didn't find targetMaxNumber, remove last item in current lists
            // continue with index that is last index -1 
            if( currentIndexList.size() != 0){ //same control with 'currentValueList.size() != 0'
                int lastVal = currentValueList.remove(currentValueList.size()-1);
                sum = sum - lastVal;

                int lastIndex = currentIndexList.remove(currentIndexList.size()-1);
                startIndex = lastIndex - 1;

                System.out.println("&&& Remove last item in currentIndex and continue with 'lastIndex-1' &&&" );
                System.out.println("lastIndex in currentIndexList: " + lastIndex  );
                System.out.println("remove last item in currentIndexList." );
                System.out.println("currentIndexList: " + currentIndexList);
                System.out.println("currentValueList: " + currentValueList);
                System.out.println("&&& &&&& &&&" );
                
            }else{
            	startIndex = startIndex - 1;
            }

            //if we didn't find maxNumber and loops are ended.
            if(currentIndexList.size() == 0 && startIndex == -1){
            	System.out.println("=== Problem solving is ended. ===" );
                break;
            }
    	}
    	    	
    	System.out.println(); 
    	System.out.println("%%% pizza type size: " + maxSumIndexList.size());
    	System.out.println("%%% pizza type list: " + maxSumIndexList);
    	int totalPizzaSlice = 0;
    	for (int value : maxSumValueList) {
    		totalPizzaSlice += value;
    	}
    	System.out.println("%%% total pizza slice: " + totalPizzaSlice); 
    	
    	return maxSumIndexList;
    }
}
