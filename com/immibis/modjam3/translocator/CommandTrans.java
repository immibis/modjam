package com.immibis.modjam3.translocator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;

public class CommandTrans extends CommandBase {

	@Override
	public String getCommandName() {
		return "cbtrans";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/"+getCommandName()+" [cis|trans]";
	}

	@Override
	public void processCommand(ICommandSender ply, String[] args) {
		if(!(ply instanceof EntityPlayer)) {
			ply.addChatMessage(new ChatComponentTranslation("immibis_modjam3.cmd.trans.players_only"));
			return;
		}
		TransFlagEntityProperties tfep = (TransFlagEntityProperties)((EntityPlayer)ply).getExtendedProperties(TransFlagEntityProperties.PROPS_ID);
		if(tfep == null) {
			((EntityPlayer)ply).registerExtendedProperties(TransFlagEntityProperties.PROPS_ID, tfep = new TransFlagEntityProperties());
		}
		
		if(args.length == 1 && args[0].equals("trans")) {
			ply.addChatMessage(new ChatComponentTranslation("immibis_modjam3.cmd.trans."+(!tfep.isTrans?"now":"already")+".true"));
			tfep.isTrans = true;
		} else if(args.length == 1 && args[0].equals("cis")) {
			ply.addChatMessage(new ChatComponentTranslation("immibis_modjam3.cmd.trans."+(tfep.isTrans?"now":"already")+".false"));
			tfep.isTrans = false;
		} else {
			ply.addChatMessage(new ChatComponentTranslation("immibis_modjam3.cmd.trans.is." + (tfep.isTrans ? "true" : "false")));
			ply.addChatMessage(new ChatComponentTranslation("immibis_modjam3.cmd.trans.usage", getCommandUsage(ply)));
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

}
