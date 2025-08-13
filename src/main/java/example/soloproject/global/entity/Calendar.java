package example.soloproject.global.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private Set<LocalDate> dates;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
