import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;

import static org.junit.Assert.assertArrayEquals;

public class AESGCMTest
{
    private static final int AES_KEY_SIZE = 128;
    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    @Test
    public void testEncryptDecrypt()
            throws Exception
    {
        SecureRandom random = SecureRandom.getInstanceStrong();

        // Shared secret
        int keySizeBytes = AES_KEY_SIZE / 8;
        byte[] keyRaw = new byte[keySizeBytes];
        random.nextBytes(keyRaw);
        SecretKey key = new SecretKeySpec(keyRaw, "AES");

        byte[] input = "Hello AES-GCM World!".getBytes();

        final byte[] nonce;
        byte[] cipherText;

        // Encrypt
        {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            nonce = new byte[GCM_NONCE_LENGTH];
            random.nextBytes(nonce);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);

            cipherText = cipher.doFinal(input);
        }

        // Decrypt
        byte[] decrypted;
        {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);

            decrypted = cipher.doFinal(cipherText);
        }

        assertArrayEquals(decrypted, input);
    }
}