// ClickerStateService.kt
package demo

import org.springframework.stereotype.Service

@Service
class ClickerStateService(private val repository: ClickerStateRepository) {

    fun getState(): ClickerState {
        return repository.findById(1).orElseGet { repository.save(ClickerState(id = 1)) }
    }

    fun updateState(state: ClickerState): ClickerState {
        return repository.save(state)
    }
}
