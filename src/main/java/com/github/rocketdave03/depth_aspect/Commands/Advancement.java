package com.github.rocketdave03.depth_aspect.Commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class Advancement {
	private static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (commandContext, suggestionsBuilder) -> {
		Collection<net.minecraft.advancement.Advancement> collection = commandContext.getSource().getMinecraftServer().getAdvancementLoader().getAdvancements();
		return CommandSource.suggestIdentifiers(collection.stream().map(net.minecraft.advancement.Advancement::getId), suggestionsBuilder);
	};

//	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
//		dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("advancement").requires((serverCommandSource) -> {
//			return serverCommandSource.hasPermissionLevel(2);
//		})).then(CommandManager.literal("grant").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.literal("only").then(((RequiredArgumentBuilder)CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.GRANT, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.ONLY));
//		})).then(CommandManager.argument("criterion", StringArgumentType.greedyString()).suggests((commandContext, suggestionsBuilder) -> {
//			return CommandSource.suggestMatching((Iterable)IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement").getCriteria().keySet(), suggestionsBuilder);
//		}).executes((commandContext) -> {
//			return executeCriterion((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.GRANT, IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), StringArgumentType.getString(commandContext, "criterion"));
//		}))))).then(CommandManager.literal("from").then(CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.GRANT, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.FROM));
//		})))).then(CommandManager.literal("until").then(CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.GRANT, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.UNTIL));
//		})))).then(CommandManager.literal("through").then(CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.GRANT, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.THROUGH));
//		})))).then(CommandManager.literal("everything").executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.GRANT, ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getAdvancementLoader().getAdvancements());
//		}))))).then(CommandManager.literal("revoke").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.literal("only").then(((RequiredArgumentBuilder)CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.REVOKE, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.ONLY));
//		})).then(CommandManager.argument("criterion", StringArgumentType.greedyString()).suggests((commandContext, suggestionsBuilder) -> {
//			return CommandSource.suggestMatching((Iterable)IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement").getCriteria().keySet(), suggestionsBuilder);
//		}).executes((commandContext) -> {
//			return executeCriterion((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.REVOKE, IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), StringArgumentType.getString(commandContext, "criterion"));
//		}))))).then(CommandManager.literal("from").then(CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.REVOKE, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.FROM));
//		})))).then(CommandManager.literal("until").then(CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.REVOKE, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.UNTIL));
//		})))).then(CommandManager.literal("through").then(CommandManager.argument("advancement", IdentifierArgumentType.identifier()).suggests(SUGGESTION_PROVIDER).executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.REVOKE, select(IdentifierArgumentType.getAdvancementArgument(commandContext, "advancement"), Advancement.Selection.THROUGH));
//		})))).then(CommandManager.literal("everything").executes((commandContext) -> {
//			return executeAdvancement((ServerCommandSource)commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), Advancement.Operation.REVOKE, ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getAdvancementLoader().getAdvancements());
//		})))));
//	}


	public static int requis(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		final ServerCommandSource source = context.getSource();

		List<ServerPlayerEntity> players = context.getArgument("Player", EntitySelector.class).getPlayers(source);

		return requis(context, players);
	}

	public static int requis(CommandContext<ServerCommandSource> context, List<ServerPlayerEntity> players) throws CommandSyntaxException {
		final ServerCommandSource source = context.getSource();

		if(players.size() <= 0)
		{
			throw new SimpleCommandExceptionType(new TranslatableText("argument.player.unknown")).create();
		}


		executeAdvancement(
			source,
			players,
			Operation.GRANT,
			select(
				source.getMinecraftServer().getAdvancementLoader().get(
					new Identifier(
						"depth_aspect:requis"
					)
				),
				Selection.ONLY
			)
		);

		return 1;
	}

	public static int executeAdvancement(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Advancement.Operation operation, Collection<net.minecraft.advancement.Advancement> selection) throws CommandException{
		int i = 0;

		ServerPlayerEntity serverPlayerEntity;
		for(Iterator<ServerPlayerEntity> var5 = targets.iterator(); var5.hasNext(); i += operation.processAll(serverPlayerEntity, selection)) {
			serverPlayerEntity = var5.next();
		}

		if (i == 0) {
			if (selection.size() == 1) {
				if (targets.size() == 1) {
					throw new CommandException(new TranslatableText(operation.getCommandPrefix() + ".one.to.one.failure", selection.iterator().next().toHoverableText(), targets.iterator().next().getDisplayName()));
				} else {
					throw new CommandException(new TranslatableText(operation.getCommandPrefix() + ".one.to.many.failure", selection.iterator().next().toHoverableText(), targets.size()));
				}
			} else if (targets.size() == 1) {
				throw new CommandException(new TranslatableText(operation.getCommandPrefix() + ".many.to.one.failure", selection.size(), targets.iterator().next().getDisplayName()));
			} else {
				throw new CommandException(new TranslatableText(operation.getCommandPrefix() + ".many.to.many.failure", selection.size(), targets.size()));
			}
		} else {
			if (selection.size() == 1) {
				if (targets.size() == 1) {
					source.sendFeedback(new TranslatableText(operation.getCommandPrefix() + ".one.to.one.success", selection.iterator().next().toHoverableText(), targets.iterator().next().getDisplayName()), true);
				} else {
					source.sendFeedback(new TranslatableText(operation.getCommandPrefix() + ".one.to.many.success", selection.iterator().next().toHoverableText(), targets.size()), true);
				}
			} else if (targets.size() == 1) {
				source.sendFeedback(new TranslatableText(operation.getCommandPrefix() + ".many.to.one.success", selection.size(), targets.iterator().next().getDisplayName()), true);
			} else {
				source.sendFeedback(new TranslatableText(operation.getCommandPrefix() + ".many.to.many.success", selection.size(), targets.size()), true);
			}

			return i;
		}
	}

//	private static int executeCriterion(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Advancement.Operation operation, net.minecraft.advancement.Advancement advancement, String criterion) {
//		int i = 0;
//		if (!advancement.getCriteria().containsKey(criterion)) {
//			throw new CommandException(new TranslatableText("commands.advancement.criterionNotFound", advancement.toHoverableText(), criterion));
//		} else {
//
//			for (ServerPlayerEntity serverPlayerEntity : targets) {
//				if (operation.processEachCriterion(serverPlayerEntity, advancement, criterion)) {
//					++i;
//				}
//			}
//
//			if (i == 0) {
//				if (targets.size() == 1) {
//					throw new CommandException(new TranslatableText(operation.getCommandPrefix() + ".criterion.to.one.failure", criterion, advancement.toHoverableText(), targets.iterator().next().getDisplayName()));
//				} else {
//					throw new CommandException(new TranslatableText(operation.getCommandPrefix() + ".criterion.to.many.failure", criterion, advancement.toHoverableText(), targets.size()));
//				}
//			} else {
//				if (targets.size() == 1) {
//					source.sendFeedback(new TranslatableText(operation.getCommandPrefix() + ".criterion.to.one.success", criterion, advancement.toHoverableText(), targets.iterator().next().getDisplayName()), true);
//				} else {
//					source.sendFeedback(new TranslatableText(operation.getCommandPrefix() + ".criterion.to.many.success", criterion, advancement.toHoverableText(), targets.size()), true);
//				}
//
//				return i;
//			}
//		}
//	}

	public static List<net.minecraft.advancement.Advancement> select(net.minecraft.advancement.Advancement advancement, Advancement.Selection selection) {
		List<net.minecraft.advancement.Advancement> list = Lists.newArrayList();
		if (selection.before) {
			for(net.minecraft.advancement.Advancement advancement2 = advancement.getParent(); advancement2 != null; advancement2 = advancement2.getParent()) {
				list.add(advancement2);
			}
		}

		list.add(advancement);
		if (selection.after) {
			addChildrenRecursivelyToList(advancement, list);
		}

		return list;
	}

	public static void addChildrenRecursivelyToList(net.minecraft.advancement.Advancement parent, List<net.minecraft.advancement.Advancement> childList) {

		for (net.minecraft.advancement.Advancement advancement : parent.getChildren()) {
			childList.add(advancement);
			addChildrenRecursivelyToList(advancement, childList);
		}

	}

	enum Selection {
		ONLY(false, false),
		THROUGH(true, true),
		FROM(false, true),
		UNTIL(true, false),
		EVERYTHING(true, true);

		private final boolean before;
		private final boolean after;

		Selection(boolean before, boolean after) {
			this.before = before;
			this.after = after;
		}
	}

	enum Operation {
		GRANT("grant") {
			protected boolean processEach(ServerPlayerEntity player, net.minecraft.advancement.Advancement advancement) {
				AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
				if (advancementProgress.isDone()) {
					return false;
				} else {

					for (String string : advancementProgress.getUnobtainedCriteria()) {
						player.getAdvancementTracker().grantCriterion(advancement, string);
					}

					return true;
				}
			}

			protected boolean processEachCriterion(ServerPlayerEntity player, net.minecraft.advancement.Advancement advancement, String criterion) {
				return player.getAdvancementTracker().grantCriterion(advancement, criterion);
			}
		},
		REVOKE("revoke") {
			protected boolean processEach(ServerPlayerEntity player, net.minecraft.advancement.Advancement advancement) {
				AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
				if (!advancementProgress.isAnyObtained()) {
					return false;
				} else {

					for (String string : advancementProgress.getObtainedCriteria()) {
						player.getAdvancementTracker().revokeCriterion(advancement, string);
					}

					return true;
				}
			}

			protected boolean processEachCriterion(ServerPlayerEntity player, net.minecraft.advancement.Advancement advancement, String criterion) {
				return player.getAdvancementTracker().revokeCriterion(advancement, criterion);
			}
		};

		private final String commandPrefix;

		Operation(String name) {
			this.commandPrefix = "commands.advancement." + name;
		}

		public int processAll(ServerPlayerEntity player, Iterable<net.minecraft.advancement.Advancement> advancements) {
			int i = 0;

			for (net.minecraft.advancement.Advancement advancement : advancements) {
				if (this.processEach(player, advancement)) {
					++i;
				}
			}

			return i;
		}

		protected abstract boolean processEach(ServerPlayerEntity player, net.minecraft.advancement.Advancement advancement);

		protected abstract boolean processEachCriterion(ServerPlayerEntity player, net.minecraft.advancement.Advancement advancement, String criterion);

		protected String getCommandPrefix() {
			return this.commandPrefix;
		}
	}
}
