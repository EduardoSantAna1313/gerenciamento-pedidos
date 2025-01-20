package order.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.*
import java.util.function.Consumer

@Entity
@Table(name = "Orders")
class Order(

    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID = UUID.randomUUID(),

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

) {

    @Column(name = "total")
    var total: BigDecimal = BigDecimal.ZERO
        get() = field.setScale(2, RoundingMode.HALF_UP)


    @JoinColumn(name = "order_id")
    @OneToMany(cascade = [CascadeType.MERGE])
    var items: MutableList<Item> = mutableListOf()
        set(items) {
            field = items
            total = calculateTotal()
            items.forEach(Consumer { i: Item ->
                i.orderId = id
            })
        }

    fun addItem(item: Item) {
        item.orderId = id
        items.add(item)
        total = calculateTotal()
    }

    fun changeProcessed() {
        this.status = Status.PROCESSED
    }

    fun calculateTotal(): BigDecimal {
        return items.sumOf { it.totalItem() }
    }
}