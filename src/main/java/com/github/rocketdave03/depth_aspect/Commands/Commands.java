package com.github.rocketdave03.depth_aspect.Commands;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;


public class Commands {
	public static void registerCommands()
	{
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

			//Swap Items Command
			{
				LiteralCommandNode<ServerCommandSource> swapItemsNode = CommandManager
					.literal("swapitems")
					.executes(SwapItem::hasPermission)
					.build();

				ArgumentCommandNode<ServerCommandSource, EntitySelector> entityNode = CommandManager
					.argument("Player", EntityArgumentType.entities())
					.build();

				ArgumentCommandNode<ServerCommandSource, ItemStackArgument> sourceNode = CommandManager
					.argument("Item A", ItemStackArgumentType.itemStack())
					.build();

				ArgumentCommandNode<ServerCommandSource, ItemStackArgument> destinationNode = CommandManager
					.argument("Item B", ItemStackArgumentType.itemStack())
					.executes(SwapItem::swapItems)
					.build();

				dispatcher.getRoot().addChild(swapItemsNode);

				swapItemsNode	.addChild(entityNode		);
				entityNode		.addChild(sourceNode		);
				sourceNode		.addChild(destinationNode	);
			}
			//Requis command
			{
				LiteralCommandNode<ServerCommandSource> requis = CommandManager
					.literal("requis")
					.build();

				ArgumentCommandNode<ServerCommandSource,EntitySelector> entityNode = CommandManager
					.argument("Player",EntityArgumentType.entities())
					.executes(RequisAdvancement::check)
					.build();

				dispatcher.getRoot().addChild(requis);
				requis.addChild(entityNode);
			}
		});
	}
}



