package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private boolean field_74222_o;

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
        	if (PotionHUD.potionPush.equals("true")) {
	            this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
        	} else if (PotionHUD.potionPush.equals("auto")) {
        		this.guiLeft = Math.max(this.guiLeft, 176);
        	}
        	this.field_74222_o = true;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);

        if (this.field_74222_o)
        {
            this.displayDebuffEffects();
        }
    }

    /**
     * Displays debuff/potion effects that are currently being applied to the player
     */
    private void displayDebuffEffects()
    {
        int var1 = this.guiLeft - 124;
        int var2 = this.guiTop;
        Collection var4 = this.mc.thePlayer.getActivePotionEffects();

        if (!var4.isEmpty())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            int var5 = 33;

            if (var4.size() > 5)
            {
                var5 = 132 / (var4.size() - 1);
            }

            ArrayList<PotionEffect> effectList = PotionHUD.sortPotionByDuration(var4);
            for (PotionEffect effect : effectList) {
                Potion potion = Potion.potionTypes[effect.getPotionID()];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.renderEngine.bindTexture("/gui/inventory.png");
                this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

                if (potion.hasStatusIcon())
                {
                    int var9 = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }

                String var11 = StatCollector.translateToLocal(potion.getName());

                if (effect.getAmplifier() == 1)
                {
                    var11 = var11 + " II";
                }
                else if (effect.getAmplifier() == 2)
                {
                    var11 = var11 + " III";
                }
                else if (effect.getAmplifier() == 3)
                {
                    var11 = var11 + " IV";
                }

                this.fontRenderer.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
                String var10 = Potion.getDurationString(effect);
                this.fontRenderer.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
                
                var2 += var5;
            }
        }
    }
}
