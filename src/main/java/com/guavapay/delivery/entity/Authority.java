package com.guavapay.delivery.entity;

import com.guavapay.delivery.entity.enums.AuthorityName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authority")
@Getter
@Setter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private AuthorityName name;

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;
}
