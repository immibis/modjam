package com.immibis.modjam3.translocator;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.Vec3;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketClosestTrans implements IMessage {
	public Vec3 val;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		double x = buf.readDouble();
		if(Double.isFinite(x)) {
			double y = buf.readDouble();
			double z = buf.readDouble();
			val = Vec3.createVectorHelper(x, y, z);
		} else
			val = null;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		if(val == null) {
			buf.writeDouble(Double.NaN);
		} else {
			buf.writeDouble(val.xCoord);
			buf.writeDouble(val.yCoord);
			buf.writeDouble(val.zCoord);
		}
	}
}
