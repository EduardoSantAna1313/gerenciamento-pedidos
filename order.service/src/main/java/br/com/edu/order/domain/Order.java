package br.com.edu.order.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue
    @Column( columnDefinition = "uuid", updatable = false )
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "total")
    private BigDecimal total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;

    public Order() {
        id = UUID.randomUUID();
        status = Status.PENDING;
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
        total = BigDecimal.ZERO;
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<Item> items) {
        if (items == null) {
            items = new ArrayList<>();
        }
        this.items = items;
        this.total = calculateTotal();
    }

    public BigDecimal calculateTotal() {
        if (total == null) {
            total = getItems().stream().map(Item::totalItem).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return total;
    }
}
