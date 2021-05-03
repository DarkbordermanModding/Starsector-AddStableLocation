package data.scripts.plugins;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.BaseCampaignPlugin;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;

public class EntryCampaignPlugin extends BaseCampaignPlugin {

	public String getId() {return "EntryCampaignPlugin";}
	public void updatePlayerFacts(MemoryAPI memory) {}

	public PluginPick<InteractionDialogPlugin> pickInteractionDialogPlugin(SectorEntityToken interactionTarget) {
		if (interactionTarget instanceof PlanetAPI && interactionTarget.isStar()) {
			return new PluginPick<InteractionDialogPlugin>(new StableLocationPluginImpl(), PickPriority.MOD_GENERAL);
		}
		return null;
	}
}
