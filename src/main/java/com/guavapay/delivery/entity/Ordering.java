package com.guavapay.delivery.entity;

import com.guavapay.delivery.entity.enums.OrderingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordering")
@Getter
@Setter
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "destination")
    private String destination;

    @Column(name = "order_date")
    private Instant orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderingStatus orderStatus;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserData user;

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
}
