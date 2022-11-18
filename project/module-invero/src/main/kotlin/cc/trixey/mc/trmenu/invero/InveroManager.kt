package cc.trixey.mc.trmenu.invero

import cc.trixey.mc.trmenu.invero.impl.WindowHolder
import cc.trixey.mc.trmenu.invero.impl.element.BasicDynamicItem
import cc.trixey.mc.trmenu.invero.impl.element.BasicItem
import cc.trixey.mc.trmenu.invero.impl.panel.*
import cc.trixey.mc.trmenu.invero.impl.window.CompleteWindow
import cc.trixey.mc.trmenu.invero.impl.window.ContainerWindow
import cc.trixey.mc.trmenu.invero.module.Panel
import cc.trixey.mc.trmenu.invero.module.TypeAddress
import cc.trixey.mc.trmenu.invero.module.Window
import cc.trixey.mc.trmenu.invero.module.element.PanelElement
import cc.trixey.mc.trmenu.invero.module.PanelWeight
import org.bukkit.entity.Player
import org.bukkit.event.inventory.*
import taboolib.common.platform.event.SubscribeEvent

/**
 * @author Arasple
 * @since 2022/10/28 19:22
 *
 * TODO List
 *
 * - #[?] ItemElement 请求更新逻辑
 * - #[√] 周期性帧 ItemElement
 * - #[√] DynamicElement 动态槽位
 * ^ 至此已实现支持 TrMenu v3 的功能框架
 *
 * [ ] Paged(Generator)?Panel 支持静态元素，方便设置导航按钮
 * [ ] 支持虚拟容器发包实现的 PacketWindow（特殊场景如不使用 IOPanel的UI可用）
 */
object InveroManager {

    fun Panel.constructElement(type: Class<*>): PanelElement {
        return when (type) {
            BasicItem::class.java -> BasicItem(null, this)
            BasicDynamicItem::class.java -> BasicDynamicItem(null, this)
            else -> throw IllegalArgumentException("Failed to create element, not found registered class $type")
        }
    }

    /**
     * TODO 需要改进开放性注册
     */
    fun constructPanel(type: Class<*>, scale: Pair<Int, Int>, posMark: Int, weight: PanelWeight): Panel {
        return when (type) {
            StandardPanel::class.java -> StandardPanel(scale, posMark, weight)
            PagedStandardPanel::class.java -> PagedStandardPanel(scale, posMark, weight)
            PagedNetesedPanel::class.java -> PagedNetesedPanel(scale, posMark, weight)
            PagedGeneratorPanel::class.java -> PagedGeneratorPanel(scale, posMark, weight)
            IOStoragePanel::class.java -> IOStoragePanel(scale, posMark, weight)
            IOCraftingPanel::class.java -> IOCraftingPanel(scale, posMark, weight)
            else -> throw IllegalArgumentException("Failed to create panel, not found registered class $type")
        }
    }

    /**
     * TODO 需要改进开放性注册
     */
    fun constructWindow(type: Class<*>, viewer: Player, windowType: TypeAddress, title: String): Window {
        return when (type) {
            ContainerWindow::class.java -> ContainerWindow(viewer, windowType, title)
            CompleteWindow::class.java -> CompleteWindow(viewer, windowType, title)
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