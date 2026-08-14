package data.missions.vayra_k008;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;
import com.fs.starfarer.api.util.Misc;
import java.util.List;
import java.util.Random;

public class MissionDefinition implements MissionDefinitionPlugin {

    @Override
    public void defineMission(MissionDefinitionAPI api) {
        api.initFleet(FleetSide.PLAYER, "KHS", FleetGoal.ATTACK, false);
        api.initFleet(FleetSide.ENEMY, "ISS", FleetGoal.ATTACK, true);

        api.setFleetTagline(FleetSide.PLAYER, "Parish of the Harrowing");
        api.setFleetTagline(FleetSide.ENEMY, "Tech-scavengers");

        api.addBriefingItem("Qamar ships are aggressive, but short-lived in contrast to their Kadur counterparts.");
        api.addBriefingItem("The KHS Dhu'l-Kifl must survive.");
        
        PersonAPI officer = OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 5, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 2, new Random());
        officer.getName().setFirst("Hazqiyal");
        officer.getName().setLast("");
        officer.setGender(FullName.Gender.MALE);
        officer.setPortraitSprite(OfficerManagerEvent.pickPortraitPreferNonDuplicate(Global.getSector().getFaction("kadur_remnant"), FullName.Gender.MALE));
        api.addToFleet(FleetSide.PLAYER, "vayra_prophet_qamar_retribution", FleetMemberType.SHIP, "KHS Dhu'l-Kifl", true).setCaptain(officer);
        api.defeatOnShipLoss("KHS Dhu'l-Kifl");
        
        api.addToFleet(FleetSide.PLAYER, "vayra_falcon_qamar_strike", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 4, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 2, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_archimandrite_qamar_strike", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 4, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 2, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_archimandrite_qamar_strike", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 4, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 2, new Random()));
        
        api.addToFleet(FleetSide.PLAYER, "vayra_falchion_qamar_standard", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 3, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 2, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_falchion_qamar_standard", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 3, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 2, new Random()));
        
        api.addToFleet(FleetSide.PLAYER, "vayra_camel_qamar_assault", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 2, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 1, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_camel_qamar_torpedo", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 2, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 1, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_camel_qamar_missile", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 2, OfficerManagerEvent.SkillPickPreference.YES_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 1, new Random()));

        api.addToFleet(FleetSide.PLAYER, "vayra_hyena_qamar_missile", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 1, OfficerManagerEvent.SkillPickPreference.NO_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 0, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_hyena_qamar_missile", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 1, OfficerManagerEvent.SkillPickPreference.NO_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 0, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_mendicant_qamar_assault", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 1, OfficerManagerEvent.SkillPickPreference.NO_ENERGY_NO_BALLISTIC_YES_MISSILE_YES_DEFENSE, true, null, true, true, 0, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_mendicant_qamar_assault", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 1, OfficerManagerEvent.SkillPickPreference.NO_ENERGY_NO_BALLISTIC_YES_MISSILE_YES_DEFENSE, true, null, true, true, 0, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_mendicant_qamar_strike", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 1, OfficerManagerEvent.SkillPickPreference.NO_ENERGY_NO_BALLISTIC_YES_MISSILE_YES_DEFENSE, true, null, true, true, 0, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_mendicant_qamar_strike", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 1, OfficerManagerEvent.SkillPickPreference.NO_ENERGY_NO_BALLISTIC_YES_MISSILE_YES_DEFENSE, true, null, true, true, 0, new Random()));
        api.addToFleet(FleetSide.PLAYER, "vayra_mendicant_qamar_missile", FleetMemberType.SHIP, false).setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("kadur_remnant"), 1, OfficerManagerEvent.SkillPickPreference.NO_ENERGY_NO_BALLISTIC_YES_MISSILE_NO_DEFENSE, true, null, true, true, 0, new Random()));

        FleetMemberAPI member = api.addToFleet(FleetSide.ENEMY, "odyssey_Balanced", FleetMemberType.SHIP, false);
        member.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("tritachyon"), 4, FleetFactoryV3.getSkillPrefForShip(member), true, null, true, false, 2, new Random()));
        
        FleetMemberAPI member6 = api.addToFleet(FleetSide.ENEMY, "heron_Attack", FleetMemberType.SHIP, false);
        member6.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 2, FleetFactoryV3.getSkillPrefForShip(member6), true, null, true, false, 0, new Random()));
        FleetMemberAPI member7 = api.addToFleet(FleetSide.ENEMY, "heron_Attack", FleetMemberType.SHIP, false);
        member7.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 2, FleetFactoryV3.getSkillPrefForShip(member7), true, null, true, false, 0, new Random()));
        FleetMemberAPI member8 = api.addToFleet(FleetSide.ENEMY, "heron_Strike", FleetMemberType.SHIP, false);
        member8.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 2, FleetFactoryV3.getSkillPrefForShip(member8), true, null, true, false, 0, new Random()));
        
        FleetMemberAPI member2 = api.addToFleet(FleetSide.ENEMY, "medusa_Attack", FleetMemberType.SHIP, false);
        member2.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 2, FleetFactoryV3.getSkillPrefForShip(member2), true, null, true, false, 0, new Random()));
        FleetMemberAPI member3 = api.addToFleet(FleetSide.ENEMY, "medusa_CS", FleetMemberType.SHIP, false);
        member3.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 2, FleetFactoryV3.getSkillPrefForShip(member3), true, null, true, false, 0, new Random()));
        FleetMemberAPI member4 = api.addToFleet(FleetSide.ENEMY, "medusa_PD", FleetMemberType.SHIP, false);
        member4.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 2, FleetFactoryV3.getSkillPrefForShip(member4), true, null, true, false, 0, new Random()));
        FleetMemberAPI member5 = api.addToFleet(FleetSide.ENEMY, "medusa_CS", FleetMemberType.SHIP, false);
        member5.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 2, FleetFactoryV3.getSkillPrefForShip(member5), true, null, true, false, 0, new Random()));
        
        FleetMemberAPI member9 = api.addToFleet(FleetSide.ENEMY, "shade_Assault", FleetMemberType.SHIP, false);
        member9.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("tritachyon"), 2, FleetFactoryV3.getSkillPrefForShip(member5), true, null, true, false, 1, new Random()));
        
        FleetMemberAPI member10 = api.addToFleet(FleetSide.ENEMY, "centurion_Assault", FleetMemberType.SHIP, false);
        member10.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 1, FleetFactoryV3.getSkillPrefForShip(member10), true, null, true, false, 0, new Random()));
        FleetMemberAPI member11 = api.addToFleet(FleetSide.ENEMY, "centurion_Assault", FleetMemberType.SHIP, false);
        member11.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 1, FleetFactoryV3.getSkillPrefForShip(member11), true, null, true, false, 0, new Random()));
        FleetMemberAPI member12 = api.addToFleet(FleetSide.ENEMY, "centurion_Assault", FleetMemberType.SHIP, false);
        member12.setCaptain(OfficerManagerEvent.createOfficer(Global.getSector().getFaction("independent"), 1, FleetFactoryV3.getSkillPrefForShip(member12), true, null, true, false, 0, new Random()));

        float width = 15000f;
        float height = 10000f;
        api.initMap(-width / 2f, width / 2f, -height / 2f, height / 2f);

        //api.addObjective(width / 2f * -0.333f, height / 2f * -0.333f, "nav_buoy");
        api.addObjective(width / 2f * -0.666f, height / 2f * 0.333f, "sensor_array");
        api.addObjective(width / 2f * 0.2f/*0.333f*/, height / 2f * -0.333f, "nav_buoy");
        api.addObjective(width / 2f * 0.8f, height / 2f * -0.666f, "sensor_array");

        for (int i = 0; i < 20; i++) {
            float x = (float) Math.random() * width - width / 2;
            float y = (float) Math.random() * height - height / 2;
            float size = 333f + (float) Math.random() * 666f;
            api.addNebula(x, y, size);
        }
        // Add some planets.  These are defined in data/config/planets.json.
        api.addPlanet(0, 0, 600f, "ice_giant", 105f, true); //600f
        api.addPlanet(-width*0.3f, height*0.1f, width*0.025f, "frozen3", 10f, false); //60f
        api.addPlanet(width*0.4f, -height*0.4f, width*0.035f, "cryovolcanic", 14f, false); //80f
            api.addPlugin(new BaseEveryFrameCombatPlugin() {
			public void init(CombatEngineAPI engine) {
			}
			public void advance(float amount, List events) {
                            if (Global.getCombatEngine().isPaused()) {
                                return;
                            }
                            for (ShipAPI ship : Global.getCombatEngine().getShips()) {
                                if (ship.getCustomData().get("poopystinky") == null) {
                                    if (ship.getCaptain() != null && ship.getOwner() == 0) {
                                        String text = "";
                                        for (int u = 0; u < ship.getCaptain().getStats().getSkillsCopy().size(); u++) {
                                            if (!((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().isAptitudeEffect()) {
                                                if (u < ship.getCaptain().getStats().getSkillsCopy().size()-1) {text = text+(((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getLevel() > 1 ?  ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+"+, " :  ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+", ");} else {text = text+(((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getLevel() > 1 ? ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+"+." :  ((MutableCharacterStatsAPI.SkillLevelAPI) ship.getCaptain().getStats().getSkillsCopy().get(u)).getSkill().getName()+".");}
                                            }
                                        }
									if (ship.getFleetMember() != null && Global.getCombatEngine().getPlayerShip() == ship) {
									Global.getCombatEngine().getCombatUI().addMessage(1, ship.getFleetMember(), Misc.getPositiveHighlightColor(), ship.getName(), Misc.getTextColor(), "", Global.getSettings().getColor("standardTextColor"), "is skilled in "+text);}
                                    }
                                    ship.setCurrentCR(ship.getCurrentCR()+ship.getMutableStats().getMaxCombatReadiness().getModifiedValue()); //Properly adds the max CR, for some reason it cannot be caught as FleetMemberAPI or this would have been easier...
                                    ship.setCRAtDeployment(ship.getCRAtDeployment()+ship.getMutableStats().getMaxCombatReadiness().getModifiedValue()); //This only affects the "score" result of said mission, but the algorithm is mostly 100% since you have to basically LOSE ships to lose score. I don't think this needs setting, but eh couldn't help but tried.
                                    ship.setCustomData("poopystinky", true); //Fires once per ship.
                                }
                            }
                        }
		});
    }
}
