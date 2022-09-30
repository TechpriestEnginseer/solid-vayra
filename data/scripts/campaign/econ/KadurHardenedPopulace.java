package data.scripts.campaign.econ;

import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import java.util.Arrays;

public class KadurHardenedPopulace extends BaseMarketConditionPlugin {
    private static final String [] kadurFactions = new String [] {
		"kadur_remnant",
	};
    /**
     *
     * @param id
     */
    @Override
    public void apply(String id) {
        if (Arrays.asList(kadurFactions).contains(market.getFactionId())) {
			market.getStability().modifyFlat(id, 2, this.getName());
		}
        
    }

    /**
     *
     * @param id
     */
    @Override
    public void unapply(String id) {
        market.getStability().unmodify(id);
    }
    
    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);
        if (Arrays.asList(kadurFactions).contains(market.getFactionId())) {
            tooltip.addPara(
                "%s stability",
                10f, 
                Misc.getHighlightColor(),
                "+"+2
        );
	}
        
    }
    
}
