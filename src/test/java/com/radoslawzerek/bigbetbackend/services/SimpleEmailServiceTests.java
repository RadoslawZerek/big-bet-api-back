package com.radoslawzerek.bigbetbackend.services;

import com.radoslawzerek.bigbetbackend.pojo.Mail;
import com.radoslawzerek.bigbetbackend.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SimpleEmailServiceTests {

    @InjectMocks
    private SimpleEmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void sendBetsWonInfo() {
        //Given
        Mail mail = new Mail("napiorkowskik83@gmail.com", "Bets test e-mail", "Bets test message");

        //When
        emailService.sendBetsWonInfo(mail, "Kris");

        //Then
        verify(javaMailSender, times(1)).send((MimeMessagePreparator) any());
    }
}
