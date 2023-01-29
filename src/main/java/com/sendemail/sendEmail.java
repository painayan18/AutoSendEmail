package com.sendemail;

import java.text.Normalizer;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class sendEmail {
    public static void main(String[] args) {

    }


    public static void sendEmails(){
        // email and login info as strings
        String emailTo = "vedantini@hotmail.com";
        String emailFrom = "nayanspai@gmail.com";
        String fromPassword = "wfgcwasawppdvuwh";

        // Set new properties for the server
        Properties prop = new Properties();

        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Get the Session object and pass sender's username and password
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, fromPassword);
            }
        });

        // To debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From
            message.setFrom(new InternetAddress(emailFrom));

            // Set To
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));

            // Set Subject
            message.setSubject("Automation Test Email");

            // Set email body
            message.setText("This is a test. \nThis is an automated message.\nIt's working :)");

            System.out.println("Sending email...");

            // Send the email
            Transport.send(message);
            System.out.println("Email Sent Successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
