import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class Encrypt {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// Sane user interface
	// String privkeybasename = args[0];
	// String messagebasename = args[1];

	// Stupid user interface
	System.out.println("Enter name of the pubkey file to use");
	BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
	String pubkeybasename=buffer.readLine().trim();
	System.out.println("Enter name of the message file to write to");
	String messagebasename=buffer.readLine().trim();
	// End stupid user interface

	String pubname = pubkeybasename+".pubkey";
	String messagename = messagebasename+".msg";


	// read object from file
	FileInputStream fis = new FileInputStream(pubname);
	ObjectInputStream ois = new ObjectInputStream(fis);
	PublicKey pub = (PublicKey) ois.readObject();
	ois.close();

	System.out.println("Inserisci il tuo messaggio...");
	String plaintext = buffer.readLine().trim();

	byte[] original = plaintext.getBytes("US-ASCII");
	int[] encrypted = new int[original.length];

	for (int i = 0;
		i < original.length;
		i++) {
	    encrypted[i] = MerkleHellman.encrypt(pub, original[i]); // intList);
	}

	ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(messagename));
	outputStream.writeObject(encrypted);
    }
}
