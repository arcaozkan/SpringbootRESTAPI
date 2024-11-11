// MessageController.kt
package demo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.annotation.CacheEvict


@EnableCaching
@RestController
class MessageController(private val clickerStateService: ClickerStateService) {
    private var dynamicRate: Long = 10000
    private var lastRunTime: Long = System.currentTimeMillis()
    // Endpoint to increment the stone counter
    @PostMapping("/stoneclick")
    fun stoneclick(): String {
        val state = clickerStateService.getState()
        state.stoneCounter += state.stoneMultiplier
        clickerStateService.updateState(state)
        return "${state.stoneCounter} stones"
    }

    // Endpoint to return the current stone count
    @GetMapping("/stoneclick")
    fun returnStoneclick(): String {
        val state = clickerStateService.getState()
        return "${state.stoneCounter} stones"
    }

    // Endpoint to increment the wood counter
    @PostMapping("/woodclick")
    fun woodclick(): String {
        val state = clickerStateService.getState()
        state.woodCounter += state.woodMultiplier
        clickerStateService.updateState(state)
        return "${state.woodCounter} wood"
    }

    // Endpoint to return the current wood count
    @GetMapping("/woodclick")
    fun returnWoodclick(): String {
        val state = clickerStateService.getState()
        return "${state.woodCounter} wood"
    }

    // Endpoint to increment the wood counter
    @CacheEvict(value = ["wheatCache"], key = "'wheatCounter'")
    @PostMapping("/wheatclick")
    fun wheatclick(): String {
        val state = clickerStateService.getState()
        state.wheatCounter += state.wheatMultiplier
        clickerStateService.updateState(state)
        return "${state.wheatCounter} wheat"
    }

    @Cacheable(value = ["wheatCache"], key = "'wheatCounter'")
    @GetMapping("/wheatclick")
    fun returnWheatclick(): String {
        val state = clickerStateService.getState()
        val wheatCounterValue = state.wheatCounter // Just the number
        println("Caching wheat counter: $wheatCounterValue") // Debug output
        return "$wheatCounterValue wheat" // Format for the response
    }

    // Endpoint to return the current wood count
    @GetMapping("/popupdate")
    fun returnPopulation(): String {
        val state = clickerStateService.getState()
        return "${state.population} people are living in your town."
    }

    // Endpoint to increase the stone multiplier
    @PostMapping("/stonemulti")
    fun stonemulti(): String {
        val state = clickerStateService.getState()
        if (state.stoneCounter >= 50*(state.stoneMultiplier) && state.woodCounter >= 10*(state.stoneMultiplier) &&
            state.wheatCounter >= 20*(state.stoneMultiplier)) {

            state.stoneCounter -= 50 * state.stoneMultiplier
            state.woodCounter -= 10 * state.stoneMultiplier
            state.wheatCounter -= 20 * state.stoneMultiplier
            state.stoneMultiplier += 1
            clickerStateService.updateState(state)
            return "You can now mine ${state.stoneMultiplier} Stone at a time."
        }
        return "Not enough materials!"
    }

    // Endpoint to increase the wood multiplier
    @PostMapping("/woodmulti")
    fun woodmulti(): String {
        val state = clickerStateService.getState()
        if (state.stoneCounter >= 50*(state.woodMultiplier) && state.woodCounter >= 10*(state.woodMultiplier) &&
            state.wheatCounter >= 20*(state.woodMultiplier)) {

            state.stoneCounter -= 50 * state.woodMultiplier
            state.woodCounter -= 10 * state.woodMultiplier
            state.wheatCounter -= 20 * state.woodMultiplier
            state.woodMultiplier += 1
            clickerStateService.updateState(state)
            return "You can now chop ${state.woodMultiplier} Wood at a time."
        }
        return "Not enough materials!"
    }

    // Endpoint to increase the wood multiplier
    @PostMapping("/wheatmulti")
    fun wheatmulti(): String {
        val state = clickerStateService.getState()
        if (state.stoneCounter >= 50*(state.wheatMultiplier) && state.woodCounter >= 10*(state.wheatMultiplier) &&
            state.wheatCounter >= 20*(state.wheatMultiplier)) {

            state.stoneCounter -= 50*state.wheatMultiplier
            state.woodCounter -= 10*state.wheatMultiplier
            state.wheatCounter -= 20*state.wheatMultiplier
            state.wheatMultiplier += 1
            clickerStateService.updateState(state)
            return "You can now gather ${state.wheatMultiplier} Wheat at a time."
        }
        return "Not enough materials!"
    }

    // Endpoint to increase the wood multiplier
    @PostMapping("/speedupg")
    fun speedupg(): String {

        val state = clickerStateService.getState()
        if (state.stoneCounter >= 10*(state.efficiency) && state.woodCounter >= 10*(state.efficiency) &&
            state.wheatCounter >= 50*(state.efficiency)) {
            if (state.efficiency < 100) {

                state.stoneCounter -= 10*state.efficiency
                state.woodCounter -= 10*state.efficiency
                state.wheatCounter -= 50*state.efficiency
                dynamicRate = 10000 / state.efficiency.toLong()
                state.efficiency += 1
                clickerStateService.updateState(state)
                return "You can now gather resources faster. Level: ${state.efficiency}"
            } else {
                return "Gathering Efficiency is maxed. Level: ${state.efficiency}"
            }
        }
        return "Not enough materials!"
    }

    // Endpoint to upgrade the stone mine if enough resources are available
    @PostMapping("/stonemine")
    fun stonemine(): String {
        val state = clickerStateService.getState()
        if (state.stoneCounter >= 10*(state.stoneMines+1) && state.woodCounter >= 50*(state.stoneMines+1) &&
            state.wheatCounter >= 20*(state.stoneMines+1)) {

            state.stoneMines += 1
            state.stoneCounter -= 10*state.stoneMines
            state.woodCounter -= 50*state.stoneMines
            state.wheatCounter -= 20*state.stoneMines
            clickerStateService.updateState(state)
            return "You built a Stone Mine."
        }
        return "Not enough materials!"
    }

    // Endpoint to upgrade the stone mine if enough resources are available
    @PostMapping("/woodcamp")
    fun woodcamp(): String {
        val state = clickerStateService.getState()
        if (state.stoneCounter >= 10*(state.woodCamps+1) && state.woodCounter >= 50*(state.woodCamps+1) &&
            state.wheatCounter >= 20*(state.woodCamps+1)) {

            state.woodCamps+= 1
            state.stoneCounter -= 10*state.woodCamps
            state.woodCounter -= 50*state.woodCamps
            state.wheatCounter -= 20*state.woodCamps

            clickerStateService.updateState(state)
            return "You built a Wood Camp."
        }
        return "Not enough materials!"
    }

    // Endpoint to upgrade the stone mine if enough resources are available
    @PostMapping("/wheatfield")
    fun wheatfield(): String {
        val state = clickerStateService.getState()
        if (state.stoneCounter >= 10 * (state.wheatFields+1) && state.woodCounter >= 50*(state.wheatFields+1) &&
            state.wheatCounter >= 20*(state.wheatFields+1)) {

            state.wheatFields += 1
            state.stoneCounter -= 10*state.wheatFields
            state.woodCounter -= 50*state.wheatFields
            state.wheatCounter -= 20*state.wheatFields

            clickerStateService.updateState(state)
            return "You built a Wheat Field."
        }
        return "Not enough materials!"
    }



    // Scheduled task to increase the stone count automatically every second
    @CacheEvict(value = ["wheatCache"], key = "'wheatCounter'")
    @Scheduled(fixedRate = 100)
    fun autoIncrement() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRunTime >= dynamicRate) {

            val state = clickerStateService.getState()
            if (state.stoneMines > 0) {
                state.stoneCounter += (state.stoneMultiplier * state.stoneMines)

            }
            if (state.woodCamps > 0) {
                state.woodCounter += (state.woodMultiplier * state.woodCamps)

            }
            if (state.wheatFields > 0) {
                state.wheatCounter += (state.wheatMultiplier * state.wheatFields)

            }
            state.population+=(state.stoneMines+state.woodCamps+state.wheatFields)*(state.woodMultiplier+state.stoneMultiplier+state.wheatMultiplier)
            clickerStateService.updateState(state)
            lastRunTime = currentTime  // Reset last run time
        }
    }
}
