package mezz.modnametooltip;

import java.util.function.Consumer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ModNameTooltip.MOD_ID)
public class ModNameTooltip {
	public static final String MOD_ID = "modnametooltip";

	public ModNameTooltip() {
		DistExecutor.runWhenOn(Dist.CLIENT, ()->()-> {
			Config config = new Config();
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, config.getConfigSpec());
			TooltipEventHandler tooltipEventHandler = new TooltipEventHandler(config);

			IEventBus eventBus = MinecraftForge.EVENT_BUS;
			addListener(eventBus, ModConfig.Reloading.class, EventPriority.NORMAL, config::onConfigChanged);
			addListener(eventBus, ItemTooltipEvent.class, EventPriority.LOW, tooltipEventHandler::onToolTip);
		});
	}

	private static <T extends Event> void addListener(IEventBus eventBus, Class<T> eventType, EventPriority priority, Consumer<T> listener) {
		eventBus.addListener(priority, false, eventType, listener);
	}
}
