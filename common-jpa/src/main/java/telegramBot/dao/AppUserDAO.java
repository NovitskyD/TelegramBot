package telegramBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import telegramBot.entity.UserEntity;

public interface UserEntityDAO extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByTelegramUserid(Long id);
}
