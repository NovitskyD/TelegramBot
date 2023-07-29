package telegramBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import telegramBot.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByTelegramUserid();
}
