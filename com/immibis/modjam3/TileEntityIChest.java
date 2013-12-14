package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityIChest extends TileEntity implements IInventory {
	/** The current angle of the chest lid (between 0 and 1) */
    public float lidAngle;

    /** The angle of the chest lid last tick */
    public float prevLidAngle;

    /** The number of players currently using this ender chest. */
    public int numUsingPlayers;

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    
    String owner = "";
    InventoryPlayer inv;
    EntityPlayer ply;
    ItemStack[] cl_inv = new ItemStack[36];

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        
        if(!worldObj.isRemote) {
        	if(ply == null || ply.isDead)
        		ply = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(owner);
        	inv = (ply == null ? null : ply.inventory);
        	cl_inv = null;
        	
        }

        if (++this.ticksSinceSync % 20 * 4 == 0)
        {
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, getBlockType().blockID, 1, this.numUsingPlayers);
        }

        this.prevLidAngle = this.lidAngle;
        float f = 0.1F;
        double d0;

        if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F)
        {
            double d1 = (double)this.xCoord + 0.5D;
            d0 = (double)this.zCoord + 0.5D;
            this.worldObj.playSoundEffect(d1, (double)this.yCoord + 0.5D, d0, "immibis_modjam3:ichest.open", 0.5F, 1.0f);
        }

        if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F)
        {
            float f1 = this.lidAngle;

            if (this.numUsingPlayers > 0)
            {
                this.lidAngle += f;
            }
            else
            {
                this.lidAngle -= f;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;

            if (this.lidAngle < f2 && f1 >= f2)
            {
                d0 = (double)this.xCoord + 0.5D;
                double d2 = (double)this.zCoord + 0.5D;
                this.worldObj.playSoundEffect(d0, (double)this.yCoord + 0.5D, d2, "immibis_modjam3:ichest.close", 0.5F, 1.0f);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public boolean receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            this.numUsingPlayers = par2;
            return true;
        }
        else
        {
            return super.receiveClientEvent(par1, par2);
        }
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        this.updateContainingBlockInfo();
        super.invalidate();
    }

    public void openChest()
    {
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, getBlockType().blockID, 1, this.numUsingPlayers);
    }

    public void closeChest()
    {
        --this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, getBlockType().blockID, 1, this.numUsingPlayers);
    }

    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
    	super.writeToNBT(par1nbtTagCompound);
    	par1nbtTagCompound.setString("owner", owner);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
    	super.readFromNBT(par1nbtTagCompound);
    	owner = par1nbtTagCompound.getString("owner");
    }

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(worldObj.isRemote) {
			if(cl_inv[i] == null)
				return null;
			if(cl_inv[i].stackSize <= j) {
				ItemStack rv = cl_inv[i];
				cl_inv[i] = null;
				return rv;
			}
			return cl_inv[i].splitStack(j);
		}
		return inv == null ? null : inv.decrStackSize(i, j);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public String getInvName() {
		return "immibis_modjam3.ichest_inv";
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
	@Override
	public int getSizeInventory() {
		return 36;
	}
	
	@Override
	public ItemStack getStackInSlot(int i) {
		return worldObj.isRemote ? cl_inv[i] : inv != null ? inv.getStackInSlot(i) : null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return worldObj.isRemote || inv != null;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		if(inv != null)
			inv.onInventoryChanged();
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if(worldObj.isRemote)
			cl_inv[i] = itemstack;
		else if(inv != null)
			inv.setInventorySlotContents(i, itemstack);
	}
}
