package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityPipedItem extends Entity {
	public EntityPipedItem(World w) {
		super(w);
	}
	
	@Override
	protected void entityInit() {
		getDataWatcher().addObject(10, new ItemStack(Blocks.stone));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		setStack(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("stack")));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		NBTTagCompound st = new NBTTagCompound();
		getStack().writeToNBT(st);
		tag.setTag("stack", st);
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
		
		if(worldObj.isRemote)
			return;
        
        Block blockID = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
        if(blockID != Modjam3Mod.blockChickenPipe) {
        	double c = 1 / (motionX + motionY + motionZ);
        	posX += motionX * c;
        	posY += motionY * c;
        	posZ += motionZ * c;
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
        
        if(!worldObj.isRemote &&
        	((motionX < 0 && prevFX > 0.5 && FX <= 0.5) || (motionY < 0 && prevFY > 0.5 && FY <= 0.5) || (motionZ < 0 && prevFZ > 0.5 && FZ <= 0.5)
        	|| (motionX > 0 && prevFX < 0.5 && FX >= 0.5) || (motionY > 0 && prevFY < 0.5 && FY >= 0.5) || (motionZ > 0 && prevFZ < 0.5 && FZ >= 0.5))){
        	
        	// passed the middle of a pipe; decide direction
        	posX = Math.floor(posX) + 0.5;
        	posY = Math.floor(posY) + 0.5;
        	posZ = Math.floor(posZ) + 0.5;
        	
        	double speed = Math.abs(motionX + motionY + motionZ);
        	int currentDir = (motionX < 0 ? 4 : motionX > 0 ? 5 : motionY < 0 ? 0 : motionY > 0 ? 1 : motionZ < 0 ? 2 : 3);
        	ForgeDirection next = ForgeDirection.VALID_DIRECTIONS[BlockChickenPipe.chooseNextDirection(worldObj, MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), currentDir)];
        	System.out.println("switch to "+next.ordinal()+" from "+currentDir+" at "+MathHelper.floor_double(posX)+" "+MathHelper.floor_double(posY)+" "+MathHelper.floor_double(posZ));
        	motionX = next.offsetX * speed;
        	motionY = next.offsetY * speed;
        	motionZ = next.offsetZ * speed;
        }

	}
}
