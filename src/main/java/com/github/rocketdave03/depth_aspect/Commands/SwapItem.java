package com.github.rocketdave03.depth_aspect.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class SwapItem {

	public static LiteralCommandNode register(CommandDispatcher<Object> dispatcher) { // You can also return a LiteralCommandNode for use with possible redirects
		return dispatcher.register(literal("giveMeDiamond")
				.executes(SwapItem::giveDiamond));
	}

	public static int giveDiamond(CommandContext<Object> ctx) throws CommandSyntaxException {
		final ServerCommandSource source = (ServerCommandSource) ctx.getSource();

		final PlayerEntity self = source.getPlayer(); // If not a player than the command ends
		if(!self.inventory.insertStack(new ItemStack(Items.DIAMOND))){
			throw new SimpleCommandExceptionType(new TranslatableText("inventory.isfull")).create();
		}

		return 1;
	}


	public static int tiom(CommandContext<ServerCommandSource> context) {
		return 1;
	}
}
