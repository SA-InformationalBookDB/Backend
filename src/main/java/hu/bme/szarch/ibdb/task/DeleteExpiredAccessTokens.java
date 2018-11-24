package hu.bme.szarch.ibdb.task;

import hu.bme.szarch.ibdb.service.TokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredAccessTokens {

    private TokenService tokenService;

    public DeleteExpiredAccessTokens(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Scheduled(cron = "0 0/${ibdb.task.token-cleanup-frequency} * * * *")
    public void deleteAllExpiredTokens() {
        tokenService.deleteExpiredTokens();
    }

}
