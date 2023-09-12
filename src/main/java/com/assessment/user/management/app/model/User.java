package com.assessment.user.management.app.model;

import com.assessment.user.management.app.enums.Provider;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User extends BaseClass implements Serializable {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotBlank
    @Size(min=8,max = 100)
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

}
