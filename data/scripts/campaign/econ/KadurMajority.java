package data.scripts.campaign.econ;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.econ.ConditionData;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import java.util.Arrays;


public class KadurMajority extends BaseMarketConditionPlugin {

	private static final String [] kadurFactions = new String [] {
		"kadur_remnant",
	};

    /**
     *
     * @param id
     */
    @Override
	public void apply(String id) {
            if (showIcon()) {
		if (Arrays.asList(kadurFactions).contains(market.getFactionId()) || "player".equals(market.getFactionId()) && Global.getSector().getFaction("kadur_remnant").getRelToPlayer().getRel() >= 0.25f) {
			market.getStability().modifyFlat(id, ConditionData.STABILITY_LUDDIC_MAJORITY_BONUS, "Kadur fellowship");
		} else if (!Misc.getFactionMarkets("kadur_remnant").isEmpty()) {
                        market.getStability().modifyFlat(id, ConditionData.STABILITY_LUDDIC_MAJORITY_PENALTY, "Kadur insurgency");
                }
		for (Industry ind : market.getIndustries()) {
			if (ind.getSpec().hasTag(Industries.TAG_INDUSTRIAL)) {
                            ind.getSupplyBonusFromOther().modifyFlat(id, 1f, "Kadur majority");
			}
		}
            } else {unapply(id);}
	}

    /**
     *
     * @param id
     */
    @Override
	public void unapply(String id) {
		market.getStability().unmodify(id);
		for (Industry ind : market.getIndustries()) {
			if (ind.getSpec().hasTag(Industries.TAG_INDUSTRIAL)) {
				ind.getSupplyBonusFromOther().unmodifyFlat(id);
			}
		}
	}
        
    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);
        if (Arrays.asList(kadurFactions).contains(market.getFactionId()) || "player".equals(market.getFactionId()) && Global.getSector().getFaction("kadur_remnant").getRelToPlayer().getRel() >= 0.25f) {
            tooltip.addPara(
                "%s stability",
                10f, 
                Misc.getHighlightColor(),
                "+"+Misc.getRoundedValue(ConditionData.STABILITY_LUDDIC_MAJORITY_BONUS)
        );
	} else if (!Misc.getFactionMarkets(Global.getSector().getFaction("kadur_remnant"), null).isEmpty()) {
            tooltip.addPara(
                "%s stability",
                10f, 
                Misc.getHighlightColor(),
                ""+Misc.getRoundedValue(ConditionData.STABILITY_LUDDIC_MAJORITY_PENALTY)
        );
        } else {
            tooltip.addPara(
                "No stability change for Kadurans are no longer a significant presence in the Persean Sector.",
                10f, 
                Misc.getHighlightColor()
        );
        
        }
            tooltip.addPara("%s production for Mining, Heavy Industry, and similar", 
				10f, Misc.getHighlightColor(),
				"+" + (int)1f);
        
    }
    
    @Override
    public boolean showIcon() {
	return market.getSize() >= 3;
    }
}
