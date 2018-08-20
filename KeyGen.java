import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class KeyGen {
    public static void main(String[] args) throws IOException {
	// Sane user interface
	// String basename = args[0];

	// Stupid user interface
	System.out.println("Inserisci il nome per la coppia di chiavi");
	BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
	String basename=buffer.readLine().trim();
	// End stupid user interface

	String pubname = basename+".pubkey";
	String privname = basename+".privkey";

	KeySeed seed = MerkleHellman.generateKeySeed();
	PrivateKey priv = MerkleHellman.createPrivate(seed);
	PublicKey pub = MerkleHellman.createPublic(seed);

	// write object to file
	FileOutputStream fos = new FileOutputStream(pubname);
	ObjectOutputStream oos = new ObjectOutputStream(fos);
	oos.writeObject(pub);
	oos.close();

	FileOutputStream fos1 = new FileOutputStream(privname);
	ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
	oos1.writeObject(priv);
	oos1.close();

	System.out.println("Keys saved to "+pubname+", "+privname);
    }
}
