import java.util.*;
import java.math.*;

public class RSA {

    public static boolean isPrime(int n) {
        int m = n / 2;

        if (n <= 1) {
            return false;
        } 

        for (int i = 2; i <= m; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static int findNthPrimeInInterval(int n, int lo, int hi) {
        ArrayList<Integer> primes = new ArrayList<Integer>();
        
        for (int i = lo; i <= hi; i++) {
            if (isPrime(i)) {
                primes.add(i);
                if (primes.size() == n) break;
            }
        }
        return primes.get(n - 1);
    }

    public static int get_n(int p, int q) {
        return p * q;
    }

    public static int get_z(int p, int q) {
        return (p - 1) * (q - 1);
    }

    public static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }

    public static int get_e(int z) {
        int e;
        for (e = 2; e < z; e++) {
            if (gcd(e, z) == 1) {
                break;
            }
        }
        return e;
    }

    public static int get_d(int z, int e) {
        int d = 0;
        for (int i = 1; i <= z; i++) {
            int x = (e * i) % z;

            if (x == 1) {
                d = i;
                break;
            }
        }
        return d;
    }

    public static BigInteger encrypt(int msg, int e, int n) {
        BigInteger m = BigInteger.valueOf(msg);
        BigInteger cipherText = m.pow(e).mod(BigInteger.valueOf(n));
        return cipherText;
    }

    public static BigInteger decrypt(BigInteger c, int d, int n) {
        BigInteger plainText = (c.pow(d)).mod(BigInteger.valueOf(n));
        return plainText;
    }

    public static int[] mapToInt(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        int[] ints = new int[chars.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = chars[i] - 'a';
        }
        return ints;
    }

    public static char[] mapToChar(BigInteger[] n) {
        char[] chars = new char[n.length];
        for (int i = 0; i < n.length; i++) {
            chars[i] = (char) (n[i].intValue() + 97);
        }
        return chars;
    }

    public static void main(String[] args) {
        String arg = args[0];
        int p = findNthPrimeInInterval(10, 1000, 10000);
        int q =  findNthPrimeInInterval(19, 1000, 10000);
        int n = get_n(p, q);
        int z = get_z(p, q);
        int e = get_e(z);
        int d = get_d(z, e);
        System.out.println("p (10th prime between 1000 and 10000) = " + p);
        System.out.println("q (19th prime between 1000 and 10000) = " + q);
        System.out.println("n = " + n);
        System.out.println("phi(n) = " +  z);
        System.out.println("the value of e = " + e);
        System.out.println("the value of d = " + d);
        System.out.println("e * d (mod z) = " + (e * d) % z);

        System.out.println("Message received: " + arg);
        int[] mappings = mapToInt(arg);
        System.out.println("chars are: " + Arrays.toString(mappings));
        BigInteger[] cipherText = new BigInteger[mappings.length];
        for (int i = 0; i < mappings.length; i++) {
            cipherText[i] = encrypt(mappings[i], e, n);
        }
        System.out.println("encrypted chars: " + Arrays.toString(cipherText));

        BigInteger[] plainText = new BigInteger[mappings.length];
        for (int j = 0; j < mappings.length; j++) {
            plainText[j] = decrypt(cipherText[j], d, n);
        }
        System.out.println("decrypted chars: " + Arrays.toString(plainText));
        System.out.println("Decrypted message: " + String.valueOf(mapToChar(plainText)));
    }
}
