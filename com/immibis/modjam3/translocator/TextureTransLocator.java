package com.immibis.modjam3.translocator;

import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.world.World;

import com.immibis.modjam3.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureTransLocator extends TextureCompass {
	public TextureTransLocator(String name) {
		super(name);
	}
	
    @Override
	public void updateCompass(World p_94241_1_, double p_94241_2_, double p_94241_4_, double p_94241_6_, boolean p_94241_8_, boolean p_94241_9_) {
        if (this.framesTextureData.isEmpty())
        	return;

        double targetAngle;

    	if (p_94241_1_ == null || p_94241_8_ || ClientProxy.nearestTrans == null) {
            targetAngle = Math.random() * Math.PI * 2.0D;
        } else {
        	double d4 = (double)ClientProxy.nearestTrans.xCoord - p_94241_2_;
            double d5 = (double)ClientProxy.nearestTrans.zCoord - p_94241_4_;
            if(d4 == 0 && d5 == 0) {
            	targetAngle = Math.random() * Math.PI * 2.0D;
            } else {
            	p_94241_6_ %= 360.0D;
            	targetAngle = -((p_94241_6_ - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));
            }
        }

        double targetAngleRelative = targetAngle - this.currentAngle;
        while(targetAngleRelative < -Math.PI) targetAngleRelative += Math.PI*2;
        while(targetAngleRelative >= Math.PI) targetAngleRelative -= Math.PI*2;
        targetAngleRelative = Math.min(1, Math.max(-1, targetAngleRelative));

        this.angleDelta += targetAngleRelative * 0.1D;
        this.angleDelta *= 0.8D;
        this.currentAngle += this.angleDelta;
        
        int newFrameIndex = (int)((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size();

        while(newFrameIndex < 0)
        	newFrameIndex = (newFrameIndex + this.framesTextureData.size()) % this.framesTextureData.size();

        if (newFrameIndex != this.frameCounter) {
            this.frameCounter = newFrameIndex;
            TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
        }
    }
}
