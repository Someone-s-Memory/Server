package example.soloproject.domain.diary.entity;

import example.soloproject.global.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> fillings = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> weathers = new ArrayList<>();

    private String content;

    private String picture;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
