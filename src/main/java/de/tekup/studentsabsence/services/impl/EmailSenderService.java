package de.tekup.studentsabsence.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;
    public void sendmail(String destinataire ,String mat)throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("mokhtarabbes9@gmail.com");
        mimeMessageHelper.setTo(destinataire);
        mimeMessageHelper.setText("Bonjour, chers étudiants, " +
                "\n nous vous informerons que vous étes éliminée dans la matière "+mat);
        mimeMessageHelper.setSubject("Elimination");
        javaMailSender.send(mimeMessage);
        System.out.println("email envoyée");

    }
}
