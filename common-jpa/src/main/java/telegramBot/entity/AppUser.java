package telegramBot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import telegramBot.entity.enums.UserState;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramUserid;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isActive;
    @CreationTimestamp
    private LocalDateTime firstLoginDate;
    @Enumerated(EnumType.STRING)
    private UserState state;
}
