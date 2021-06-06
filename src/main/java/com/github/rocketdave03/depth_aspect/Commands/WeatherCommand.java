package com.github.rocketdave03.depth_aspect.Commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class WeatherCommand {
	public static int clear(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerCommandSource source = context.getSource();
		source.getWorld().setWeather(6000, 0, false, false);
		source.sendFeedback(new TranslatableText("commands.weather.set.clear"), true);
		return 6000; //Often we return a positive number related to what we did,
		//in this case the number of clear-weather ticks we set
	}

	public static int rain(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		/* impl goes here */
		return 1;
	}

	public static int thunder(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		/* impl goes here */
		return 1;
	}
}
