package telegramBot.service.impl;

import org.springframework.stereotype.Service;
import telegramBot.dao.AppUserDAO;
import telegramBot.entity.AppUser;
import telegramBot.service.UserActivationService;
import telegramBot.utils.CryptoTool;

import java.util.Optional;

@Service
public class UserActivationServiceImpl implements UserActivationService {
    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;

    public UserActivationServiceImpl(AppUserDAO appUserDAO, CryptoTool cryptoTool) {
        this.appUserDAO = appUserDAO;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public boolean activation(String cryptoUserId) {
        Long userId = cryptoTool.idOf(cryptoUserId);
        var optional = appUserDAO.findAppUserById(userId);
        if(optional.isPresent()) {
            var user = optional.get();
            user.setIsActive(true);
            appUserDAO.save(user);
            return true;
        }
        return false;
    }
}
