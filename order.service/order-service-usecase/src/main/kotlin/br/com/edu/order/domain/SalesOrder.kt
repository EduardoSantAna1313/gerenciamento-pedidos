package br.com.edu.order.domain

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "sales_order")
data class SalesOrder(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID = UUID.randomUUID(),

    @Column(name = "num_order")
    var numOrder: Long = 0,

    @Column(name = "num_item")
    var numItem: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status = Status.PENDING,

    @Column(name = "created")
    var created: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by")
    var createdBy: String? = null,

    @Column(name = "updated")
    var updated: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    var updatedBy: String? = null,

    @Column(name = "val_total")
    var valTotal: BigDecimal = BigDecimal.ZERO,

    @Column(name = "product_id")
    var productId: String = "",

    @Column(name = "price")
    var price: BigDecimal = BigDecimal.ZERO,

    @Column(name = "quantity")
    var quantity: Int = 0,

    @Column(name = "val_base_calculo")
    var valBaseCalculo: BigDecimal? = null,

    @Column(name = "val_icsm")
    var valIcsm: BigDecimal? = null,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "json_tabela_cheia", columnDefinition = "json")
    var jsonTabelaCheia: String? = null,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "json_tabela_praticada", columnDefinition = "json")
    var jsonTabelaPraticada: String? = null,

    @Column(name = "active")
    var active: Boolean = true
)