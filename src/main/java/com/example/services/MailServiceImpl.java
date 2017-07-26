package com.example.services;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.models.User;

@Service
public class MailServiceImpl implements MailService{
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	private static final String RESET_PASSWORD = "reset_password";
	private static final String ACTIVATION_ACCOUNT = "activation_account";
	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendResetPasswordEmail(String email, User user, String url, String token) {
		MimeMessagePreparator prerator = getMessagePrerator(email, user, url, token, RESET_PASSWORD);
		try {
			mailSender.send(prerator);
		} catch (MailException me) {
			logger.info("Mail exception: "+ me.getMessage());
		}
	}

	@Override
	public void sendActivationAccountEmail(String email, User user, String url, String token) {
		MimeMessagePreparator prerator = getMessagePrerator(email, user, url, token, ACTIVATION_ACCOUNT);
		try {
			mailSender.send(prerator);
		} catch (MailException me) {
			logger.info("Mail exception: "+ me.getMessage());
		}
	}

	private MimeMessagePreparator getMessagePrerator(final String email, User user, String url, String token, String type){
		MimeMessagePreparator prerator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom("caingocduong2606@gmail.com");
				helper.setTo(new InternetAddress(email));
				if(type.equals(RESET_PASSWORD)){
					helper.setSubject("Reset password");
					helper.setText(constructBodyResetPasswordEmail(user, email, url, token),true);
				} else {
					helper.setSubject("Confirm registering");
					helper.setText(constructBodyActivationAccountEmail(user, email, url, token),true);
				}
				mailSender.send(mimeMessage);
			}
		};

		return prerator;
	}

	private String constructBodyResetPasswordEmail(User user, String email,String url, String token){
		String url_temp = "<a href=\"" + url  + "/user/changePassword/" + user.getId()
							+ "/" + token + "\">" + "Reset password" + "</a>";
		String message = "You have required to reset password. Click the link below to reset your password:\n ";

		return message + url_temp;
	}

	private String constructBodyActivationAccountEmail(User user, String email,String url, String token){
		String url_temp = "<a href=\"" + url  + "/activationSuccessful/" + user.getId()
							+ "/" + token + "\">" + "Confirm regitering" + "</a>";
		String message = "Thank you for registing our website. Please click the link to active account:\n ";

		return message + url_temp;
	}

}
