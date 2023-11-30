package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.events.UserTopClientEvent;
import com.example.pastry.shop.model.entity.PromoCodes;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.PromoCodesRepository;
import com.example.pastry.shop.repository.UsersRepository;
import com.example.pastry.shop.service.UserTopClientService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserTopClientServiceImpl implements UserTopClientService {

    private final JavaMailSender javaMailSender;
    private final UsersRepository usersRepository;

    private final PromoCodesRepository promoCodesRepository;

    public UserTopClientServiceImpl(JavaMailSender javaMailSender, UsersRepository usersRepository, PromoCodesRepository promoCodesRepository) {
        this.javaMailSender = javaMailSender;
        this.usersRepository = usersRepository;
        this.promoCodesRepository = promoCodesRepository;
    }

    @Override
    @EventListener(UserTopClientEvent.class)
    public void UserTopClient(UserTopClientEvent event) throws MessagingException, UnsupportedEncodingException {
        Optional<Users> user = this.usersRepository.findByEmail(event.getUserEmail());
        String promoCode = createPromoCode(user);
        String subject = "Top buy";
        String senderName = "Pastry Shop Team";
        String mailContent = "<h4>Dear " + user.get().getFirstName()
                + " " + user.get().getLastName() + ",</h4>";
        mailContent += "<p>You purchased products for over 100 BGN</p>";
        mailContent += "<h3>You get an additional 10 percent off your next order</h3>";
        mailContent += "<h3>Your promo code: </h3>" + promoCode;
        mailContent += "<h4>We are expecting you!</h4>";
        mailContent += "<p>Mom's sweet shop team<p/>";
        sendMail(user, subject, senderName, mailContent);
    }

    private String createPromoCode(Optional<Users> user) {
        String code = user.get().getUsername() + "#" + RandomString.make(12);
        PromoCodes promoCodes = new PromoCodes();
        promoCodes.setPromoCode(code);
        promoCodes.setUser(user.get());
        this.promoCodesRepository.save(promoCodes);
        return code;
    }

    private void sendMail(Optional<Users> user, String subject, String senderName,
                          String mailContent) throws MessagingException, UnsupportedEncodingException {
        sendMail(user, subject, senderName, mailContent, javaMailSender);
    }

    public static void sendMail(Optional<Users> user, String subject, String senderName,
                                String mailContent, JavaMailSender javaMailSender)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ivailoali@gmail.com", senderName);
        helper.setTo(user.get().getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
