package cc.trixey.mc.trmenu.test.unit

import cc.trixey.mc.trmenu.invero.impl.element.BasicItem
import cc.trixey.mc.trmenu.invero.impl.panel.IOStoragePanel
import cc.trixey.mc.trmenu.invero.impl.window.ContainerWindow
import cc.trixey.mc.trmenu.invero.util.addPanel
import cc.trixey.mc.trmenu.invero.util.buildItem
import cc.trixey.mc.trmenu.invero.util.buildWindow
import cc.trixey.mc.trmenu.test.generateRandomItem
import org.bukkit.Material
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand

/**
 * TrMenu
 * cc.trixey.mc.trmenu.test.unit.UnitIO
 *
 * @author Score2
 * @since 2022/11/15 21:11
 */
object UnitIO {

    val storage = subCommand {
        execute<Player> { player, _, _ ->
            buildWindow<ContainerWindow>(player) {
                title = "Storage Panels Test"

                val pane = randomPane()

                addPanel<IOStoragePanel>(7 to 4, 10) {
                    setElement(0..4, buildItem<BasicItem>(generateRandomItem {
                        name = "§bA random Item"
                        lore.add("§3You can move freely in this panel.")
                    }))
                }
                // ContainerWindow 不应该包含干涉玩家容器的 Panel
                // TODO 通过 ContainerWindow 提供锁定/允许玩家容器交互的方法
//                // 使玩家背包可以移动物品
//                addPanel<IOStoragePanel>(9 to 3, 54)
//                arrayOf(0, 45).forEach { pos ->
//                    addPanel<StandardPanel>(8 to 1, pos) {
//                        val paneItem = buildItem<BasicItem>(pane)
//
//                        slotsUnoccupied.forEach { slot ->
//                            setElement(slot, paneItem)
//                        }
//                    }
//                }

            }.also { it.open() }

        }
    }

    fun randomPane(): Material {
        return Material.values().filter { it.name.endsWith("_stained_glass_pane", ignoreCase = true) }.random()
    }
    /*    @SubscribeEvent
        fun e(e: InventoryDragEvent) {
            println("================================> InventoryDragEvent")
            println("          type: " + e.type)
            println("        cursor: " + e.cursor.dump())
            println("     oldCursor: " + e.oldCursor.dump())
            println("      newItems: " + e.newItems.toList().map { "{${it.first}, ${it.second.dump()}}, " })
            println("      rawSlots: " + e.rawSlots)
            println("inventorySlots: " + e.inventorySlots)

        }

        @SubscribeEvent
        fun e(e: InventoryClickEvent) {
            println("================================> InventoryClickEvent")
            println("      action: " + e.action)
            println("       click: " + e.click)
            println(" currentItem: " + e.currentItem.dump())
            println("      cursor: " + e.cursor.dump())
            println("hotbarButton: " + e.hotbarButton)
            println("     rawSlot: " + e.rawSlot)
            println("        slot: " + e.slot)
            println("    slotType: " + e.slotType)
        }

        fun ItemStack?.dump() = "${this?.type}-${this?.amount}"
        */

}