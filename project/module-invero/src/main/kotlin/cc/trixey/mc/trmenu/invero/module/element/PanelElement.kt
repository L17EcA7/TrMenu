package cc.trixey.mc.trmenu.invero.module.element

import cc.trixey.mc.trmenu.invero.impl.panel.PagedNetesedPanel
import cc.trixey.mc.trmenu.invero.impl.panel.PagedStandardPanel
import cc.trixey.mc.trmenu.invero.module.MappedElements
import cc.trixey.mc.trmenu.invero.module.Panel
import cc.trixey.mc.trmenu.invero.module.Window
import cc.trixey.mc.trmenu.invero.module.base.BasePanel
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Arasple
 * @since 2022/11/1 22:00
 */
interface PanelElement {

    /**
     * The parent panel of this element
     */
    val panel: Panel

    /**
     * Get the MappedElements of the panel to which this element belong
     */
    val panelElements: MappedElements
        get() = getPanelElementMap(panel)

    /**
     * Get the fallback MappedElements
     */
    val panelElementsFallback: MappedElements
        get() = getPanelFallbackElementMap(panel)

    private fun getPanelElementMap(panel: Panel): MappedElements {
        return when (panel) {
            is BasePanel -> panel.elementsMap
            is PagedStandardPanel -> panel.getPage()
            is PagedNetesedPanel -> getPanelElementMap(panel.getPage())
            else -> error("Unsupported panel")
        }
    }

    private fun getPanelFallbackElementMap(panel: Panel): MappedElements {
        return when (panel) {
            is PagedStandardPanel -> panel.getFallbackElements()
            else -> error("Unsupported panel")
        }
    }

    /**
     * The windows that this element is suppose to render
     */
    val windows: ArrayList<Window>
        get() {
            return panel.windows
        }

    fun Window.slotMap() = panel.getSlotsMap(this)

    fun forWindows(function: Window.() -> Unit) = panel.forWindows(function)

    fun renderElement(window: Window)

    fun renderAll() = forWindows { renderElement(this) }

}