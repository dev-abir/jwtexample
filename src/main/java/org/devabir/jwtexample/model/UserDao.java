package org.devabir.jwtexample.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "user_")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {

    @Id
    private String email;

    @Column(nullable = false, name = "hashed_password")
    private String hashedPassword;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt = new Date();

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}
