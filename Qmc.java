package qmc;

import java.util.*;
import java.util.Map.*;
import java.math.*;

public class Qmc {
    public class PrimeImplicant {
	private Implicant imp;
	private HashMap<Integer, Boolean> mintermList;
	public PrimeImplicant(Implicant imp, int[] mintermList){
		this.imp = imp;
		this.mintermList= new HashMap<Integer, Boolean>();
		initializeMintermList(mintermList);
	}

	private void initializeMintermList(int[] mintlist) {
		for(Integer mint : mintlist){
			mintermList.put(mint, false);
		}
	}

	public void markMinterm(int minterm, boolean key){
		mintermList.put(minterm, key);
	}
	

	public boolean contains(int minterm){
		for(int mint : imp.getMinterms()){
			if(mint == minterm){
				return true;
			}
		}
		return false;
	}

	public String toString(){
		String s = new String("");
		for(Entry<Integer, Boolean> mint : mintermList.entrySet()){
			s+="| ";
			if(mint.getValue() == true){
				s += "x";
			}
			else{
				s +="_";
			}
			s+=" |";
		}
		
		s += "\t" + Arrays.toString(imp.getMinterms());
		s+= "\t" + this.getMarkCount();
		return s;
	}
	

	public int getMarkCount(){
		int count = 0;
		
		for(Entry<Integer, Boolean> mint : mintermList.entrySet()){
			if(mint.getValue() == true){
				count++;
			}
		}
		
		return count;
	}
	
	public Implicant getImplicant(){
		return imp;
	}
    }
    public class QMCTable {

	private final int size;

	private HashMap<Integer, ArrayList<Implicant>> mcQuineSections; 
	
	public QMCTable(int size){
		this.size = size;
		mcQuineSections = new HashMap<Integer, ArrayList<Implicant>>();
		for(int bitCount = 0; bitCount < size; bitCount++){
			mcQuineSections.put(bitCount, new ArrayList<Implicant>());
		}
	}

	public int getSize(){
		return size;
	}

	public void addImplicant(int bitCount, Implicant imp) {
		ArrayList<Implicant> bufferGroup = mcQuineSections.get(bitCount);
		bufferGroup.add(imp);
	}
	
	public HashMap<Integer, ArrayList<Implicant>> getMcQuineSections(){
		return mcQuineSections;
	}

	public void printTable(){
		ArrayList<Implicant> bufferGroup;
		for(int i = 0; i < size; i++){
			
			bufferGroup = mcQuineSections.get(i);
			
			if(bufferGroup.isEmpty()){
				System.out.println("SECTION: "+i+"");
				System.out.println("EMPTY!");
			}
			else{
				System.out.println("SECTION: "+i+"");
				System.out.println("| BitCount | BinaryValue |  Mark | MintermList");
				for(Implicant imp :  bufferGroup){
					System.out.println(imp.toString());
				}
			}
			System.out.println();
		}
	}
	
	public ArrayList<Implicant> getImplicantList(){
		ArrayList<Implicant> implicantList = new ArrayList<Implicant>();
		for(Map.Entry<Integer, ArrayList<Implicant>> entry : mcQuineSections.entrySet()){
			for(Implicant imp : entry.getValue()){
				implicantList.add(imp);
			}
		}

		return implicantList;
	}

	
	public boolean isComparable() {
			return (mcQuineSections.size() > 1) ? true : false;
	}
    }
    public class Implicant{
	
	private int bitCount;
	private int[] minterms;
	private String binaryValue;
	private boolean isPaired; 

	public Implicant(int[] minterms, String binaryValue) {
		this.binaryValue = new String(binaryValue);
		this.minterms = minterms;
		setPaired(false);
		setBitCount();
        }
	private void setBitCount(){
		bitCount = 0;
		for(char digit : binaryValue.toCharArray()){
			if(digit == '1'){
				bitCount++;
			}
		}
	}
	
	public int getBitCount(){
		return bitCount;
	}

	public int getMinterm(){
		return minterms[0];
	}
	
	public int[] getMinterms(){
		return minterms;
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
	
	public String getBinaryValue(){
		return binaryValue;
	}

	public String toString(){
		return String.format("| %8d | %11s | %5s | %s ", bitCount, binaryValue, isPaired, Arrays.toString(minterms));
	}
    }
    
        private final int LITERAL_COUNT;
	private Implicant[] implicants;
	private int[] minterms;
	private ArrayList<QMCTable> mcquineTables;
	private ArrayList<PrimeImplicant> primeImps, finalImps;
	private String output;
	
	/**
	 * Controller constructor. Initializes the quine-mccluskey engine. 
	 * @param input string containing the minterms separated by space.
	 */
	public Qmc(String input) {
		System.out.println("\nInitializing Quine-McCluskey data structures...\n");
		String[] tokens = input.split(" ");
		int MINTERM_COUNT = tokens.length;
		minterms = new int[MINTERM_COUNT];
		for(int i = 0; i < MINTERM_COUNT; i++){ //parse minterms
			try{
				minterms[i] = Integer.parseInt(tokens[i]);
			}
			catch(NumberFormatException e){
				System.out.println("ERROR: The input contains a non-numerical value.");
				e.printStackTrace();
			}
		}
		Arrays.sort(minterms); //sort minterms in ascending order to place the highest item in the last entry of the array 
		System.out.print("MINTERMS:\t\t\t");
		print(minterms);
		System.out.println("NUMBER OF MINTERMS:\t\t" + minterms.length);
		LITERAL_COUNT = evaluateLiteralCount(minterms[MINTERM_COUNT-1]);
		System.out.println("NUMBER OF LITERALS TO USE:\t" + LITERAL_COUNT);
		implicants = new Implicant[MINTERM_COUNT];
		System.out.println("\nInitializing list of implicants...\n");
		String raw, formatted;
		int[] mintermList;
		for(int i = 0; i < MINTERM_COUNT; i++){
			mintermList = new int[]{minterms[i]};
			raw = Integer.toBinaryString(minterms[i]);
			formatted = String.format("%0"+LITERAL_COUNT+"d", new BigInteger(raw));
			implicants[i] = new Implicant(mintermList, formatted);
		}
		System.out.println("IMPLICANT LIST:\n| BitCount | BinaryValue |  Mark | MintermList");
		print(implicants);
		mcquineTables = new ArrayList<QMCTable>(); //other initializations
		primeImps = new ArrayList<PrimeImplicant>();
		finalImps = new ArrayList<PrimeImplicant>();
		output = new String("");
	}
	private int evaluateLiteralCount(int maxMinterm){
		int exponent = 0;
		while(maxMinterm >= Math.pow(2, exponent)){
                    exponent++;
		}
		return exponent; 
	}

	public void runQuineMcCluskey(){
		System.out.println("\nRunning Quine McCluskey...");
		int size = LITERAL_COUNT;
		QMCTable bufferTable = new QMCTable(size+1);
		for(Implicant imp : implicants){ 
			bufferTable.addImplicant(imp.getBitCount(), imp);
		}
		String binaryResult;
		int[] mintermList;
		ArrayList<Implicant> above, below, impList;
		Implicant imp1, imp2;
		HashMap<Integer, ArrayList<Implicant>> sections;
		while(bufferTable.isComparable()){ //loop to compare all binary values. Needs refactoring.
			impList = new ArrayList<Implicant>();
			sections = bufferTable.getMcQuineSections();
			Map.Entry<Integer, ArrayList<Implicant>> secAbove = null;
			for(Map.Entry<Integer, ArrayList<Implicant>> secBelow : sections.entrySet()){
				if(secAbove != null){
					above = secAbove.getValue();
					below = secBelow.getValue();
					for(int i = 0; i < above.size(); i++){
						for(int j = 0; j < below.size(); j++){
							imp1 = above.get(i);
							imp2 = below.get(j);
							if(difference(imp1.getBinaryValue(), imp2.getBinaryValue()) == 1){
								binaryResult = evaluateBinaryValue(imp1.getBinaryValue(), imp2.getBinaryValue());
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
			mcquineTables.add(bufferTable);
			bufferTable = new QMCTable(size--);
			for(Implicant imp: impList){
				bufferTable.addImplicant(imp.getBitCount(), imp);
			}
		};
		System.out.println("Comparison is complete.");
		System.out.println("GENERATED TABLES:");
		for(int i = 0; i < mcquineTables.size(); i++){
			System.out.print("\n------------------------------------------TABLE "+i+"\n");
			mcquineTables.get(i).printTable();
		}
		ArrayList<Implicant> primeImplicants = new ArrayList<Implicant>(); 
		boolean duplicate = false;
		for(QMCTable table : mcquineTables){
			for(Implicant imp : table.getImplicantList()){ //delete duplicates if existing. needs refactoring.
				if(imp.isPaired() == false){
					for(Implicant pi : primeImplicants){
						if(imp.getBinaryValue().equals(pi.getBinaryValue())){
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
		primeImps = new ArrayList<PrimeImplicant>();
		for(Implicant imp : primeImplicants){
			primeImps.add(new PrimeImplicant(imp, minterms));
		}
		System.out.println("\nPrime Implicants Table");
		for(int mint : minterms){
			for(PrimeImplicant prim : primeImps){
				if(prim.contains(mint)){
					prim.markMinterm(mint, true);
				}
			}
		}
		for(PrimeImplicant prim : primeImps){
			System.out.println(prim.toString());
		}
		for(int mint : minterms){
			int count = 0;
			for(PrimeImplicant prim : primeImps){
				if(prim.contains(mint)){
					count++;
				}
			}
			if(count == 1){
				for(PrimeImplicant pi : primeImps){
					if(pi.contains(mint)){
						if(finalImps.isEmpty()){
							finalImps.add(pi);
						}
						else{
							if(!finalImps.contains(pi)){
								finalImps.add(pi);
							}
						}
					}
				}
			}
		}
		for(PrimeImplicant fimp : finalImps){
			primeImps.remove(fimp);

			for(int m : fimp.getImplicant().getMinterms()){
				for(PrimeImplicant pi : primeImps){
					//pi.removeMinterm(m);
					pi.markMinterm(m, false);
				}
			}
		}
		while(!primeImps.isEmpty()){
			PrimeImplicant dominant = primeImps.get(0);
			for(PrimeImplicant pi : primeImps){
				if(pi.getMarkCount() > dominant.getMarkCount()){
					dominant = pi;
				}
			}
			if(dominant.getMarkCount() != 0){
				finalImps.add(dominant);
				primeImps.remove(dominant);
			}
			for(int m : dominant.getImplicant().getMinterms()){
				for(PrimeImplicant pi: primeImps){
//					pi.removeMinterm(m);
					pi.markMinterm(m, false);
				}
			}
			boolean mark = true;
			for(PrimeImplicant pi : primeImps){
				if(pi.getMarkCount() > 0){
					mark = false;
				}
			}
			if(mark == true){
				break;
			}

		}
		if(!finalImps.isEmpty()){
			System.out.println("\nEssential Prime Implicants");
			print(minterms);
			for(PrimeImplicant pi : finalImps){
				System.out.println(pi.toString());
			}
			output = evaluateExpression(finalImps);
		}
		else{
			output = "1";
		}
		System.out.println("\nThe simplified expression is: "+output);
	}
	
	private String evaluateBinaryValue(String binary1, String binary2) {
		String result = new String("");
		for(int i = 0; i < binary1.length(); i++){
			if(binary1.charAt(i) == binary2.charAt(i)){
				result+= binary1.charAt(i);
			}
			else{
				result += "-";
			}
		}
		return result;
	}

	private String evaluateExpression(ArrayList<PrimeImplicant> primeImplicants) {
		String expression = new String("");
		String binaryBuffer, term = new String("");
		for(PrimeImplicant pi : primeImplicants){
			binaryBuffer = pi.getImplicant().getBinaryValue();
			for(int i = 0; i < binaryBuffer.length(); i++){
				if(binaryBuffer.charAt(i) != '-'){
					expression += getTerm(i);
					if(binaryBuffer.charAt(i) == '0'){
						expression += "'"; 
					}
				}
			}
			expression += "+";
		}

		return expression.substring(0, expression.length()-1);
	}

	private char getTerm(int n){
		return new Character((char) (n + 'a'));
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

	private int difference(String binary1, String binary2){
		int difference = 0;

		for(int i = 0; i < binary1.length(); i++){
			if(binary1.charAt(i) != binary2.charAt(i)){
				difference++;
			}
		}

		return difference;
	}


	private void print(int[] minterms){
		for(int item : minterms){
			System.out.print(String.format("| %s |", item));
		}
		System.out.println();
	}


	private void print(Implicant[] implicants) {
		for(Implicant item : implicants){
			System.out.println(item.toString());
		}
	}
	

	public static void main(String[] args) {
		Scanner mcQuineView = new Scanner(System.in);
		String input = new String("");
		System.out.println("Quine-McCluskey Simulator!");
		System.out.println("Please input all the minterms that evaluate to 1 separated by space:");
		
		if(mcQuineView.hasNextLine()){
			input = mcQuineView.nextLine();
		}
		
		if(!input.isEmpty()){
			Qmc engine = new Qmc(input);
			engine.runQuineMcCluskey();
		}
        }

    
}
