import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;

public class FileAttachmentExample {

    // Small 96x64 PNG image embedded directly; no local image file is required.
    private static final String IMAGE_BASE64 =
            "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";

    public static void main(String[] args) throws IOException {
        attachment();
    }

    public static void attachment() throws IOException {
        String apiKey = System.getenv("SENDGRID_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Set the SENDGRID_API_KEY environment variable.");
        }

        String fromAddress = "sender@example.com";
        String toAddress = "recipient@example.com";

        Mail mail = new Mail(
                new Email(fromAddress),
                "SendGrid Java: File attachment example",
                new Email(toAddress),
                new Content("text/plain",
                        "Please find the attached image."));

        Attachments attachment = new Attachments();
        attachment.setContent(IMAGE_BASE64);
        attachment.setType("image/png");
        attachment.setFilename("mail.png");
        attachment.setDisposition("attachment");
        mail.addAttachments(attachment);

        sendEmail(apiKey, mail);
    }

    private static void sendEmail(String apiKey, Mail mail)
            throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = new SendGrid(apiKey).api(request);
        System.out.println("HTTP status: " + response.getStatusCode());

        if (response.getStatusCode() < 200
                || response.getStatusCode() >= 300) {
            System.err.println(response.getBody());
            throw new IllegalStateException("Email sending failed.");
        }
    }
}
