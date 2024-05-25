package enigma;
import java.util.*;

public class Rotor {
	private Dictionary<Character, Character> wiringE;
	private Dictionary<Character, Character> wiringD;
	private int offset;
	
	public Rotor(Dictionary<Character, Character> encode, Dictionary<Character, Character> decode, int startOffset) {
		wiringE = encode;
		wiringD = decode;
		offset = startOffset;
	}
	
	//apply any offset, then get the value from the wired rotor
	public char encode(char preEncode) {
		char changed = preEncode;
		changed += offset;
		while (changed > 90) changed -= 26;
		return wiringE.get(changed);
	}
	
	//apply any offset, then get the value from the wired rotor
		public char decode(char preEncode) {
			char changed = wiringD.get(preEncode);
			changed -= offset;
			while (changed < 65) changed += 26;
			return changed;
		}
	
	//increment offset. True if the above rotor needs to increment as well
	public boolean inc(int amt) {
		offset += amt;
		if (offset >= 26) {
			offset -= 26;
			return true;
		}
		return false;
	}
	
	//for when the offset is, well, set
	public void setOffset(int val) {
		offset = val;
	}
	
	//if the encoder ring is swapped out. Ideally, there's only three objects in the main file
	public void changeEncoding(Dictionary<Character, Character> encode, Dictionary<Character, Character> decode) {
		wiringE = encode;
		wiringD = decode;
	}
}
