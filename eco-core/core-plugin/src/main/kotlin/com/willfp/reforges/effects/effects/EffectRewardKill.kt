package com.willfp.reforges.effects.effects

import com.willfp.eco.core.config.interfaces.JSONConfig
import com.willfp.eco.core.events.EntityDeathByEntityEvent
import com.willfp.reforges.effects.Effect
import com.willfp.reforges.vault.EconomyHandler
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

class EffectRewardKill : Effect("reward_kill") {
    override fun onKill(
        killer: LivingEntity,
        victim: LivingEntity,
        event: EntityDeathByEntityEvent,
        config: JSONConfig
    ) {
        if (killer !is Player) {
            return
        }

        EconomyHandler.getInstance().depositPlayer(killer, config.getDouble("amount"))
    }
}