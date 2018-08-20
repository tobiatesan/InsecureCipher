import java.nio.charset.Charset;
import java.util.*;
import java.io.UnsupportedEncodingException;

public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        KeySeed seed = MerkleHellman.generateKeySeed();
        PrivateKey priv = MerkleHellman.createPrivate(seed);
        PublicKey pub = MerkleHellman.createPublic(seed);
        System.out.println(priv);
        System.out.println(pub);
        byte[] original = "pippo".getBytes("US-ASCII");
        int[] encrypted = new int[original.length];
        for (int i = 0;
                i < original.length;
                i++) {
            encrypted[i] = MerkleHellman.encrypt(pub, original[i]);
        }
        System.out.println("Encrypted msg: " + Arrays.toString(encrypted));
        byte[] decrypted = new byte[encrypted.length];
        for (int i = 0;
                i < encrypted.length;
                i++) {
            decrypted[i] = (MerkleHellman.decrypt(priv, encrypted[i]));
        }
        System.out.println(new String(decrypted, "US-ASCII"));
    }
}
