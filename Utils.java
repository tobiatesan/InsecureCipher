import java.util.*;
import java.math.BigInteger;

class Utils {
    /**
     * @return a random integer in the [min, max] range.
     */
    public static int randInt(int min, int max) {
	Random rand = new Random();
	// WARNING: for testing only, not very random
	int randomNum = rand.nextInt((max - min) + 1) + min;
	return randomNum;
    }

    ////////////////////////////////////
    // Arith utils
    ////////////////////////////////////

    /**
     * @return a number m that is coprime to n
     */
    public static int fastCoprime(int n) {
	// WARNING: for testing only, not very random
	// Quick copout
	return (n - 1);
    }

    /**
     * @return x s.t. ax \equiv 1 (mod m)
     */
    public static int modularInverse(int a, int m) {
	return (new BigInteger(""+a)).modInverse(new BigInteger(""+m)).intValue();
    }

    public static int gcd(int a, int b) {
	if (b == 0)
	    return a;
	return gcd(b, a % b);
    }

    ////////////////////////////////////
    // Bit/byte utils
    ////////////////////////////////////

    public static boolean getBit(byte b, int position) {
	return (((b >> position) & 1) == 1) ? true : false;
    }

    public static byte setBit(byte b, int position) {
	return (byte)(b | (1 << position));
    }

    public static byte clearBit(byte b, int position) {
	return (byte)(b & ~(1 << position));
    }

    public static byte bitsToByte(boolean[] bits) {
	byte res = 0;
	for (int i = 7; i >= 0; i--) {
	    if (bits[i]) {
		res = Utils.setBit(res, i);
	    } else {
		res = Utils.clearBit(res, i);
	    }
	}
	return res;
    }

    public static boolean[] byteToBits(byte b) {
	boolean[] res = { false, false, false, false, false, false, false, false };
	for (int i = 7; i >= 0; i--) {
	    res[i] = Utils.getBit(b, i);
	}
	return res;
    }
}
