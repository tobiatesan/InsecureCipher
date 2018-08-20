import java.io.Serializable;

class KeySeed  {
    public final int m;
    public final int w;
    public final int[] ap;
    public KeySeed(int[] ap, int w, int m) {
	this.m = m;
	this.w = w;
	this.ap = ap;
    }

    public String toString() {
	return "Seed; (m,w,a') = ("+ap+", "+w+", "+m+")";
    }
}

class PrivateKey implements Serializable {
    public final int m;
    public final int[] ap;
    // We add a' to the private key for
    // simplicity (used for solving the easy
    // knapsack problem) instead of storing it
    // separately.
    public final int wp;
    public PrivateKey(int[] ap, int wp, int m) {
	this.ap = ap;
	this.wp = wp;
	this.m = m;
    }

    public String toString() {
	return "Private Key; (a', w', m) = ("+wp+", "+m+")";
    }
}

class PublicKey implements Serializable {
    public final int[] a;
    public PublicKey(int[] a) {
	this.a = a;
    }
    public String toString() {
	return "Public Key; (a) = ("+a+")";
    }
}

class MerkleHellman {
    public final static int KEY_SIZE = 7;
    // Don't change this (we are encoding 7-bit ASCII)

    /**
     * Generate keyseed (a', w, m)
     * Box 1 of diagram in Hellman 1979
     */
    public static KeySeed generateKeySeed() {
	int[] ap = new int[KEY_SIZE];
	ap[0] = Utils.randInt(1, KEY_SIZE);
	int sum  = ap[0];
	for (int i = 1; i < KEY_SIZE; i++) {
	    ap[i] = Utils.randInt(sum+1, 2*sum);;
	    sum += ap[i];
	}
	int m = Utils.randInt(sum+1, 2*sum);
	int wp = Utils.fastCoprime(m);
	return new KeySeed(ap, wp, m);
    }

    /**
     * Generate public key
     * Box 2 of diagram in Hellman 1979
     */
    public static PublicKey createPublic(KeySeed seed) {
	int[] a = new int[KEY_SIZE];
	for (int i = 0; i < KEY_SIZE; i++) {
	    a[i] = ((seed.ap[i] * seed.w) % seed.m);
	}
	return new PublicKey(a);
    }

    /**
     * Generate private key
     * Box 6 of diagram in Hellman 1979
     * Obviously must use the SAME keyseed for a keypair to work
     */
    public static PrivateKey createPrivate(KeySeed seed) {
	int wp = Utils.modularInverse(seed.w, seed.m);
	return new PrivateKey(seed.ap, wp, seed.m);
    }

    /**
     * Encrypt a single char/byte
     * Box 5 of Hellman 1979
     */
    public static int encrypt(PublicKey pub, byte in) {
	boolean[] x = Utils.byteToBits(in);
	int c = 0;
	for (int i = 0; i < KEY_SIZE; i++) {
	    if (x[i]) {
		c += pub.a[i];
	    }
	}
	return c;
    }

    /**
     * Solve easy knapsack
     * Box 8 of Hellman 1979
     * @return An array of _bits_, i.e. T/F values signaling whether
     * the given coefficient is 1 or 0.
     */
    private static boolean[] solveEasyKnapsack(int cp, int[] ap) {
	int tmp = 0;
	boolean[] x = new boolean[8];
	for (int i = (KEY_SIZE - 1); i >= 0; i--) {
	    tmp = cp - ap[i];
	    if(tmp < 0) {
		x[i] = false;
	    } else {
		x[i] = true;
		cp = tmp;
	    }
	}
	return x;
    }

    /**
     * Decrypt _and_ solve (easy) knapsack
     * i.e. turn C into C' (Box 7)
     * _and_, afterwards, solve knapsack with a call to appropriate method
     */
    public static byte decrypt(PrivateKey priv, int c) {
	int cp = (c * priv.wp) % priv.m;
	boolean[] bits = solveEasyKnapsack(cp, priv.ap);
	// Convert bits back to byte, uninteresting
	return Utils.bitsToByte(bits);
    }
}
