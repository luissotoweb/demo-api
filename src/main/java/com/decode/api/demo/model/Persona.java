package com.decode.api.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "personas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    private Integer edad;

    private String email;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
