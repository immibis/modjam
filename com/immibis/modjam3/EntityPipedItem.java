package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

public class EntityPipedItem extends Entity {
	public EntityPipedItem(World w) {
		super(w);
	}
	
	@Override
	protected void entityInit() {
		getDataWatcher().addObject(10, new ItemStack(1, 1, 0));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		setStack(ItemStack.loadItemStackFromNBT(tag));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		getStack().writeToNBT(tag);
	}
	
	public ItemStack getStack() {
		return getDataWatcher().getWatchableObjectItemStack(10);
	}
	
	public void setStack(ItemStack stack) {
		getDataWatcher().updateObject(10, stack);
	}
	
	@Override
	public void onUpdate() {
		ItemStack stack = getStack();

        super.onUpdate();
        
        int blockID = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
        if(blockID != Modjam3Mod.blockChickenPipe.blockID) {
        	EntityItem ei = new EntityItem(worldObj, posX, posY, posZ, stack);
        	ei.motionX = motionX;
        	ei.motionY = motionY;
        	ei.motionZ = motionZ;
        	worldObj.spawnEntityInWorld(ei);
        	setDead();
        	return;
        }
        
        double prevFX = posX - Math.floor(posX);
        double prevFY = posY - Math.floor(posY);
        double prevFZ = posZ - Math.floor(posZ);

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        
        double FX = posX - Math.floor(posX);
        double FY = posY - Math.floor(posY);
        double FZ = posZ - Math.floor(posZ);
        
        if((motionX < 0 && prevFX > 0.5 && FX <= 0.5) || (motionY < 0 && prevFY > 0.5 && FY <= 0.5) || (motionZ < 0 && prevFZ > 0.5 && FZ <= 0.5)
        	|| (motionX > 0 && prevFX < 0.5 && FX >= 0.5) || (motionY > 0 && prevFY < 0.5 && FY >= 0.5) || (motionZ > 0 && prevFZ < 0.5 && FZ >= 0.5)) {
        	
        	// passed the middle of a pipe; decide direction
        	posX = Math.floor(posX) + 0.5;
        	posY = Math.floor(posY) + 0.5;
        	posZ = Math.floor(posZ) + 0.5;
        	
        	double speed = motionX + motionY + motionZ;
        	int currentDir = (motionX < 0 ? 4 : motionX > 0 ? 5 : motionY < 0 ? 0 : motionY > 0 ? 1 : motionZ < 0 ? 2 : 3);
        	ForgeDirection next = ForgeDirection.VALID_DIRECTIONS[BlockChickenPipe.chooseNextDirection(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), currentDir)];
        	motionX = next.offsetX * speed;
        	motionY = next.offsetY * speed;
        	motionZ = next.offsetZ * speed;
        }

	}
}
