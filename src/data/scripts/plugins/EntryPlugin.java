package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

public class EntryPlugin extends BaseModPlugin {

    public boolean isTransient() {return true;}

    @Override
    public void onGameLoad(boolean newGame) {
        Global.getSector().registerPlugin(new EntryCampaignPlugin());
    }
}
