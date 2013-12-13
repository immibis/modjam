package com.immibis.modjam3;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class TileEntityIChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_110637_a = new ResourceLocation("immibis_modjam3", "textures/ichest.png");

    /** The Ender Chest Chest's model. */
    private ModelChest theEnderChestModel = new ModelChest() {
    	ModelRenderer chin;
    	
    	{
    		// magic
    		chestKnob.cubeList.clear();
        	chestKnob.addBox(-2.0F, -2.0F, -16.0F, 4, 2, 2, 0.0F);
        	chin = new ModelRenderer(this, 0, 4).setTextureSize(64, 64);
            chin.addBox(-1.0F, -0.0F, -15.0F, 2, 2, 2, 0.0F);
            chin.rotationPointX = 8.0F;
    		chin.rotationPointY = 7.0F;
    		chin.rotationPointZ = 15.0F;
    	}
    	
        @Override
        public void renderAll() {
    		super.renderAll();
    		chin.rotateAngleX = chestLid.rotateAngleX;
    		chin.render(0.0625f);
    	}
    };

    /**
     * Helps to render Ender Chest.
     */
    public void renderEnderChest(TileEntityIChest par1TileEntityEnderChest, double par2, double par4, double par6, float par8)
    {
        int i = 0;

        if (par1TileEntityEnderChest.hasWorldObj())
        {
            i = par1TileEntityEnderChest.getBlockMetadata();
        }

        this.bindTexture(field_110637_a);
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = 0;

        if (i == 2)
        {
            short1 = 180;
        }

        if (i == 3)
        {
            short1 = 0;
        }

        if (i == 4)
        {
            short1 = 90;
        }

        if (i == 5)
        {
            short1 = -90;
        }

        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float f1 = par1TileEntityEnderChest.prevLidAngle + (par1TileEntityEnderChest.lidAngle - par1TileEntityEnderChest.prevLidAngle) * par8;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        this.theEnderChestModel.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
        this.theEnderChestModel.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderEnderChest((TileEntityIChest)par1TileEntity, par2, par4, par6, par8);
    }
}
