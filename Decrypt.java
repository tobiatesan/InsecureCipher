import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class Decrypt {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// Sane user interface
	// String privkeybasename = args[0];
	// String messagebasename = args[1];

	// Stupid user interface
	System.out.println("Enter name of the privkey file to use");
	BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
	String privkeybasename=buffer.readLine().trim();
	System.out.println("Enter name of the message file to decrypt");
	String messagebasename=buffer.readLine().trim();
	// End stupid user interface

	String privname = privkeybasename+".privkey";
	String messagename = messagebasename+".msg";

	int[] encrypted = (int[]) new ObjectInputStream(new FileInputStream(messagename)).readObject();

	// read object from file
	FileInputStream fis = new FileInputStream(privname);
	ObjectInputStream ois = new ObjectInputStream(fis);
	PrivateKey priv = (PrivateKey) ois.readObject();
	ois.close();

	byte[] decrypted = new byte[encrypted.length];
	for (int i = 0;
		i < encrypted.length;
		i++) {
	    decrypted[i] = (MerkleHellman.decrypt(priv, encrypted[i])); // intList);
	}
	System.out.println(new String(decrypted, "US-ASCII"));
    }
}
