import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.time.Instant;

import static org.junit.Assert.assertArrayEquals;

public class AESGCMTest
{
    static {
        err("static init");
    }

    private static final int AES_KEY_SIZE = 128;
    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    public AESGCMTest()
    {
        err("constructor");
    }

    @BeforeClass
    public static void setUpClass()
            throws Exception
    {
        err("setUpClass");
    }

    @AfterClass
    public static void tearDownClass()
            throws Exception
    {
        err("tearDownClass");
    }

    @Before
    public void setUp()
            throws Exception
    {
        err("setUp");
    }

    @After
    public void tearDown()
            throws Exception
    {
        err("tearDown");
    }

    @Test
    public void testEncryptDecrypt()
            throws Exception
    {
        err("start");

        SecureRandom random = SecureRandom.getInstanceStrong();

        // Shared secret
        int keySizeBytes = AES_KEY_SIZE / 8;
        byte[] keyRaw = new byte[keySizeBytes];
        random.nextBytes(keyRaw);
        SecretKey key = new SecretKeySpec(keyRaw, "AES");

        err("generated shared secret");

        byte[] input = "Hello AES-GCM World!".getBytes();

        final byte[] nonce;
        byte[] cipherText;

        // Encrypt
        {
            err("Encrypt: 1");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            err("Encrypt: 2");
            nonce = new byte[GCM_NONCE_LENGTH];
            err("Encrypt: 3");
            random.nextBytes(nonce);
            err("Encrypt: 4");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
            err("Encrypt: 5");
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            err("Encrypt: 6");

            cipherText = cipher.doFinal(input);
            err("Encrypt: 7");
        }

        // Decrypt
        byte[] decrypted;
        {
            err("Decrypt: 1");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            err("Decrypt: 2");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
            err("Decrypt: 3");
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            err("Decrypt: 4");

            decrypted = cipher.doFinal(cipherText);
            err("Decrypt: 5");
        }

        assertArrayEquals(decrypted, input);

        err("Done");
    }

    private static void err(String s)
    {
        System.err.println(Instant.now() + " AESGCMTest: " + s);
        System.err.flush();
    }
}