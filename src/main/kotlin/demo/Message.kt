package demo

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Table
import jakarta.persistence.Column

@Entity
@Table(name = "clicker_state")
data class ClickerState(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var stoneCounter: Int = 0,
    var woodCounter: Int = 0,
    var stoneMultiplier: Int = 1,
    var woodMultiplier: Int = 1,


    @Column(name = "stone_mines")
    var stoneMines: Int = 0,

    @Column(name = "wood_camps")
    var woodCamps: Int = 0,

    @Column(name = "wheat_counter")
    var wheatCounter: Int = 0,

    @Column(name = "wheat_multiplier")
    var wheatMultiplier: Int = 1,

    @Column(name = "wheat_fields")
    var wheatFields: Int = 0,

    @Column(name = "efficiency")
    var efficiency: Int = 1,

    @Column(name = "population")
    var population: Int = 100,




)
