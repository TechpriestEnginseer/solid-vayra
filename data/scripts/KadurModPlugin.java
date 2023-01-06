package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.campaign.CampaignTerrainAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.RingBandAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI.SurveyLevel;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.impl.campaign.DerelictShipEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Entities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.ids.Terrain;
import com.fs.starfarer.api.impl.campaign.intel.deciv.DecivTracker;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.impl.campaign.terrain.BaseRingTerrain.RingParams;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import data.scripts.ai.VayraLRMAI;
import data.scripts.ai.VayraSplinterAI;
import data.scripts.campaign.KadurBlueprintStocker;
import data.scripts.world.KadurGen;
import exerelin.campaign.SectorManager;
import java.awt.Color;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

public class KadurModPlugin extends BaseModPlugin {

    public static Logger log = Global.getLogger(KadurModPlugin.class);

    public static final String KADUR_ID = "kadur_remnant";

    public static final String KADUR_SRM = "vayra_partisanmis";
    public static final String KADUR_LRM = "vayra_jerichomis";
    public static final String KADUR_SLOWLRM = "vayra_slowlrm_copy";
    public static final String KADUR_SPLINTER = "vayra_splintergun_shot_copy";
    public static final String KADUR_ANTIFTR = "vayra_antifighter_mis";

    public static boolean VAYRA_DEBUG = false;

    public static boolean EXERELIN_LOADED;
    public static Set<String> EXERELIN_ACTIVE = new HashSet<>();

    @Override
    public void onApplicationLoad() throws Exception {
        try {
            Global.getSettings().getScriptClassLoader().loadClass("org.lazywizard.lazylib.ModUtils");
        } catch (ClassNotFoundException lazy) {
            String message = System.lineSeparator()
                    + System.lineSeparator() + "LazyLib is required to run Kadur Remnant."
                    + System.lineSeparator() + System.lineSeparator()
                    + "You can download LazyLib at http://fractalsoftworks.com/forum/index.php?topic=5444"
                    + System.lineSeparator();
            throw new ClassNotFoundException(message);
        }

        try {
            Global.getSettings().getScriptClassLoader().loadClass("data.scripts.util.MagicTargeting");
        } catch (ClassNotFoundException magic) {
            String message = System.lineSeparator()
                    + System.lineSeparator() + "MagicLib is required to run Kadur Remnant."
                    + System.lineSeparator() + System.lineSeparator()
                    + "You can download MagicLib at http://fractalsoftworks.com/forum/index.php?topic=13718.0"
                    + System.lineSeparator();
            throw new ClassNotFoundException(message);
        }
        //VayraTags.checkSpecial();

        EXERELIN_LOADED = Global.getSettings().getModManager().isModEnabled("nexerelin");
        //Mayasuran Navy
        if (!Global.getSettings().getModManager().isModEnabled("Mayasuran Navy")) {
            try {if (Global.getSettings().getFighterWingSpec("vayra_phalanx_m_wing") != null) {Global.getSettings().getFighterWingSpec("vayra_phalanx_m_wing").getTags().remove("fighter4");Global.getSettings().getFighterWingSpec("vayra_phalanx_m_wing").getTags().remove("fighter");Global.getSettings().getFighterWingSpec("vayra_phalanx_m_wing").addTag(Tags.NO_DROP);Global.getSettings().getFighterWingSpec("vayra_phalanx_m_wing").addTag(Items.TAG_NO_DEALER);Global.getSettings().getFighterWingSpec("vayra_phalanx_m_wing").addTag(Tags.NO_SELL);}} catch (Exception ex) {}
            try {if (Global.getSettings().getHullSpec("vayra_phalanx_m") != null) {Global.getSettings().getHullSpec("vayra_phalanx_m").getHints().add(ShipHullSpecAPI.ShipTypeHints.HIDE_IN_CODEX);}} catch (Exception ex) {}
            try {if (Global.getSettings().getFighterWingSpec("vayra_hoplite_m_wing") != null) {Global.getSettings().getFighterWingSpec("vayra_hoplite_m_wing").getTags().remove("bomber3");Global.getSettings().getFighterWingSpec("vayra_hoplite_m_wing").getTags().remove("bomber");Global.getSettings().getFighterWingSpec("vayra_hoplite_m_wing").addTag(Tags.NO_DROP);Global.getSettings().getFighterWingSpec("vayra_hoplite_m_wing").addTag(Items.TAG_NO_DEALER);Global.getSettings().getFighterWingSpec("vayra_hoplite_m_wing").addTag(Tags.NO_SELL);}} catch (Exception ex) {}
            try {if (Global.getSettings().getHullSpec("vayra_hoplite_m") != null) {Global.getSettings().getHullSpec("vayra_hoplite_m").getHints().add(ShipHullSpecAPI.ShipTypeHints.HIDE_IN_CODEX);}} catch (Exception ex) {}
        }

        try {if (Global.getSettings().getVariant("vayra_caliph_revenant") != null) {Global.getSettings().getVariant("vayra_caliph_revenant").addTag(Tags.SHIP_CAN_NOT_SCUTTLE);Global.getSettings().getVariant("vayra_caliph_revenant").addTag(Tags.SHIP_UNIQUE_SIGNATURE);}} catch (Exception ex) {}
        try {if (Global.getSettings().getVariant("vayra_caliph_revenant_Hull") != null) {Global.getSettings().getVariant("vayra_caliph_revenant_Hull").addTag(Tags.SHIP_CAN_NOT_SCUTTLE);Global.getSettings().getVariant("vayra_caliph_revenant_Hull").addTag(Tags.SHIP_UNIQUE_SIGNATURE);}} catch (Exception ex) {}
        try {if (Global.getSettings().getVariant("vayra_caliph_revenant_station") != null) {Global.getSettings().getVariant("vayra_caliph_revenant_station").addTag(Tags.SHIP_CAN_NOT_SCUTTLE);Global.getSettings().getVariant("vayra_caliph_revenant_station").addTag(Tags.SHIP_UNIQUE_SIGNATURE);}} catch (Exception ex) {}
    }

    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip) {
        switch (missile.getProjectileSpecId()) {
            case KADUR_LRM:
                return new PluginPick<MissileAIPlugin>(new VayraLRMAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SET);
            case KADUR_SPLINTER:
                return new PluginPick<MissileAIPlugin>(new VayraSplinterAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SET);
            default:
                return null;
        }
    }

    @Override
    public void onNewGame() {

        if (EXERELIN_LOADED) {
            if (!SectorManager.getCorvusMode()) {
                // return here because we don't want to generate our handcrafted systems if we're in an exerelin random sector
                return;
            }
        }
        genKadur();
    }
    
    @Override
    public void onGameLoad(boolean newGame) {
        if (Global.getSector().getMemoryWithoutUpdate().get("$vayra_missions_generated") == null) {
            Global.getSector().getMemoryWithoutUpdate().set("$vayra_missions_generated", true);
            StarSystemAPI system = Global.getSector().getStarSystem("Mirage");
            if (system.getEntityById("vayra_mirage_star") != null) {
                for (Object object : Global.getSector().getStarSystem("Mirage").getEntities(CampaignTerrainAPI.class)) {
                    //K1 && K2 Mission Rewards
                    if (object instanceof SectorEntityToken) {
                        SectorEntityToken asteroidsociety = (SectorEntityToken) object;
                        if (asteroidsociety.getCircularOrbitRadius() == 12500 && asteroidsociety.getCircularOrbitPeriod() == 778) {
                            DerelictShipEntityPlugin.DerelictShipData paramsk1 = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("falcon_xiv_Hull"), ShipRecoverySpecial.ShipCondition.WRECKED, "HSS Left Hook", Factions.HEGEMONY, 0f), false);
                            SectorEntityToken K1 = BaseThemeGenerator.addSalvageEntity(system, Entities.WRECK, Factions.NEUTRAL, paramsk1);
                            K1.setDiscoverable(true);
                            K1.setDiscoveryXP(250f);
                            K1.setCircularOrbit(asteroidsociety, 270, 200, 15); //L4 Asteroid.
                            K1.setSalvageXP(250f);
                            K1.getMemoryWithoutUpdate().set("$vayra_k1_eventRef", true);
                            ShipRecoverySpecial.ShipRecoverySpecialData data = new ShipRecoverySpecial.ShipRecoverySpecialData(null);
                            data.addShip(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("falcon_xiv_Hull"), ShipRecoverySpecial.ShipCondition.WRECKED, "HSS Left Hook", Factions.INDEPENDENT, 0f));
                            Misc.setSalvageSpecial(K1, data);
                            DerelictShipEntityPlugin.DerelictShipData paramsk2 = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("vayra_eagle_k_Hull"), ShipRecoverySpecial.ShipCondition.BATTERED, "KHS Babylonian Raven", "kadur_remnant", 0f), false);
                            SectorEntityToken K2 = BaseThemeGenerator.addSalvageEntity(system, Entities.WRECK, Factions.NEUTRAL, paramsk2);
                            K2.setCircularOrbit(asteroidsociety, 90, 300, 15);
                            K2.setDiscoverable(true);
                            K2.setDiscoveryXP(250f);
                            K2.setSalvageXP(250f);
                            K2.getMemoryWithoutUpdate().set("$vayra_k2_eventRef", true);
                            ShipRecoverySpecial.ShipRecoverySpecialData data2 = new ShipRecoverySpecial.ShipRecoverySpecialData(null);
                            data2.addShip(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("vayra_eagle_k_Hull"), ShipRecoverySpecial.ShipCondition.BATTERED, "KHS Babylonian Raven", "kadur_remnant", 0f));
                            Misc.setSalvageSpecial(K2, data2);
                            break;
                        }
                    }
                }
                //K3
                if (system.getEntityById("mirage_ii_jump") != null) {
                    DerelictShipEntityPlugin.DerelictShipData paramsk3 = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("vayra_seraph_standard"), ShipRecoverySpecial.ShipCondition.WRECKED, "KHS Holy Sword", "kadur_remnant", 0f), false);
                    SectorEntityToken K3 = BaseThemeGenerator.addSalvageEntity(system, Entities.WRECK, Factions.NEUTRAL, paramsk3);
                    K3.setDiscoverable(true);
                    K3.setDiscoveryXP(250f);
                    K3.setCircularOrbit(system.getEntityById("mirage_ii_jump"), 270, 200, 15); //Mirage Jump Point of course...
                    K3.setSalvageXP(250f);
                    K3.getMemoryWithoutUpdate().set("$vayra_k3_eventRef", true);
                    ShipRecoverySpecial.ShipRecoverySpecialData data = new ShipRecoverySpecial.ShipRecoverySpecialData(null);
                    data.addShip(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("vayra_seraph_standard"), ShipRecoverySpecial.ShipCondition.WRECKED, "KHS Holy Sword", "kadur_remnant", 0f));
                    Misc.setSalvageSpecial(K3, data);
                }
                //K4
                if (system.getEntityById("mirage_relay") != null) {
                    DerelictShipEntityPlugin.DerelictShipData paramsk4 = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("vayra_targe_crystal"), ShipRecoverySpecial.ShipCondition.WRECKED, "KHS Ceto", "kadur_remnant", 0f), false);
                    SectorEntityToken K4 = BaseThemeGenerator.addSalvageEntity(system, Entities.WRECK, Factions.NEUTRAL, paramsk4);
                    K4.setDiscoverable(true);
                    K4.setDiscoveryXP(250f);
                    K4.setCircularOrbit(system.getEntityById("mirage_relay"), 270, 200, 15); //Mirage Relay of course...
                    K4.setSalvageXP(250f);
                    K4.getMemoryWithoutUpdate().set("$vayra_k4_eventRef", true);
                    ShipRecoverySpecial.ShipRecoverySpecialData data = new ShipRecoverySpecial.ShipRecoverySpecialData(null);
                    data.addShip(new ShipRecoverySpecial.PerShipData(Global.getSettings().getVariant("vayra_targe_crystal"), ShipRecoverySpecial.ShipCondition.WRECKED, "KHS Ceto", "kadur_remnant", 0f));
                    Misc.setSalvageSpecial(K4, data);
                }
                //K5 and K6
                if (Global.getSettings().getMissionScore("vayra_k006") > 0) {Global.getSector().getEntityById("vayra_kadur_revenant").getMarket().getMemoryWithoutUpdate().set("$nex_recentlyCapturedByPlayer", true, 365f);}
                if (Global.getSettings().getMissionScore("vayra_k005") > 0) {Global.getSector().getEntityById("vayra_refugestation").getMarket().getMemoryWithoutUpdate().set("$nex_recentlyCapturedByPlayer", true, 365f);}
                //K7 (if player start with Caliph, disable the HVB)
                if (newGame) {
                    if ("vayra_caliph".equals(Global.getSector().getPlayerFleet().getFlagship().getHullSpec().getBaseHullId())) {
                        //HVB Handling...
                        if (Global.getSettings().getModManager().isModEnabled("vayrasector")) {
                            List<String> spentBounties = (List<String>) Global.getSector().getMemory().get("$vayra_uniqueBountiesSpent");
                            if (spentBounties == null) {
                                spentBounties = new ArrayList<>();
                                Global.getSector().getMemory().set("$vayra_uniqueBountiesSpent", spentBounties);
                                log.info("unique spentBounties was null, adding empty list");
                            } else {
                                log.info("unique spentBounties contains: " + spentBounties);
                            }
                            log.info("spending unique bounty " + "vayra_caliph_bounty");
                            if (!spentBounties.contains("vayra_caliph_bounty")) {
                                spentBounties.add("vayra_caliph_bounty");
                                log.info("unique spentBounties contains: " + spentBounties);
                            } else {
                                log.warn("unique spentBounties already contains " + "vayra_caliph_bounty");
                            }
                        }
                        //MagicBounties Handling...
                         Global.getSector().getMemoryWithoutUpdate().set("$HVB_vayra_caliph_bounty_succeeded", true);
                    }
                }
            }
        }
    }

    @Override
    public void onNewGameAfterTimePass() {

        log.info("new game started, adding scripts");

        // add these scripts regardless of setting, since they all just return immediately if not activated
        // and this way they will activate if you activate the setting midgame
        Global.getSector().addScript(new KadurBlueprintStocker());

    }

    private static void genKadur() {
        new KadurGen().generate(Global.getSector());
    }

    public static float randomRange(float min, float max) {
        return (float) (random() * (max - min) + min);
    }

    public static MarketAPI addMarketplace(String factionID, SectorEntityToken primaryEntity, ArrayList<SectorEntityToken> connectedEntities, String name,
            int size, ArrayList<String> marketConditions, ArrayList<String> submarkets, boolean WithJunkAndChatter, boolean PirateMode, boolean freePort) {

        EconomyAPI globalEconomy = Global.getSector().getEconomy();
        String entityId = primaryEntity.getId();
        String marketId = entityId + "market";

        MarketAPI newMarket = Global.getFactory().createMarket(marketId, name, size);
        newMarket.setFactionId(factionID);
        newMarket.setPrimaryEntity(primaryEntity);
        newMarket.getTariff().modifyFlat("generator", newMarket.getFaction().getTariffFraction());

        if (submarkets != null) {
            for (String market : submarkets) {
                newMarket.addSubmarket(market);
            }
        }

        newMarket.addCondition("population_" + size);
        if (marketConditions != null) {
            for (String condition : marketConditions) {
                try {
                    newMarket.addCondition(condition);
                } catch (RuntimeException e) {
                    newMarket.addIndustry(condition);
                }
            }
        }

        if (connectedEntities != null) {
            for (SectorEntityToken entity : connectedEntities) {
                newMarket.getConnectedEntities().add(entity);
            }
        }

        globalEconomy.addMarket(newMarket, WithJunkAndChatter);
        primaryEntity.setMarket(newMarket);
        primaryEntity.setFaction(factionID);

        createAdmin(newMarket);

        if (connectedEntities != null) {
            for (SectorEntityToken entity : connectedEntities) {
                entity.setMarket(newMarket);
                entity.setFaction(factionID);
            }
        }

        if (PirateMode) {
            newMarket.setEconGroup(newMarket.getId());
            newMarket.setHidden(true);
            primaryEntity.setSensorProfile(1f);
            primaryEntity.setDiscoverable(true);
            primaryEntity.getDetectedRangeMod().modifyFlat("gen", 5000f);
            newMarket.getMemoryWithoutUpdate().set(DecivTracker.NO_DECIV_KEY, true);
        }

        newMarket.setFreePort(freePort);

        for (MarketConditionAPI mc : newMarket.getConditions()) {
            mc.setSurveyed(true);
        }
        newMarket.setSurveyLevel(SurveyLevel.FULL);

        newMarket.reapplyIndustries();

        log.info("created " + factionID + " market " + name);

        return newMarket;
    }

    public static PersonAPI createAdmin(MarketAPI market) {
        FactionAPI faction = market.getFaction();
        PersonAPI admin = faction.createRandomPerson();
        int size = market.getSize();

        switch (size) {
            case 3:
            case 4:
                admin.setRankId(Ranks.GROUND_CAPTAIN);
                break;
            case 5:
                admin.setRankId(Ranks.GROUND_MAJOR);
                break;
            case 6:
                admin.setRankId(Ranks.GROUND_COLONEL);
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                admin.setRankId(Ranks.GROUND_GENERAL);
                break;
            default:
                admin.setRankId(Ranks.GROUND_LIEUTENANT);
                break;
        }

        List<String> skills = Global.getSettings().getSortedSkillIds();

        int industries = 0;
        int defenses = 0;
        boolean military = market.getMemoryWithoutUpdate().getBoolean(MemFlags.MARKET_MILITARY);

        for (Industry curr : market.getIndustries()) {
            if (curr.isIndustry()) {
                industries++;
            }
            if (curr.getSpec().hasTag(Industries.TAG_GROUNDDEFENSES)) {
                defenses++;
            }
        }

        admin.getStats().setSkipRefresh(true);

        int num = 0;
        if (industries >= 2 || (industries == 1 && defenses == 1)) {
            if (skills.contains(Skills.INDUSTRIAL_PLANNING)) {
                admin.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 2);
            }
            num++;
        }

        if (num == 0 || size >= 6) {
            if (military) {
                if (skills.contains(Skills.SPACE_OPERATIONS)) {
                    admin.getStats().setSkillLevel(Skills.SPACE_OPERATIONS, 2);
                }
            } else if (defenses > 0) {
                if (skills.contains(Skills.PLANETARY_OPERATIONS)) {
                    admin.getStats().setSkillLevel(Skills.PLANETARY_OPERATIONS, 2);
                }
            } else {
                // nothing else suitable, so just make sure there's at least one skill, if this wasn't already set
                if (skills.contains(Skills.INDUSTRIAL_PLANNING)) {
                    admin.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);
                }
            }
        }

        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        admin.getStats().setSkipRefresh(false);
        admin.getStats().refreshCharacterStatsEffects();
        admin.setPostId(Ranks.POST_ADMINISTRATOR);
        market.addPerson(admin);
        market.setAdmin(admin);
        market.getCommDirectory().addPerson(admin);
        ip.addPerson(admin);
        ip.getData(admin).getLocation().setMarket(market);
        ip.checkOutPerson(admin, "permanent_staff");

        log.info(String.format("Applying admin %s %s to market %s", market.getFaction().getRank(admin.getRankId()), admin.getNameString(), market.getName()));

        return admin;
    }

    public static String[] JSONArrayToStringArray(JSONArray jsonArray) {
        try {
            String[] ret = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                ret[i] = jsonArray.getString(i);
            }
            return ret;
        } catch (JSONException e) {
            log.warn(e);
            return new String[]{};
        }
    }

    public static String aOrAn(String input) {

        ArrayList<String> vowels = new ArrayList<>(Arrays.asList(
                "a",
                "e",
                "i",
                "o",
                "u"));

        String firstLetter = input.substring(0, 1).toLowerCase();

        if (vowels.contains(firstLetter)) {
            return "an";
        } else {
            return "a";
        }
    }

    public static float Rotate(float currAngle, float addAngle) {
        return (currAngle + addAngle) % 360;
    }

    public static void addAccretionDisk(PlanetAPI star, String name) {
        StarSystemAPI system = star.getStarSystem();
        float orbitRadius = star.getRadius() * 8f;
        float bandWidth = 256f;
        int numBands = 12;

        for (float i = 0; i < numBands; i++) {
            float radius = orbitRadius - i * bandWidth * 0.25f - i * bandWidth * 0.1f;
            float orbitDays = radius / (30f + 10f * Misc.random.nextFloat());
            WeightedRandomPicker<String> rings = new WeightedRandomPicker<>();
            rings.add("rings_dust0");
            rings.add("rings_ice0");
            String ring = rings.pick();
            RingBandAPI visual = system.addRingBand(star, "misc", ring, 256f, 0, Color.white, bandWidth,
                    radius + bandWidth / 2f, -orbitDays);
            float spiralFactor = 2f + Misc.random.nextFloat() * 5f;
            visual.setSpiral(true);
            visual.setMinSpiralRadius(star.getRadius());
            visual.setSpiralFactor(spiralFactor);
        }
        SectorEntityToken ring = system.addTerrain(Terrain.RING, new RingParams(orbitRadius, orbitRadius / 2f, star, name == null ? "Accretion Disk" : name));
        ring.addTag(Tags.ACCRETION_DISK);
        ring.setCircularOrbit(star, 0, 0, -100);
    }
}
