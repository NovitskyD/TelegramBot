package telegramBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import telegramBot.entity.AppUser;

import java.util.Optional;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByTelegramUserid(Long id);
}
