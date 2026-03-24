package com.contacts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "contact_group")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String colorTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private Users user;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();

}
