import org.junit.Test;

import java.security.SecureRandom;

public class StrongSecureRandomTest
{
    @Test
    public void test()
            throws Exception
    {
        SecureRandom random = SecureRandom.getInstanceStrong();
        System.out.println(random.nextInt());
    }
}
