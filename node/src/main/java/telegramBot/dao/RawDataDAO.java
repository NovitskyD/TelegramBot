package telegramBot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import telegramBot.entity.RawData;

@Repository
public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
