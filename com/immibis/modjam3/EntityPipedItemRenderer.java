package com.immibis.modjam3;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityPipedItemRenderer extends Render {
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
		EntityPipedItem e = (EntityPipedItem)entity;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
