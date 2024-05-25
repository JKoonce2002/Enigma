package enigma;
import java.util.*;

public class RotaryController {

	Rotor[] rotorArr;
	int incAmt;
	boolean isReflected;
	Rotor reflector;
	
	public RotaryController(int increment) {
		rotorArr = new Rotor[3];
		rotorArr[0] = createRotor("IC", 0);
		rotorArr[1] = createRotor("IIC", 0);
		rotorArr[2] = createRotor("IIIC", 0);
		reflector = createRotor("UKW", 0);
		incAmt = increment;
		isReflected = false;
	}
	
	public void setWiring(int rotorIndex, String type) {
		ArrayList<Dictionary<Character, Character>> dicts = getRotary(type);
		rotorArr[rotorIndex].changeEncoding(dicts.get(0), dicts.get(1));
	}
	
	//set the offset of a specified rotor
	public void setOffset(int rotorIndex, int offset) {
		rotorArr[rotorIndex].setOffset(offset-1);
	}
	
	//actually do the encoding
	public String encode(String message, boolean reflect) {
		String fixedMessage = message.replace(" ", "");
		fixedMessage = fixedMessage.toUpperCase();
		char[] messageArr = fixedMessage.toCharArray();
		StringBuffer postEnc = new StringBuffer();
		
		int counter = 0;
		for(char i : messageArr) {
			counter++;
			//all three rotors
			if(i != '\n'){ //if not a new line
				//always encode thrice
				char encChar = rotorArr[2].encode(rotorArr[1].encode(rotorArr[0].encode(i)));
				
				//optional reflection back across the wheels
				if (reflect) {
					encChar = reflector.encode(encChar);
					encChar = rotorArr[0].encode(rotorArr[1].encode(rotorArr[2].encode(encChar)));
				}
				
				//append char
				postEnc.append(encChar);
				doIncrement(); //only increment on non-newline
				
			} else { //if a newline, append it
				postEnc.append('\n');
				counter = 0;
			}
			if (counter%5 == 0 && counter != 0) postEnc.append(' ');
		}
		
		return postEnc.toString();
	}
	
	//actually do the decoding
	public String decode(String message, boolean reflect) {
		String fixedMessage = message.replace(" ", "");
		fixedMessage = fixedMessage.toUpperCase();
		char[] messageArr = fixedMessage.toCharArray();
		StringBuffer postDec = new StringBuffer();
		
		int counter = 0;
		for(char i : messageArr) {
			counter++;
			//all three rotors, but backwards
			if(i != '\n') { //if not a newline
				
				char decChar = i;
				
				//reflection happens first if it happens
				if (reflect) {
					decChar = rotorArr[2].decode(rotorArr[1].decode(rotorArr[0].decode(decChar)));
					decChar = reflector.encode(decChar);
				}
				
				decChar = rotorArr[0].decode(rotorArr[1].decode(rotorArr[2].decode(decChar)));
				
				postDec.append(decChar);
				doIncrement();
			} else { //if a newline, append it
				postDec.append('\n');
				counter = 0;
			}
			if (counter%5 == 0 && counter != 0) postDec.append(' ');
		}
		
		return postDec.toString();
	}
	
	//increment the rotors as necessary
	private void doIncrement() {
		if(rotorArr[0].inc(incAmt)) {
			if(rotorArr[1].inc(1)) {
				rotorArr[2].inc(1);
			}
		}
	}
	
	//returns a Rotor object with the specified wiring and offset
	private Rotor createRotor(String type, int offset) {
		ArrayList<Dictionary<Character, Character>> wiring = getRotary(type);
		return new Rotor(wiring.get(0), wiring.get(1), offset);
	}
	
	//add any new wirings here
	public ArrayList<Dictionary<Character, Character>> getRotary(String type) {
		
		ArrayList<Dictionary<Character, Character>> rotors = new ArrayList<Dictionary<Character, Character>>();
		
		Dictionary<Character, Character> forward = new Hashtable<>();
		Dictionary<Character, Character> backward = new Hashtable<>();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", cipher = "";
		
		//Commercial Enigma Rotor I
		if(type.equals("IC")) {
			cipher = "DMTWSILRUYQNKFEJCAZBPGXOHV";
		}
		
		//Commercial Enigma Rotor II
		else if(type.equals("IIC")) {
			cipher = "HQZGPJTMOBLNCIFDYAWVEUSRKX";
		}
		
		//Commercial Enigma Rotor III
		else if(type.equals("IIIC")) {
			cipher = "UQNTLSZFMREHDPXKIBVYGJCWOA";
		}
		
		//1941 Reflector
		else if(type.equals("UKW")) {
			cipher = "QYHOGNECVPUZTFDJAXWMKISRBL";
		}
		
		//1941 Keyboard Order
		else if(type.equals("EKW")) {
			cipher = "QWERTZUIOASDFGHJKPYXCVBNML";
		}
		
		//1941 Enigma Rotor I
		if(type.equals("I")) {
			cipher = "JGDQOXUSCAMIFRVTPNEWKBLZYH";
		}
		
		//1941 Enigma Rotor II
		else if(type.equals("II")) {
			cipher = "NTZPSFBOKMWRCJDIVLAEYUXHGQ";
		}
		
		//1941 Enigma Rotor III
		else if(type.equals("III")) {
			cipher = "JVIUBHTCDYAKEQZPOSGXNRMWFL";
		}
		
		//Current Keyboard Layout
		else if(type.equals("KBD")) {
			cipher = "QWERTYUIOPASDFGHJKLZXCVBNM";
		}
		
		//create the dictionaries
		char[] cipherArr = cipher.toCharArray();
		char[] alphaArr = alphabet.toCharArray();
		for(int i = 0; i < cipherArr.length; i++) {
			forward.put(alphaArr[i], cipherArr[i]);
			backward.put(cipherArr[i], alphaArr[i]);
		}
		
		//adds the encoder dictionary to index 0, decoder to 1
		rotors.add(forward);
		rotors.add(backward);
		
		return rotors;
	}
}
