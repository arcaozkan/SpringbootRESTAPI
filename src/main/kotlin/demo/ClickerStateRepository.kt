// ClickerStateRepository.kt
package demo

import org.springframework.data.jpa.repository.JpaRepository

interface ClickerStateRepository : JpaRepository<ClickerState, Long>
