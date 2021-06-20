package com.github.rocketdave03.depth_aspect.Commands;

import com.github.rocketdave03.depth_aspect.DepthAspect;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;


public class SwapItem {
//	public static int giveDiamond(CommandContext<Object> context) throws CommandSyntaxException {
//		final ServerCommandSource source = (ServerCommandSource) context.getSource();
//
//		final PlayerEntity self = source.getPlayer(); // If not a player than the command ends
//		if(!self.inventory.insertStack(new ItemStack(Items.DIAMOND))){
//			throw new SimpleCommandExceptionType(new TranslatableText("inventory.isfull")).create();
//		}
//
//		return 1;
//	}

	public static int hasPermission(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
	{
		final ServerCommandSource source = context.getSource();
		boolean hasPerms = source.hasPermissionLevel(2);
		if(hasPerms)
		{
			return 1;
		}
		throw new SimpleCommandExceptionType(new TranslatableText("commands.help.failed")).create();

	}

	public static int swapItems(CommandContext<ServerCommandSource> context,List<ServerPlayerEntity> players, ItemStack itemA, ItemStack itemB) throws CommandSyntaxException {
		final ServerCommandSource source = context.getSource();
		if(players.size() <= 0)
		{
			throw new SimpleCommandExceptionType(new TranslatableText("argument.player.unknown")).create();
		}

		int totalCount = 0;
		for (ServerPlayerEntity player : players) {
			while(player.inventory.getSlotWithStack(itemA) != -1)
			{
				int slot =  player.inventory.getSlotWithStack(itemA);
				int count = player.inventory.getStack(slot).getCount();
				totalCount += count;

				player.inventory.removeStack(slot);

				ItemStack stack = itemB.copy();
				stack.setCount(count);
				player.inventory.setStack(slot, stack);
			}
		}
		return totalCount;
	}
	public static int swapItems(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

		List<ServerPlayerEntity> players = context.getArgument("Player", EntitySelector.class).getPlayers(context.getSource());

		ItemStack itemA = context.getArgument("Item A", ItemStackArgument.class).getItem().getDefaultStack();
		ItemStack itemB = context.getArgument("Item B", ItemStackArgument.class).getItem().getDefaultStack();

		return swapItems(context, players, itemA, itemB);
	}
}
