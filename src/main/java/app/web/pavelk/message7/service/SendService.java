package app.web.pavelk.message7.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Scanner;

@Data
@RequiredArgsConstructor
public class SendService {
    private final ReceivedService receivedService;
    String chatId = "";

    public void send() throws TelegramApiException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            if (s.equals("exit")) {
                break;
            } else if (s.startsWith("s")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(s);
                receivedService.execute(sendMessage);
            }
        }
    }

}
