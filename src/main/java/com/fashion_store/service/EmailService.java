package com.fashion_store.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    JavaMailSender mailSender;

    public void sendEmail(String to, String otp) throws MessagingException {
        String htmlContent = EmailContent(otp);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Xác minh đăng ký - Fashion Store");
        helper.setText(htmlContent, true); // `true` để gửi dạng HTML
        helper.setFrom("kduy12354@gmail.com");
        mailSender.send(message);
    }

    private String EmailContent(String otp) {
        return String.format("""
                <div style="font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2">
                    <div style="margin:50px auto;width:70%%;padding:20px 0">
                        <div style="border-bottom:1px solid #eee">
                            <a href="" style="font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600">Fashion Store</a>
                        </div>
                        <p style="font-size:1.1em">Xin Chào,</p>
                        <p>Cảm ơn bạn đã chọn Fashion Store. Sử dụng OTP sau để hoàn tất thủ tục Đăng ký của bạn. OTP có hiệu lực trong 3 phút:</p>
                        <h2 style="background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;">%s</h2>
                        <p style="font-size:0.9em;">Trân trọng,<br />Fashion Store</p>
                    </div>
                </div>
                """, otp);
    }
}
