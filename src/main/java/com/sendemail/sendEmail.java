package com.sendemail;

import java.sql.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.openmbean.OpenMBeanConstructorInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class sendEmail {
    public static void main(String[] args) {

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Boolean> knowMore = new ArrayList<Boolean>();
        ArrayList<String> emailIDs = new ArrayList<String>();

        names = getNames();
        knowMore = getKnowMore();
        emailIDs = getEmailIDs();

        sendEmails(names, knowMore, emailIDs);


        for (String x : names) {
            System.out.println(x);
        }

        for (Boolean x : knowMore){
            System.out.println(x);
        }

        for (String x : emailIDs){
            System.out.println(x);
        }

    }

    public static ArrayList<String> getNames() {

        ArrayList<String> names = new ArrayList<String>();

        try {

            // open workbook
            String filePath = "Book1.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(filePath);
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            // format all cell data to object type
            DataFormatter formatter = new DataFormatter();  // convert any cell data to Object type
            Object value;       // to store the input from the cell

            // get index of last row in sheet
            int rowCount = sheet.getLastRowNum();

            for (int x = 0; x < rowCount; x++) {
                value = formatter.formatCellValue(sheet.getRow(x + 1).getCell(0));

                if (value.toString() == "") {
                    names.add("null");
                } else {
                    names.add(value.toString());
                }
            }


        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return names;
    }

    public static ArrayList<Boolean> getKnowMore() {

        ArrayList<Boolean> knowMore = new ArrayList<Boolean>();

        try {

            // open workbook
            String filePath = "Book1.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(filePath);
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            // format all cell data to object type
            DataFormatter formatter = new DataFormatter();  // convert any cell data to Object type
            Object value;       // to store the input from the cell

            // get index of last row in sheet
            int rowCount = sheet.getLastRowNum();

            for (int x = 0; x < rowCount; x++) {
                value = formatter.formatCellValue(sheet.getRow(x + 1).getCell(1));

                if (value == "Yes") {
                    knowMore.add(true);
                } else {
                    knowMore.add(false);
                }
            }


        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return knowMore;
    }


    public static ArrayList<String> getEmailIDs() {

        ArrayList<String> emails = new ArrayList<String>();

        try {

            // open workbook
            String filePath = "Book1.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(filePath);
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            // format all cell data to object type
            DataFormatter formatter = new DataFormatter();  // convert any cell data to Object type
            Object value;       // to store the input from the cell

            // get index of last row in sheet
            int rowCount = sheet.getLastRowNum();

            for (int x = 0; x < rowCount; x++) {
                value = formatter.formatCellValue(sheet.getRow(x + 1).getCell(2));

                if (value.toString() == "") {
                    emails.add("null");
                } else {
                    emails.add(value.toString());
                }
            }


        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return emails;
    }

    public static void sendEmails(ArrayList<String> names, ArrayList<Boolean> knowMore, ArrayList<String> emailIDs) {

        // sender's email login info as strings
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

        for (int i = 0; i < names.size(); i++) {
            if(knowMore.get(i)) {
                try {
                    // Create a default MimeMessage object
                    MimeMessage message = new MimeMessage(session);

                    // Set From
                    message.setFrom(new InternetAddress(emailFrom));

                    // Set To
                    message.setRecipients(Message.RecipientType.TO, emailIDs.get(i));

                    // Set Subject
                    message.setSubject("Automation Test Email");

                    // Set email body
                    message.setText("Hi " + names.get(i) + ",\nThis is a test. \nThis is an automated message from the CSU East Bay Library.");

                    System.out.println("Sending email to " + emailIDs.get(i) + "...");

                    // Send the email
                    Transport.send(message);
                    System.out.println("Email Sent Successfully.");

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } else {
                continue;
            }
        }
    }
}

