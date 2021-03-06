package com.github.rocketdave03.depth_aspect.Commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

import static com.github.rocketdave03.depth_aspect.Items.ModItems.REQUIS;

public class RequisAdvancement {
	public static int check(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		final ServerCommandSource source = context.getSource();

		List<ServerPlayerEntity> players = context.getArgument("Player", EntitySelector.class).getPlayers(source);

		for(ServerPlayerEntity player : players)
		{
			List<ServerPlayerEntity> playerL = new ArrayList<>();
			playerL.add(player);
			if (SwapItem.swapItems(context, playerL, REQUIS.getDefaultStack(), Items.GOLDEN_APPLE.getDefaultStack()) > 0) {
				Advancement.requis(context,playerL);
			}
		}



		return 1;
	}
}
