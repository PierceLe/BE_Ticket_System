package com.scaffold.ticketSystem.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invalidated_tokens")
@Entity
public class InvalidatedToken {
    @Id
    String id;

    Date expiryTime;
}
