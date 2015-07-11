package com.immibis.modjam3.translocator;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class TransFlagEntityProperties implements IExtendedEntityProperties {

	public static final String PROPS_ID = "40ABA91A-BC9F-3603-A45D-0825EA2EE48D";
	
	public boolean isTrans = false;
	
	public Vec3 lastClosestTransSent; // server cache only
	
	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setBoolean(PROPS_ID+".isTrans", isTrans);
	}

	@Override
	public void loadNBTData(NBTTagCompound tag) {
		isTrans = tag.getBoolean(PROPS_ID+".isTrans");
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	
}
