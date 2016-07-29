import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.SecureRandom;
import java.time.Instant;

public class StrongSecureRandomTest
{
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
    public void test()
            throws Exception
    {
        SecureRandom random = SecureRandom.getInstance("NativePRNGNonBlocking");
        for (int i = 0; i < 100; i++) {
            err("i = " + i);
            err("random.nextInt() = " + random.nextInt());
        }
    }

    private static void err(String s)
    {
        System.err.println(Instant.now() + " AESGCMTest: " + s);
        System.err.flush();
    }
}
