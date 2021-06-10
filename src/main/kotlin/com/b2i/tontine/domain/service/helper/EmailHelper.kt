package com.b2i.tontine.domain.service.helper

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.service.helper.Mail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Service
class EmailHelper( private val javaMailSender: JavaMailSender,
                   @Autowired private val templateEngine : SpringTemplateEngine)
{


    fun sendEmail( to : String, username : String, mdp : String )
    {
        val msg = SimpleMailMessage()
        msg.setTo(to)
        msg.setSubject("Bienvenue sur MyTonTine / Welcome to myTontine")
        msg.setText("Votre username est : $username \n" + "Votre mot de passe est : $mdp \n" + "Connectez-vous pour profiter de nos services en ligne.")

        msg.setText("Your login is : $username \n" + "Your password is : $mdp \n" + "Log in to take advantage of our online services.")
        javaMailSender.send(msg)
    }

    fun sendHtmlEmail(to : String, user : User )
    {
        val context = Context()
        context.setVariable("user", user)
        val html:String = templateEngine.process("services/email/register-email", context)
        val msg = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(msg)
        helper.setTo(to)
        helper.setSubject("Bienvenue sur MyTonTine / Welcome to myTontine")
        helper.setText(html, true)
        /*
        msg.setText("Your login is : $username \n" + "Your password is : $mdp \n" + "Log in to take advantage of our online services.")
        */
        javaMailSender.send(msg)
    }

    fun sendEmailPasswordReset( mail : Mail)
    {

        val context = Context()
        context.setVariables(mail.model)
        val html: String = templateEngine.process("email/email-template", context)

        var msg = SimpleMailMessage()
        msg.setFrom( mail.from.toString() )
        msg.setTo( mail.to )
        msg.setSubject( mail.subject.toString() )
        msg.setText( html )
        javaMailSender.send(msg)
    }

    fun sendMsg(name : String, subject: String, mail: String, msgText : String )
    {

        val msg = SimpleMailMessage()
        msg.setTo("b2idevelop@gmail.com")
        msg.setSubject("$subject")
        msg.setText("Nom : $name \n" + "Adresse email : $mail \n" + "Message : \n" +"$msgText")
        javaMailSender.send(msg)
    }


}