package data.scripts.campaign.vayra_rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CoreInteractionListener;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import java.util.List;
import java.util.Map;


public class vayra_k8 extends BaseCommandPlugin {
	public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
		if (dialog == null) return false;
                //BaseSalvageSpecial.clearExtraSalvage(dialog.getInteractionTarget());
                //BaseSalvageSpecial.clearExtraSalvage(memoryMap);
                CargoAPI extraSalvage = Global.getFactory().createCargo(true);
                if (Global.getSector().getSeedString().hashCode() % 2 > 0) {
                    extraSalvage.addSpecial(new SpecialItemData("ship_bp", "vayra_mendicant_qamar"), 1);
                    extraSalvage.addSpecial(new SpecialItemData("ship_bp", "vayra_prophet_qamar"), 1);
                } else {
                    extraSalvage.addSpecial(new SpecialItemData("ship_bp", "vayra_hyena_qamar"), 1);
                    extraSalvage.addSpecial(new SpecialItemData("ship_bp", "vayra_camel_qamar"), 1);
                    extraSalvage.addSpecial(new SpecialItemData("ship_bp", "vayra_falchion_qamar"), 1);
                    extraSalvage.addSpecial(new SpecialItemData("ship_bp", "vayra_archimandrite_qamar"), 1);
                    extraSalvage.addSpecial(new SpecialItemData("ship_bp", "vayra_falcon_qamar"), 1);
                }
                    dialog.getVisualPanel().showLoot("Salvaged", extraSalvage, false, true, true, new CoreInteractionListener() {
                            public void coreUIDismissed() {
                            }
                    });
                return true;
	}
        
}

