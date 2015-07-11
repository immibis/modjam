package com.immibis.modjam3;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.passive.EntityChicken;

import org.lwjgl.opengl.GL11;

public class EntityBossChickenRender extends RenderChicken {

	public EntityBossChickenRender(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}
	
	@Override
	public void doRender(EntityChicken par1EntityChicken, double par2, double par4, double par6, float par8, float par9) {
		BossStatus.setBossStatus((EntityBossChicken)par1EntityChicken, false);
		
		float scale = Math.min(EntityBossChicken.MAX_SCALE, ((EntityBossChicken)par1EntityChicken).scale + par9 * EntityBossChicken.GROW_RATE);
		GL11.glPushMatrix();
		GL11.glTranslated(par2, par4, par6);
		GL11.glScalef(scale, scale, scale);
		super.doRender(par1EntityChicken, 0, 0, 0, par8, par9);
		GL11.glPopMatrix();
	}
	
}
