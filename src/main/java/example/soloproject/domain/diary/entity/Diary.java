package example.soloproject.domain.diary.entity;

import example.soloproject.global.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> feelings = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> weathers = new ArrayList<>();

    @Lob
    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> pictures = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String date;

    public String getFeelings() {
        return this.feelings.stream().findFirst().orElse(null);
    }

    public String getWeathers() {
        return this.weathers.stream().findFirst().orElse(null);
    }

}
