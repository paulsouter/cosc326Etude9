
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author paul
 */
public class TestDuplicates {

    public static boolean foundCarpet = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int width = 0;
        int length = 0;
        ArrayList<UniqueCarpet> finishedCarpets = new ArrayList<UniqueCarpet>();
        
        int result = 0; 
        
        while (sc.hasNext()){
        	width = sc.nextInt(); 
        	length = sc.nextInt(); 
        	
            int[][] carpet = new int[width][length];
            
            for(int i=0; i < width ; i++){
    			for(int j=0; j< length; j++){
    				carpet[i][j] = sc.nextInt(); 
    			}
    		}

            UniqueCarpet current = new UniqueCarpet();
            current.setCarpet(carpet);
            for (int[] row : current.getCarpet()) {
                for (int col : row) {
                    System.out.print(col + " ");
                }
                System.out.println();
            }
            

            
            if(finishedCarpets.isEmpty()){
            	System.out.println("First carpet! Add to list");
                finishedCarpets.add(current);
              //  System.out.println("Have " + finishedCarpets.size() + " carpets");
                result++;
                continue; 
            }
            

            
            if(isUnique(current, finishedCarpets)){ //returns true if we compare two identical finished carpets
            	System.out.println("and it was unique! Adding to finishedCarpets");
                finishedCarpets.add(current);
              //  System.out.println("Have " + finishedCarpets.size() + " carpets");
                result++;
            	
            }else{
            	System.out.println("but it was a duplicate... ");
            }
        

        }
        System.out.println("\nResult was " + finishedCarpets.size() + " unique carpets");
    }
    
    public static Boolean isUnique(UniqueCarpet a, ArrayList<UniqueCarpet> finishedCarpets){
    	//System.out.println("finished carpets is " + finishedCarpets.size());
    	
		/*for (int[] row : carpet) {
            for (int col : row) {
              //  System.out.print(col + " ");
                if(col != b.carpet[row][col]){
                	return false; 
                }
            }
            System.out.println();
        }*/ 
    	ArrayList<Boolean> uniqueCheck = new ArrayList<Boolean>();
		
    	for (UniqueCarpet b : finishedCarpets) {
        	//System.out.println("Looking at carpet " + b.getID() + " of index " + finishedCarpets.indexOf(b));
        	Boolean match = true;  //it is a duplicate until proven otherwise
        	
        	for(int i=0; i < a.carpet.length ; i++){
    			for(int j=0; j< a.carpet[i].length; j++){
    				System.out.println("Comparing " + a.carpet[i][j] + " with " + b.carpet[i][j]);
    				if(a.carpet[i][j] != b.carpet[i][j]){
    					match = false; //an element is not equal so this carpet is not a match 
    				//	System.out.println("UNIQUE");
    					//return false; 
    				}
    			}
    		}
        	
        	uniqueCheck.add(match); //adds if these two carpets matched 
        	
        	System.out.println();
        }
    	if(uniqueCheck.contains(true)){ //if unique check contains at least one true, then there IS a duplicate
    		System.out.println("DUPLICATE");
    		return false; 
    	}else{ //if the unique check does not contain a true, then this is unique as it does not match with any!
    		System.out.println("UNIQUE");
    		return true;
    	}
    }

    public static class UniqueCarpet {

        private int id = 1;
        private int[][] carpet;
        
        public UniqueCarpet(){
        	this.carpet = null;
        	this.id = id++;
        }

        public UniqueCarpet(int[][] carpet) {
            this.carpet = carpet;
            this.id = id++;
        }
        
        public void setCarpet(int[][] carpet){
        	this.carpet = carpet;
        }

        public int[][] getCarpet() {
            return carpet;
        }
        
        public int getID(){
        	return this.id;
        }
    }
}
