/*  Shannen Barrameda sib170130
    CS 4341.HON Digital Logic
    sources: Dr. Page Notes on QMCTable Algorithm, GeeksforGeeks, ironmaurus on github
*/
package quinemc;

import java.util.*;
import java.util.Map.*;
import java.math.*;

public class QuineMC {
    public class Implicant{
        private int numBits;
        private int[] minterms;
        private int[] dontCares;
        private String binary;
        private boolean isPaired;
        /*Constructor*/
        private Implicant(int[] minterms, int[] dontCares, String binary){
            this.binary = new String(binary);
            this.minterms = minterms;
            this.dontCares = dontCares;
            setPaired(false);
            setNumBits();
        }
        private Implicant(int[] minterms, String binary){
            this.binary = new String(binary);
            this.minterms = minterms;
            setPaired(false);
            setNumBits();
        }
        
        private void setNumBits(){
            numBits = 0;
            
            for(char bit : binary.toCharArray()){
                if(bit == '1'){
                    numBits = numBits + 1;
                }
            }
        }
        
        public int getNumBits(){
            return numBits;
        }
        
        public int getMinterm(){
            return minterms[0];
	}
	
	public int[] getMinterms(){
		return minterms;
	}
        
        public int[] getDontCares(){
            return dontCares;
        }
	
        public int getDontCareSize(){
            return dontCares.length;
        }
        
        public int[] combineMintermsDontCares(){
            int[] combined = new int[getMintermSize() + getDontCareSize()];
            int index = 0;
            for(int i : getMinterms()){
                combined[index] = i;
                index++;
            }
            for(int i : getDontCares()){
                combined[index] = i;
                index++;
            }
            
            return combined;
        }
	public int getMintermSize() {
		return minterms.length;
	}

	public boolean isPaired() {
		return isPaired;
	}
	
	public void setPaired(boolean isPaired) {
            this.isPaired = isPaired;
	}
	
	public String getBinary(){
		return binary;
	}
        public String toString(){
		return String.format("| %8d | %11s | %5s | %s ", numBits, binary, isPaired, Arrays.toString(minterms));
	}
    }
    public class PrimeImplicant{
        private Implicant implicant;
        private HashMap<Integer, Boolean> minList;
        
        public PrimeImplicant(Implicant implicant, int[] mintermList){
            this.implicant = implicant;
            this.minList = new HashMap<Integer, Boolean>();
            initializeMintermList(mintermList);
        }
        
        private void initializeMintermList(int[] list){
            for(Integer i : list){
                minList.put(i, false);
            }
        }
        
        public void markMinterm(int minterm, boolean mark){
            minList.put(minterm, mark);
        }
        /*returns true if particular minterm is in mintermlist*/
        public boolean contains(int minterm){
            for(int min : implicant.getMinterms()){
                if(min == minterm)
                    return true;
            }
            return false;
        }
        
        public String toString(){
            String s = "";
            for(Entry<Integer, Boolean> mint : minList.entrySet()){
			s+="| ";
			if(mint.getValue() == true){
				s += "x";
			}
			else{
				s +="_";
			}
			s+=" |";
		}
		
		s += "\t" + Arrays.toString(implicant.getMinterms());
		s+= "\t" + this.getMarkCount();
		return s;
        }
        
        public int getMarkCount(){
            int count = 0;
            for(Entry<Integer,Boolean> min : minList.entrySet()){
                if(min.getValue() == true){
                    count++;
                }
            }
            return count;
        }
        
        public Implicant getImplicant(){
            return implicant;
        }
    }
    public class QMCTable{
        private final int SIZE;
        private HashMap<Integer, ArrayList<Implicant>> qmcSections;
        
        public QMCTable(int size){
            this.SIZE = size;
            qmcSections = new HashMap<Integer,ArrayList<Implicant>>();
            for(int numBits = 0; numBits < size; numBits++){
                qmcSections.put(numBits, new ArrayList<Implicant>());
            }
        }
        
        public int getSize(){
            return SIZE;
        }
        
        public void addImplicant(int numBits, Implicant implicant){
            ArrayList<Implicant> temp = qmcSections.get(numBits);
            
            temp.add(implicant);
        }
        
        public HashMap<Integer, ArrayList<Implicant>> getQMCTable(){
            return qmcSections;
        }
        public void printTable(){
		ArrayList<Implicant> temp;
		for(int i = 0; i < SIZE; i++){
			
			temp = qmcSections.get(i);
			
			if(temp.isEmpty()){
				System.out.println("SECTION: "+i+"");
				System.out.println("EMPTY!");
			}
			else{
				System.out.println("SECTION: "+i+"");
				System.out.println("| BitCount | BinaryValue |  Mark | MintermList");
				for(Implicant implicant :  temp){
					System.out.println(implicant.toString());
				}
			}
			System.out.println();
		}
	}
        public ArrayList<Implicant> getImplicantList(){
            ArrayList<Implicant> implicantList = new ArrayList<Implicant>();
            for(Entry<Integer, ArrayList<Implicant>> entry : qmcSections.entrySet()){
                for(Implicant implicant : entry.getValue()){
                    implicantList.add(implicant);
                }
            }
            
            return implicantList;
        }
        
        public boolean isComparable(){
            if(qmcSections.size() > 1)
                return true;
            else
                return false;
        }
    }
    
    private final int NUM_LITERALS;
    private Implicant[] implicants;
    private int[] minterms;
    private int[] dontCares;
    private int[] mintermsWithDC;
    private ArrayList<QMCTable> qmcTable;
    private ArrayList<PrimeImplicant> PIs, finalPIs;
    private String output;
    
    public QuineMC(String input){
        String[] tokens = input.split(" ");
        int NUM_MINTERMS = 0, NUM_DONTCARES = 0;
        
        NUM_LITERALS = Integer.parseInt(tokens[0]);
        
        //if array contains "d", check for don't cares
        //otherwise skip and create matrix of size of array
        if(input.contains("d")){
            for(int i = 1; i < tokens.length; i++){
                if(!tokens[i].equals("d")){
                    NUM_MINTERMS++;
                }
                else{           
                    break;
                }
            }
            NUM_DONTCARES = tokens.length - NUM_MINTERMS - 2;
        }
        else{
            NUM_MINTERMS = tokens.length - 1;
        }
        
        mintermsWithDC = new int[NUM_MINTERMS + NUM_DONTCARES];
        minterms = new int[NUM_MINTERMS];
        
        int index = 0, breakpt = 0;
        for(int i = 1; i < tokens.length; i++){
            if(!tokens[i].equals("d")){
                minterms[index] = Integer.parseInt(tokens[i]);
                index++;
            }
            else{
                break;
            }
        }
        index = 0;
        for(int i = 1; i < tokens.length; i++){
            if(!tokens[i].equals("d")){
                mintermsWithDC[index] = Integer.parseInt(tokens[i]);
                index++;
            }
            else{
                breakpt = i;
                i++;
            }
        }
        
        dontCares = new int[NUM_DONTCARES];
        index = 0;
        while(index < NUM_DONTCARES){
            dontCares[index] = Integer.parseInt(tokens[breakpt+1]);
            index++;
            breakpt++;
        }
        
        
 //4 4 5 6 8 9 10 13 d 0 7 15       
        Arrays.sort(minterms);
//        Arrays.sort(dontCares);
        print(minterms);
//        print(dontCares);
        
        implicants = new Implicant[NUM_MINTERMS + NUM_DONTCARES];
        String raw, formatted;
        int[] mintermList;
        for(int i = 0; i < NUM_MINTERMS + NUM_DONTCARES; i++){
            mintermList = new int[]{mintermsWithDC[i]};
            raw = Integer.toBinaryString(mintermsWithDC[i]);
            formatted = String.format("%0"+NUM_LITERALS+"d", new BigInteger(raw));
            implicants[i] = new Implicant(mintermList, formatted);
        }
        
        
       
//        for(int i = NUM_MINTERMS; i < NUM_MINTERMS + NUM_DONTCARES; i++){
//            mintermList[i] = dontCares[dc_ind];
//            
//            implicants[i] = new Implicant(mintermList, formatted);
//            dc_ind++;
//        }
        qmcTable = new ArrayList<QMCTable>(); //other initializations
        PIs = new ArrayList<PrimeImplicant>();
        finalPIs= new ArrayList<PrimeImplicant>();
        output = new String("");
    }
    private boolean checkDiff(String bit1, String bit2){
       int count = 0;
       for(int index = 0; index < NUM_LITERALS; index++){
           if(bit1.charAt(index) != bit2.charAt(index)){
               count++;
           }
       }
       return (count == 1);
    }
    private int[] combineMinterms(int[] mints1, int[] mints2) {
        ArrayList<Integer> mintBuffer = new ArrayList<Integer>();
        for(int i = 0; i < mints1.length; i++){
                mintBuffer.add(mints1[i]);
        }

        for(int i = 0; i < mints2.length; i++){
                mintBuffer.add(mints2[i]);
        }

        int[] mints = new int[mintBuffer.size()];
        for(int i = 0; i < mints.length; i++){
                mints[i] = mintBuffer.get(i);
        }

        return mints;
    }
    private String diffBit(String bit1, String bit2){
       StringBuilder result = new StringBuilder(bit1);
       for(int index = 0; index < bit1.length() ; index++){
           if(bit1.charAt(index) != bit2.charAt(index)){ 
              result.setCharAt(index, '-');
           }
       }
       return result.toString();
    }
    private char getTerm(int n){
        return new Character((char) (n + 'A'));
    }
    public void run(){
        int size = NUM_LITERALS;
        QMCTable table = new QMCTable(size+1);
        for(Implicant imp : implicants){ 
            table.addImplicant(imp.getNumBits(), imp);
        }
        String binaryResult;
        int[] mintermList;
        ArrayList<Implicant> above, below, impList;
        Implicant imp1, imp2;
        HashMap<Integer, ArrayList<Implicant>> sections;
        while(table.isComparable()){
                impList = new ArrayList<Implicant>();
                sections = table.getQMCTable();
                Entry<Integer, ArrayList<Implicant>> secAbove = null;
                for(Entry<Integer, ArrayList<Implicant>> secBelow : sections.entrySet()){
                        if(secAbove != null){
                                above = secAbove.getValue();
                                below = secBelow.getValue();
                                for(int i = 0; i < above.size(); i++){
                                        for(int j = 0; j < below.size(); j++){
                                                imp1 = above.get(i);
                                                imp2 = below.get(j);
                                                if(checkDiff(imp1.getBinary(), imp2.getBinary())){
                                                        binaryResult = diffBit(imp1.getBinary(), imp2.getBinary());
                                                        mintermList = combineMinterms(imp1.getMinterms(), imp2.getMinterms());
                                                        impList.add(new Implicant(mintermList, binaryResult));
                                                        imp1.setPaired(true);
                                                        imp2.setPaired(true);
                                                }
                                        }
                                }
                        }
                        secAbove = secBelow;
                }
                qmcTable.add(table);
                table = new QMCTable(size--);
                for(Implicant imp: impList){
                        table.addImplicant(imp.getNumBits(), imp);
                }
        };
        System.out.println("Comparison is complete.");
        System.out.println("GENERATED TABLES:");
        for(int i = 0; i < qmcTable.size(); i++){
                System.out.print("\n------------------------------------------TABLE "+i+"\n");
                qmcTable.get(i).printTable();
        }
        ArrayList<Implicant> primeImplicants = new ArrayList<Implicant>(); 
        boolean duplicate = false;
        for(QMCTable table1 : qmcTable){
                for(Implicant imp : table1.getImplicantList()){ //delete duplicates if existing. needs refactoring.
                        if(imp.isPaired() == false){
                                for(Implicant pi : primeImplicants){
                                        if(imp.getBinary().equals(pi.getBinary())){
                                                duplicate = true;
                                        }
                                }
                                if(duplicate == false){
                                        primeImplicants.add(imp);
                                }
                        }
                        duplicate = false;
                }
        }
        ArrayList<Integer> mints = new ArrayList<Integer>();
        for(int m : minterms){
                mints.add(m);
        }
        PIs = new ArrayList<PrimeImplicant>();
        for(Implicant imp : primeImplicants){
                PIs.add(new PrimeImplicant(imp, minterms));
        }
        System.out.println("\nPrime Implicants Table");
        System.out.print("\t");
        print(minterms);
        for(int mint : minterms){                
                for(PrimeImplicant prim : PIs){
                        if(prim.contains(mint)){
                               
                                prim.markMinterm(mint, true);
                        }
                }
        }
        for(PrimeImplicant prim : PIs){
             System.out.println(prim.implicant.binary + "\t" + prim.toString());
        }
        for(int mint : minterms){
                int count = 0;
                for(PrimeImplicant prim : PIs){
                        if(prim.contains(mint)){
                                count++;
                        }
                }
                if(count == 1){
                        for(PrimeImplicant pi : PIs){
                                if(pi.contains(mint)){
                                        if(finalPIs.isEmpty()){
                                            finalPIs.add(pi);
                                        }
                                        else{
                                            if(!finalPIs.contains(pi)){
                                                    finalPIs.add(pi);
                                            }
                                        }
                                }
                        }
                }
        }
        for(PrimeImplicant fimp : finalPIs){
                PIs.remove(fimp);

                for(int m : fimp.getImplicant().getMinterms()){
                        for(PrimeImplicant pi : PIs){
                                pi.markMinterm(m, false);
                        }
                }
        }
        while(!PIs.isEmpty()){
                PrimeImplicant dominant = PIs.get(0);
                for(PrimeImplicant pi : PIs){
                        if(pi.getMarkCount() > dominant.getMarkCount()){
                                dominant = pi;
                        }
                }
                if(dominant.getMarkCount() != 0){
                        finalPIs.add(dominant);
                        PIs.remove(dominant);
                }
                for(int m : dominant.getImplicant().getMinterms()){
                        for(PrimeImplicant pi: PIs){
                                pi.markMinterm(m, false);
                        }
                }
                boolean mark = true;
                for(PrimeImplicant pi : PIs){
                        if(pi.getMarkCount() > 0){
                                mark = false;
                        }
                }
                if(mark == true){
                        break;
                }

        }
        if(!finalPIs.isEmpty()){
                System.out.println("\nEssential Prime Implicants");
                System.out.print("\t");
                print(minterms);
                for(PrimeImplicant pi : finalPIs){
                        System.out.println(pi.implicant.binary + "\t" + pi.toString());
                }
                output = evaluateExpression(finalPIs);
        }
        else{
                output = "1";
        }
        System.out.println("\nF = "+ output);
    }
    private String evaluateExpression(ArrayList<PrimeImplicant> primeImplicants) {
        String expression = new String("");
        String binaryBuffer = new String("");
        for(PrimeImplicant pi : primeImplicants){
            binaryBuffer = pi.getImplicant().getBinary();
            for(int i = 0; i < binaryBuffer.length(); i++){
                    if(binaryBuffer.charAt(i) != '-'){
                            expression += getTerm(i);
                            if(binaryBuffer.charAt(i) == '0'){
                                    expression += "'"; 
                            }
                    }
                    
            }
            
            expression += " + ";
            
        }
//4 4 5 6 8 9 10 13 d 0 7 15  
        return expression.substring(0, expression.length()-2);
    }
    private void print(Implicant[] implicants) {
        for(Implicant item : implicants){
                System.out.println(item.toString());
        }
    }
    private void print(int[] minterms){
        for(int item : minterms){
            System.out.print(String.format("| %s |", item));
        }
        System.out.println();
    }
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String input = "";
        if(scan.hasNextLine()){
            input = scan.nextLine();
        }
        if(!input.isEmpty()){
            QuineMC qmc = new QuineMC(input);
            qmc.run();
        }
        
    }
    
}
