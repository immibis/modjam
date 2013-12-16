package com.immibis.modjam3;

import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

public class EntityBossChicken extends EntityChicken implements IBossDisplayData {

	public EntityBossChicken(World par1World) {
		super(par1World);
		setSize(6.0f, 14.0f); // 20x normal size
	}

}
