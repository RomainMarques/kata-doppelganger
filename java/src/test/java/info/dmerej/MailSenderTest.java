package info.dmerej;

import info.dmerej.mailprovider.SendMailRequest;
import info.dmerej.mailprovider.SendMailResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MailSenderTest {
    private User user;
    private String message = "message";
    private static final PrintStream standardOut = System.out;
    private static final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    void start() {
        user = new User("name", "email");
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @Test
    void should_make_a_valid_http_request() {
        // TODO: write a test to demonstrate the bug in MailSender.sendV1()
        HttpClient httpClient = (url, request) ->  {
            SendMailRequest sendMailRequest = (SendMailRequest) request;
            System.out.println(sendMailRequest.toString());
            return new SendMailResponse(3, sendMailRequest.toString());
        };
        MailSender mailSender = new MailSender(httpClient);

        mailSender.sendV1(user, message);

        Assertions.assertEquals("SendMailRequest{recipient='email', subject='New notification', body='message'}",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void should_retry_when_getting_a_503_error() {
        // TODO: write a test to demonstrate the bug in MailSender.sendV2()
        HttpClient httpClient = (url, request) ->  {
            SendMailRequest sendMailRequest = (SendMailRequest) request;
            System.out.println(sendMailRequest.toString());
            return new SendMailResponse(503, sendMailRequest.toString());
        };
        MailSender mailSender = new MailSender(httpClient);

        mailSender.sendV2(user, message);

        Assertions.assertEquals("SendMailRequest{recipient='email', subject='New notification', body='message'}\n" +
                        "SendMailRequest{recipient='email', subject='New notification', body='message'}",
                outputStreamCaptor.toString().trim());
    }
}
