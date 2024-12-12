package com.ing.demo.store_management.model.log;

import com.ing.demo.store_management.model.authentication.StoreUser;
import com.ing.demo.store_management.model.product.base.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private StoreUser user;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Product product;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationType operation; // Values: "Create", "Update", "Delete"

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    public Log(StoreUser user, Product product, OperationType operation) {
        this.user = user;
        this.product = product;
        this.operation = operation;
        this.timestamp = LocalDateTime.now();
    }
}
