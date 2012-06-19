package co.earcos.util;

import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncrypterUtil {

  // See http://www.java2s.com/Code/Java/Security/EncryptionanddecryptionwithAESECBPKCS7Padding.htm

  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES/ECB/PKCS7Padding";
  private static final String PROVIDER = "BC";

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  private static byte[] getUniqueCryptKey(String... seed) {
    int keySize = 16;
    byte[] uniqueCryptKey = new byte[keySize];
    byte[][] byteSeed = new byte[seed.length][];

    for (int i = 0; i < seed.length; i++) {
      byteSeed[i] = seed[i].getBytes();
    }

    for (int i = 0; i < keySize; i++) {
      byte[] byteSeedWord = byteSeed[i % seed.length];
      byte b = byteSeedWord[i % byteSeedWord.length];
      uniqueCryptKey[keySize - i - 1] = b;
    }

    return uniqueCryptKey;
  }

  public static byte[] encodeFile(byte[] rawFile, String... seed) throws Exception {
    byte[] uniqueKey = getUniqueCryptKey(seed);
    SecretKeySpec key = new SecretKeySpec(uniqueKey, ALGORITHM);

    Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
    cipher.init(Cipher.ENCRYPT_MODE, key);

    byte[] encodedFile = new byte[cipher.getOutputSize(rawFile.length)];
    int ctLength = cipher.update(rawFile, 0, rawFile.length, encodedFile, 0);
    cipher.doFinal(encodedFile, ctLength);
    return encodedFile;
  }

  public static byte[] decodeFile(byte[] encodedFile, String... seed) throws Exception {
    byte[] uniqueKey = getUniqueCryptKey(seed);
    SecretKeySpec key = new SecretKeySpec(uniqueKey, ALGORITHM);

    Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
    cipher.init(Cipher.DECRYPT_MODE, key);

    byte[] decodedFile = new byte[cipher.getOutputSize(encodedFile.length)];
    int ptLength = cipher.update(encodedFile, 0, encodedFile.length, decodedFile, 0);
    cipher.doFinal(decodedFile, ptLength);
    return decodedFile;
  }
}
