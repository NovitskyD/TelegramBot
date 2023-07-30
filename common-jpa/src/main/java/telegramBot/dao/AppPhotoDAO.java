package telegramBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import telegramBot.entity.AppPhoto;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}
