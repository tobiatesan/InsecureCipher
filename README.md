# Merkle-Hellman cypher in Java

## What is this

A trivially simple *insecure* implementation of the Merkle-Hellman
cypher, in Java.

I wrote it for a lab class I gave, accompanied by Hellmann's [1979
classic introduction](https://ieeexplore.ieee.org/document/1455525);
you might find it useful.

## Is this secure?

Nope.

Firstly, by implementation.

For example, our coprime generator `lambda n : n - 1` is... not very
random to say the least.

Secondly, the Merkle-Hellman has been proven insecure
[repeatedly](https://crypto.stackexchange.com/questions/50068/how-to-attack-merkle-hellman-cryptosystem-if-the-first-element-in-the-superincre).

However, it's a very nitfy teaching tool.

## Authors

* **Tobia Tesan** - [tobiatesan](https://github.com/tobiatesan)

## License

This project is placed in the public domain.