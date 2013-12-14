package com.immibis.modjam3;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAngryChicken extends EntityChicken {
	private EntityPlayer target;
	
	public EntityAngryChicken(World w) {
		super(w);
		
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(60.0D);
    }
	
	@Override
	public void onLivingUpdate() {
		// TODO Auto-generated method stub
		super.onLivingUpdate();
	}
	
	@Override
	public void setAIMoveSpeed(float par1) {
		super.setAIMoveSpeed(par1);
		jumpMovementFactor = par1;
	}
	
	public EntityAngryChicken(World w, EntityPlayer target) {
		this(w);
		
		int dx = w.rand.nextInt(31) - 15;
		int dy = 10 + w.rand.nextInt(15);
		int dz = w.rand.nextInt(31) - 15;
		
		int x = (int)Math.floor(target.posX) + dx;
		int y = (int)target.posY + dy;
		int z = (int)Math.floor(target.posZ) + dz; 
		
		setPosition(x + 0.5, y + 0.5, z + 0.5);
		
		this.target = target;
	}
	
	private double tdx, tdy, tdz;
	private int randomizeTicks;
	
	public void onEntityUpdate() {
		
		setAttackTarget(target);
		
		super.onEntityUpdate();
		
		if(--randomizeTicks < 0) {
			randomizeTicks = 20;
			tdx = worldObj.rand.nextDouble()*5 - 2.5;
			tdy = worldObj.rand.nextDouble()*3;
			tdz = worldObj.rand.nextDouble()*5 - 2.5;
		}
		
		if((target == null || target.isDead || target.getHealth() <= 0) && !worldObj.isRemote)
			setDead();
		
		else if(!worldObj.isRemote && !onGround) {
			getMoveHelper().setMoveTo(target.posX+tdx, target.posY+tdy, target.posZ+tdz, 1);
		}
		
		if(!worldObj.isRemote && target != null) {
			if(target.posY+tdy > posY) {
				motionY += 0.2f;
			}
		}
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
}
