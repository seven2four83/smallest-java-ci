package acime;

import acime.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MailComposerTest {
    /**
     * Given the correct details, the email must get sent
     */
    @Test
    public void validDetails(){
        Assertions.assertTrue(new MailComposer().sendEmail("vikibornin1991@gmail.com","From Test fn","Test fn subject"));
    }

    /**
     * When the recipient info is invalid, no email is sent
     */
//    @Test
//    public void invalidRecipient(){
//        Assertions.assertFalse(new MailComposer().sendEmail("invalid","From Test fn","Test fn subject"));
//    }


    /**
     * When the body is empty, no email is sent
     */
    @Test
    public void EmptySubject(){
        Assertions.assertFalse(new MailComposer().sendEmail("invalid","From Test fn",""));
    }


    /**
     * When the body is empty, no email is sent
     */
    @Test
    public void EmptyBody(){
        Assertions.assertFalse(new MailComposer().sendEmail("invalid","","Test fn subject"));
    }
}
