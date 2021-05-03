package data.addstablelocation;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.Entities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator.AddedEntity;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator.EntityLocation;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator.LocationType;
import com.fs.starfarer.api.util.WeightedRandomPicker;

import org.json.JSONObject;

import java.util.*;


public class Utilities {
    @SuppressWarnings("unchecked")
    public static Map<String, Number> getCost(String type){
        Map<String, Number> costs = new HashMap<>();
        try {
            JSONObject consumed = Global.getSettings().getJSONObject("addstablelocation").getJSONObject(type);
            Iterator<String> keys = consumed.keys();
            while(keys.hasNext()){
                String key = keys.next();
                costs.put(key, (float)consumed.getDouble(key));
            }
        } catch (Exception e) {}
        return costs;
    }

    public static void setCostPanel(InteractionDialogAPI dialog, Object[] displays){
        // Due to display limit, the display will cut each 3 display items(9 length array)
        int i = 0;
        while(i < displays.length){
            if( i + 9 > displays.length){
                dialog.getTextPanel().addCostPanel("", Arrays.copyOfRange(displays, i, displays.length));
            }
            else{
                dialog.getTextPanel().addCostPanel("", Arrays.copyOfRange(displays, i, i + 9));
            }
            i += 9;
        }
    }
    public static boolean costAvailable(String type){
        if(Global.getSettings().isDevMode()) return true;
        for(Map.Entry<String, Number> cost : Utilities.getCost(type).entrySet()){
            CargoAPI cargo = Global.getSector().getPlayerFleet().getCargo();
            if (cargo.getCommodityQuantity(cost.getKey()) < (float)cost.getValue()) return false;
        }
        return true;
    }
    public static void addStableLocationNearStar(StarSystemAPI system, int num){
		for (int i = 0; i < num; i++) {
			LinkedHashMap<LocationType, Float> weights = new LinkedHashMap<LocationType, Float>();
			weights.put(LocationType.STAR_ORBIT, 10f);
			WeightedRandomPicker<EntityLocation> locs = BaseThemeGenerator.getLocations(new Random(), system, null, 100f, weights);
			EntityLocation loc = locs.pick();
			AddedEntity added = BaseThemeGenerator.addNonSalvageEntity(system, loc, Entities.STABLE_LOCATION, Factions.NEUTRAL);
			if (added != null) {
				BaseThemeGenerator.convertOrbitPointingDown(added.entity);
			}
		}
    }
}
