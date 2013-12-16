package com.immibis.modjam3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBossChicken extends EntityChicken implements IBossDisplayData {

	public static final float SCALE = 8; // was 20
	
	public EntityBossChicken(World par1World) {
		super(par1World);
		setSize(0.3f * SCALE, 0.7f * SCALE);
		
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(300.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(40.0D);
    }
	
	public boolean attackEntityAsMob(Entity par1Entity)
    {
		float damage = 5;
        boolean flag = par1Entity.attackEntityFrom(new EntityDamageSource("immibis_modjam3.chicken", this), damage);

        if (flag)
        {
        	float knockback = 0.2f;
            par1Entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)knockback * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)knockback * 0.5F));
            this.motionX *= 0.6D;
            this.motionZ *= 0.6D;
        }

        return flag;
    }
	
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		do
			this.dropItem(Item.feather.itemID, worldObj.rand.nextInt(32) + 32);
		while(worldObj.rand.nextInt(3) != 0);
		do
			this.dropItem(Modjam3Mod.itemChickenBone.itemID, worldObj.rand.nextInt(32) + 32);
		while(worldObj.rand.nextInt(3) != 0);
		do
			this.dropItem((isBurning() ? Item.chickenCooked : Item.chickenRaw).itemID, worldObj.rand.nextInt(32) + 32);
		while(worldObj.rand.nextInt(3) != 0);
	}

}
