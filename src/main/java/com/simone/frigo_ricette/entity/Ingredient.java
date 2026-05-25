package com.simone.frigo_ricette.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;

import java.time.LocalDateTime;

@Entity
@Table(name = "ingredients")
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    @Column(nullable = false)
    private Short quantity = 1;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
