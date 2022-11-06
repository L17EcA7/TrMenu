package cc.trixey.mc.trmenu.invero

import cc.trixey.mc.trmenu.invero.impl.WindowHolder
import cc.trixey.mc.trmenu.invero.impl.element.BaseItem
import cc.trixey.mc.trmenu.invero.impl.panel.BasePanel
import cc.trixey.mc.trmenu.invero.impl.window.CompleteWindow
import cc.trixey.mc.trmenu.invero.impl.window.ContainerWindow
import cc.trixey.mc.trmenu.invero.module.Panel
import cc.trixey.mc.trmenu.invero.module.TypeAddress
import cc.trixey.mc.trmenu.invero.module.Window
import org.bukkit.entity.Player
import org.bukkit.event.inventory.*
import taboolib.common.platform.event.SubscribeEvent

/**
 * @author Arasple
 * @since 2022/10/28 19:22
 */
object InveroManager {

    fun Panel.constructElement(type: Class<*>): BaseItem {
        return when (type) {
            BaseItem::class.java -> BaseItem(null, this)
            else -> throw IllegalArgumentException("Failed to create element, not found registered class $type")
        }
    }

    fun constructPanel(type: Class<*>, size: Pair<Int, Int>, posMark: Int): Panel {
        return when (type) {
            BasePanel::class.java -> BasePanel(size, posMark)
            else -> throw IllegalArgumentException("Failed to create panel, not found registered class $type")
        }
    }

    fun constructWindow(type: Class<*>, viewer: Player, title: String, windowType: TypeAddress): Window {
        return when (type) {
            ContainerWindow::class.java -> ContainerWindow(viewer, title, windowType)
            CompleteWindow::class.java -> CompleteWindow(viewer, title, windowType)
            else -> throw IllegalArgumentException("Failed to create window, not found registered class $type")
        }
    }

    @SubscribeEvent
    fun e(e: InventoryClickEvent) = e.passInventoryEvent()

    @SubscribeEvent
    fun e(e: InventoryDragEvent) = e.passInventoryEvent()

    @SubscribeEvent
    fun e(e: InventoryOpenEvent) = e.passInventoryEvent()

    @SubscribeEvent
    fun e(e: InventoryCloseEvent) = e.passInventoryEvent()

    private fun InventoryEvent.passInventoryEvent() = inventory.holder.let {
        if (it is WindowHolder) {
            it.window.handleEvent(this)
        }
    }

}