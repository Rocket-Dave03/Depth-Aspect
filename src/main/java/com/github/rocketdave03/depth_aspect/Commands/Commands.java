package com.github.rocketdave03.depth_aspect.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;


public class Commands {

	public static void registerCommands()
	{

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

			LiteralCommandNode<ServerCommandSource> swapItemsNode = CommandManager
					.literal("swapItems")
					.build();

			CommandManager.argument("Item Source", ItemStackArgumentType.itemStack())
					.executes(SwapItem::tiom);




			/*
				dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("setblock")
						.requires((serverCommandSource) - >
						{
							return serverCommandSource.hasPermissionLevel(2);
						}))
					.then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
						.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder) CommandManager.argument("block", BlockStateArgumentType.blockState())
										.executes((commandContext) - >
										{
											return execute((ServerCommandSource) commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"), BlockStateArgumentType.getBlockState(commandContext, "block"), SetBlockCommand.Mode.REPLACE, (Predicate) null);
										}))
									.then(CommandManager.literal("destroy")
										.executes((commandContext) - >
										{
											return execute((ServerCommandSource) commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"), BlockStateArgumentType.getBlockState(commandContext, "block"), SetBlockCommand.Mode.DESTROY, (Predicate) null);
										})))
								.then(CommandManager.literal("keep")
									.executes((commandContext) - >
									{
										return execute((ServerCommandSource) commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"), BlockStateArgumentType.getBlockState(commandContext, "block"), SetBlockCommand.Mode.REPLACE, (cachedBlockPosition) - >
										{
											return cachedBlockPosition.getWorld()
												.isAir(cachedBlockPosition.getBlockPos());
										});
									})))
							.then(CommandManager.literal("replace")
								.executes((commandContext) - >
								{
									return execute((ServerCommandSource) commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"), BlockStateArgumentType.getBlockState(commandContext, "block"), SetBlockCommand.Mode.REPLACE, (Predicate) null);
								})))));
			 */

		});


//		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
//			//Make some new nodes
//			LiteralCommandNode<ServerCommandSource> killNode = CommandManager
//					.literal("kill")
//					.executes(KillCommand::run)
//					.build();
//
//			LiteralCommandNode<ServerCommandSource> weatherNode = CommandManager
//					.literal("weather")
//					.build();
//
//			LiteralCommandNode<ServerCommandSource> clearNode = CommandManager
//					.literal("clear")
//					.executes(WeatherCommand::clear)
//					.build();
//
//			LiteralCommandNode<ServerCommandSource> rainNode = CommandManager
//					.literal("rain")
//					.executes(WeatherCommand::rain)
//					.build();
//
//			LiteralCommandNode<ServerCommandSource> thunderNode = CommandManager
//					.literal("thunder")
//					.executes(WeatherCommand::thunder)
//					.build();
//
//			//Now stitch them together
//
//			//usage: /kill
//			dispatcher.getRoot().addChild(killNode);
//
//			//usage: /weather [clear|rain|thunder]
//			dispatcher.getRoot().addChild(weatherNode);
//			weatherNode.addChild(clearNode);
//			weatherNode.addChild(rainNode);
//			weatherNode.addChild(thunderNode);
//		});
	}
}



