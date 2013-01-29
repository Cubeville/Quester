package com.gmail.molnardad.quester.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.molnardad.quester.QuestManager;
import com.gmail.molnardad.quester.Quester;
import com.gmail.molnardad.quester.commandbase.QCommand;
import com.gmail.molnardad.quester.commandbase.QCommandContext;
import com.gmail.molnardad.quester.commandbase.QCommandLabels;
import com.gmail.molnardad.quester.exceptions.QuesterException;

public class DescCommands {
	
	QuestManager qMan = null;
	
	public DescCommands(Quester plugin) {
		qMan = plugin.getQuestManager();
	}
	
	@QCommandLabels({"set", "s"})
	@QCommand(
			desc = "sets quest description",
			min = 0,
			max = 1,
			usage = "[new description]")
	public void set(QCommandContext context, CommandSender sender) throws QuesterException {
		String desc = "";
		if(context.length() > 0) {
			desc = context.getString(0);
		}
		qMan.setQuestDescription(sender.getName(), desc);
		sender.sendMessage(ChatColor.GREEN + context.getSenderLang().Q_DESC_SET);
	}
	
	@QCommandLabels({"add", "a"})
	@QCommand(
			desc = "adds to quest description",
			min = 1,
			max = 1,
			usage = "<description to add>")
	public void add(QCommandContext context, CommandSender sender) throws QuesterException {
		qMan.setQuestDescription(sender.getName(), context.getString(0));
		sender.sendMessage(ChatColor.GREEN + context.getSenderLang().Q_DESC_SET);
	}
}
