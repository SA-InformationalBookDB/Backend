package hu.bme.szarch.ibdb.task;

import hu.bme.szarch.ibdb.service.AuthorizationCodeService;
import org.springframework.scheduling.annotation.Scheduled;

public class DeleteExpiredAuthorizationCodes {

    private AuthorizationCodeService authorizationCodeService;

    public DeleteExpiredAuthorizationCodes(AuthorizationCodeService authorizationCodeService) {
        this.authorizationCodeService = authorizationCodeService;
    }

    @Scheduled(cron = "0 0/${ibdb.task.token-cleanup-frequency} * * * ?")
    public void deleteAllExpiredCodes() {
        authorizationCodeService.deleteExpiredCodes();
    }

}
