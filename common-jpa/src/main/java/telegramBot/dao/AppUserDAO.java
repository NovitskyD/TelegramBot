package telegramBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import telegramBot.entity.AppUser;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByTelegramUserid(Long id);
}
