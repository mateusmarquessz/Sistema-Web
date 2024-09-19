package com.smn.pastelaria.sistema_web.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendTaskNotification(String to, String taskMessage) {
        String subject = "Nova Tarefa Atribuída";
        String text = "Você tem uma nova tarefa: " + taskMessage;

        sendSimpleMessage(to, subject, text);
    }

    public void sendTaskCompletionNotification(String to, String taskMessage) {
        String subject = "Tarefa Concluída";
        String text = "A tarefa foi concluída: " + taskMessage;

        sendSimpleMessage(to, subject, text);
    }
}
