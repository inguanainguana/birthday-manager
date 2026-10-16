package ru.komogorova.birthday_manager.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "birthday")
public class Birthday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    // 0 - приятель, 1 - друг, 2 - коллега, 3 - родственник, 4 - другое
    @Column(nullable = false)
    private int relationship = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "birthday", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gift> gifts;
}
