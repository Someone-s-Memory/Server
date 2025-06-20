package example.soloproject.domain.pet.entity;

import example.soloproject.global.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> types = new ArrayList<>();

    private Double level;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
