package com.willfp.reforges.reforges

import com.google.common.collect.HashBiMap
import com.google.common.collect.ImmutableSet
import com.willfp.eco.core.config.updating.ConfigUpdater
import com.willfp.libreforge.chains.EffectChains.compile
import com.willfp.reforges.ReforgesPlugin

@Suppress("UNUSED")
object Reforges {
    private val BY_KEY = HashBiMap.create<String, Reforge>()

    /**
     * Get all registered [Reforge]s.
     *
     * @return A list of all [Reforge]s.
     */
    @JvmStatic
    fun values(): Set<Reforge> {
        return ImmutableSet.copyOf(BY_KEY.values)
    }

    /**
     * Get [String]s for all registered [Reforge]s.
     *
     * @return A list of all [Reforge]s.
     */
    @JvmStatic
    fun keySet(): Set<String> {
        return ImmutableSet.copyOf(BY_KEY.keys)
    }

    /**
     * Get [Reforge] matching key.
     *
     * @param key The key to search for.
     * @return The matching [Reforge], or null if not found.
     */
    @JvmStatic
    fun getByKey(key: String?): Reforge? {
        return if (key == null) {
            null
        } else BY_KEY[key]
    }

    /**
     * Update all [Reforge]s.
     *
     * @param plugin Instance of Reforges.
     */
    @ConfigUpdater
    @JvmStatic
    fun update(plugin: ReforgesPlugin) {
        for (config in plugin.reforgesYml.getSubsections("chains")) {
            compile(config!!, "Chains")
        }
        for (reforge in values()) {
            removeReforge(reforge)
        }
        for (config in plugin.reforgesYml.getSubsections("reforges")) {
            Reforge(config!!, plugin)
        }
    }

    /**
     * Remove [Reforge] from Reforges.
     *
     * @param reforge The [Reforge] to remove.
     */
    @JvmStatic
    fun removeReforge(reforge: Reforge) {
        BY_KEY.remove(reforge.id)
    }

    /**
     * Add new [Reforge] to Reforges.
     *
     * Only for internal use, reforges are automatically added in the constructor.
     *
     * @param reforge The [Reforge] to add.
     */
    internal fun addNewReforge(reforge: Reforge) {
        BY_KEY.remove(reforge.id)
        BY_KEY[reforge.id] = reforge
    }
}
