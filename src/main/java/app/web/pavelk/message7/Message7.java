package app.web.pavelk.message7;

import app.web.pavelk.message7.service.ReceivedService;
import app.web.pavelk.message7.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
public class Message7 {
    public static void main(String[] args) throws IOException {

        List<String> strings = Files.readAllLines(Path.of("setting.txt"), StandardCharsets.UTF_8);

        ReceivedService receivedService = new ReceivedService();
        receivedService.setUsername(strings.get(0));
        receivedService.setToken(strings.get(1));

        CompletableFuture.runAsync(() -> {
            try {
                SendService sendService = new SendService(receivedService);
                sendService.setChatId(strings.get(2));
                sendService.send();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }, Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }));

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(receivedService);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } finally {
            log.info("finally");
        }
    }
}
