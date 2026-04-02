package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class VayraGuardianIFF extends BaseHullMod {

    /*@Override
    public boolean isApplicableToShip(ShipAPI ship) {
        return (ship.getHullSize() != HullSize.CAPITAL_SHIP);
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        if (ship != null && ship.getHullSize() == HullSize.CAPITAL_SHIP) {
            return "Cannot be installed on capital ships";
        }

        return null;
    }*/
    @Override
    public void applyEffectsToFighterSpawnedByShip(ShipAPI fighter, ShipAPI ship, String id) {
        MutableShipStatsAPI stats = fighter.getMutableStats();
        boolean sMod = isSMod(ship.getMutableStats());
	if (sMod) {
            stats.getMaxSpeed().modifyPercent(id, 10f);
            stats.getAcceleration().modifyPercent(id, 10f);
            stats.getDeceleration().modifyPercent(id, 10f);
            stats.getTurnAcceleration().modifyPercent(id, 10f);
            stats.getMaxTurnRate().modifyPercent(id, 10f);
            stats.getBallisticWeaponDamageMult().modifyPercent(id, 10f);
            stats.getEnergyWeaponDamageMult().modifyPercent(id, 10f);
            stats.getMissileWeaponDamageMult().modifyPercent(id, 10f);
            stats.getArmorDamageTakenMult().modifyMult(id, 0.9f);
            stats.getShieldDamageTakenMult().modifyMult(id, 0.9f);
            stats.getHullDamageTakenMult().modifyMult(id, 0.9f);
	} else {
            stats.getMaxSpeed().modifyPercent(id, 5f);
            stats.getAcceleration().modifyPercent(id, 5f);
            stats.getDeceleration().modifyPercent(id, 5f);
            stats.getTurnAcceleration().modifyPercent(id, 5f);
            stats.getMaxTurnRate().modifyPercent(id, 5f);
            stats.getBallisticWeaponDamageMult().modifyPercent(id, 5f);
            stats.getEnergyWeaponDamageMult().modifyPercent(id, 5f);
            stats.getMissileWeaponDamageMult().modifyPercent(id, 5f);
            stats.getArmorDamageTakenMult().modifyMult(id, 0.95f);
            stats.getShieldDamageTakenMult().modifyMult(id, 0.95f);
            stats.getHullDamageTakenMult().modifyMult(id, 0.95f);
        }

    }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        return ship != null && (ship.getHullSpec().getFighterBays() > 0 || ship.getHullSpec().getBuiltInWings().size() > 0 || ship.getVariant().hasHullMod(HullMods.CONVERTED_HANGAR));
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        if (ship != null && (ship.getHullSpec().getFighterBays() < 1 || ship.getHullSpec().getBuiltInWings().size() < 1 || !ship.getVariant().hasHullMod(HullMods.CONVERTED_HANGAR))) {
            return "Ship has no fighter bays";
        }
        return null;
    }
    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return "" + (int) 5f + "%";
        }
        return null;
    }
    
    public String getSModDescriptionParam(int index, HullSize hullSize) {
	if (index == 0) return "" + (int) 5 + "%";
	return null;
    }
    
}
