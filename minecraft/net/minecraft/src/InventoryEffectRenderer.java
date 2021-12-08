package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private boolean hasActivePotionEffects;

    public InventoryEffectRenderer(Container par1Container)
    {
        super(par1Container);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();

        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty())
        {
        	Properties config = loadConfig();
        	String potionPush = config.getProperty("potionpush", "auto");
        	if (potionPush.equals("true")) {
		        this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
        	} else if (potionPush.equals("auto")) {
        		this.guiLeft = Math.max(this.guiLeft, 176);
        	}
        	this.hasActivePotionEffects = true;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);

        if (this.hasActivePotionEffects)
        {
            this.displayDebuffEffects();
        }
    }

    /**
     * Displays debuff/potion effects that are currently being applied to the player
     */
    private void displayDebuffEffects()
    {
        int i = this.guiLeft - 124;
        int j = this.guiTop;
        Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();

        if (!collection.isEmpty())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            int l = 33;

            if (collection.size() > 5)
            {
                l = 132 / (collection.size() - 1);
            }
            
            ArrayList<PotionEffect> effectlist = sortPotionByDuration(collection);
            for (PotionEffect potioneffect : effectlist)
            {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.renderEngine.bindTexture("/gui/inventory.png");
                this.drawTexturedModalRect(i, j, 0, 166, 140, 32);

                if (potion.hasStatusIcon())
                {
                    int var9 = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(i + 6, j + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }

                String s1 = StatCollector.translateToLocal(potion.getName());

                if (potioneffect.getAmplifier() == 1)
                {
                    s1 = s1 + " II";
                }
                else if (potioneffect.getAmplifier() == 2)
                {
                    s1 = s1 + " III";
                }
                else if (potioneffect.getAmplifier() == 3)
                {
                    s1 = s1 + " IV";
                }

                this.fontRenderer.drawStringWithShadow(s1, i + 10 + 18, j + 6, 16777215);
                String s = Potion.getDurationString(potioneffect);
                this.fontRenderer.drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, 8355711);
                
                j += l;
            }
        }
    }
    
    private ArrayList<PotionEffect> sortPotionByDuration(Collection<PotionEffect> collection) {
    	ArrayList<PotionEffect> effectlist = new ArrayList<PotionEffect>();
        for (PotionEffect potioneffect : collection) {
        	effectlist.add(potioneffect);
        }
        Collections.sort(effectlist, new Comparator<PotionEffect>() {
			@Override
			public int compare(PotionEffect o1, PotionEffect o2) {
				if (o1.getIsAmbient()) {
					return -1;
				} else if (o2.getIsAmbient()) {
					return 1;
				} else {
					return Integer.compare(o2.getDuration(), o1.getDuration());
				}
			}
        });
        return effectlist;
    }
    
    private Properties loadConfig() {
    	File configFile = configPath();
    	Properties config = new Properties();
    	
    	try {
			FileReader reader = new FileReader(configFile);
			config.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Potion HUD config file not found. Creating...");
			config = createDefaultConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return config;
    }
    
    private Properties createDefaultConfig() {
    	File configFile = configPath();
    	Properties config = new Properties();
    	
    	config.setProperty("potionpush", "auto");
    	
    	try {
    		configFile.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(configFile);
			config.store(writer, "Potion HUD Config");
			writer.close();
			System.out.println("Potion HUD config created at: " + configFile.getPath());
		} catch (IOException e) {
			System.out.println("Cannot create Potion HUD config file: " + e.getMessage());
		}
    	
    	return config;
    }
    
    private File configPath() {
    	return new File(new File(Minecraft.getMinecraftDir(), "config"), "potionhud.cfg");
    }
}
