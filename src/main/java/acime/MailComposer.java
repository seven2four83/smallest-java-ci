package acime;

import java.io.File;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailComposer {
    public boolean sendEmail(String recipients, String body, String subject){
        boolean didSend = false;
        //Approach 1: Gmail SMTP

        final String username = "ciserveremailsender@gmail.com";
        final String password = "DH2642ciserver";

//        final String username = "invalid";
//        final String password = "invalid";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            if (recipients.length() == 0){
                //Dummy hardcoding for local testing
                recipients = "vikibornin1991@gmail.com";
                return false;
            }
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients));
            if(subject.length() == 0){
                //Dummy hardcoding for local testing
                subject = "Testing Subject";
                return false;
            }
            message.setSubject(subject);
            if(body.length() == 0){
                //Dummy hardcoding for local testing
                body = "Dear Mail Crawler,"
                        + "\n\n No spam to my email, please!";
                return false;
            }
            message.setText(body);

            Transport.send(message);
            didSend = true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        finally{
            return didSend;
        }


        //Approach 2
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", true);
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.mailtrap.io");
//        prop.put("mail.smtp.port", "25");
//        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
//
//        Session session = Session.getDefaultInstance(prop,
//                new javax.mail.Authenticator(){
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(
//                                "ciserverstatuspost@gmail.com", "DH2642ciserver");
//                    }
//                });
//
//        try {
//
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("ciserverstatuspost@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("vikibornin1991@gmail.com"));
//            message.setSubject("Mail Subject");
//
//            String msg = "This is my first email using JavaMailer";
//
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(msg, "text/html");
//
//            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//            attachmentBodyPart.attachFile(new File("pom.xml"));
//
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//            multipart.addBodyPart(attachmentBodyPart);
//
//            message.setContent(multipart);
//
//            Transport.send(message);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
