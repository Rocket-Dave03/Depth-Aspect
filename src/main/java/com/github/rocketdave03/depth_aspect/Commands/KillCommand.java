package com.github.rocketdave03.depth_aspect.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;

public class KillCommand{

	public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		context.getSource().getPlayer().kill();
		return 1; //positive numbers are success! Negative numbers are failure.
	}
}
