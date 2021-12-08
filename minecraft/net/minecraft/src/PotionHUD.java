package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class PotionHUD extends FCAddOn {
	public static String potionPush = "auto";
	
	private static PotionHUD instance;
	
	public PotionHUD() {
		super("1.9 Potion HUD", "1.1.0", "PHUD");
		this.isRequiredClientAndServer = false;
	}
	
	public static PotionHUD getInstance() {
		if (instance == null) {
			instance = new PotionHUD();
		}
		return instance;
	}
	
	@Override
	public void PreInitialize() {
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		registerProperties();
	}
	
	@Override
	public void Initialize() {
		FCAddOnHandler.LogMessage(this.getName() + " Initialized");
	}
	
	@Override
	public void handleConfigProperties(Map<String, String> propertyValues) {
		potionPush = propertyValues.get("potionPush");
	}
	
	private void registerProperties() {
		registerProperty("potionPush", "auto", "Setting `potionPush=true` is equivalent to Vanilla functionality. "
				+ "`potionPush=false` keeps the GUI in the center of the screen at all times, "
				+ "which may cause the status icons to go off screen for small resolutions. "
				+ "`potionPush=auto` attempts to keep the GUI at the center of the screen if there "
				+ "is enough space to do so, and otherwise reverts to Vanilla functionality.");
	}
	
	public static ArrayList<PotionEffect> sortPotionByDuration(Collection<PotionEffect> collection) {
    	ArrayList<PotionEffect> effectlist = new ArrayList<PotionEffect>();
        for (PotionEffect potioneffect : collection) {
        	effectlist.add(potioneffect);
        }
        
        Collections.sort(effectlist, new Comparator<PotionEffect>() {
        	@Override
			public int compare(PotionEffect effect1, PotionEffect effect2) {
				if (effect1.getIsAmbient()) {
					return -1;
				} else if (effect2.getIsAmbient()) {
					return 1;
				} else {
					return Integer.compare(effect2.getDuration(), effect1.getDuration());
				}
			}
        });
        return effectlist;
    }
}
