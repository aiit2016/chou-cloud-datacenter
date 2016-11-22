package chou.cloud.datacenter.utils;

import java.util.ArrayList;
import java.util.List;

import chou.cloud.datacenter.instance.entity.Instance;

public class CommandBuilder {

	private static String createVmCommand = "python /usr/local/bin/createvm.py %s %d %d ";

	private static String startVmCommand = "python /usr/local/bin/startvm.py %s ";

	private static String stopVmCommand = "python /usr/local/bin/stopvm.py %s ";

	public static List<String> buildCreateVmCommands(Instance instance) {
		List<String> commands = new ArrayList<String>();
		String command = String.format(createVmCommand, instance.getName(), instance.getCpuSize(),
				instance.getMemorySize());
		commands.add(command);
		return commands;
	}

	public static List<String> buildStartVmCommands(Instance instance) {
		List<String> commands = new ArrayList<String>();
		String command = String.format(startVmCommand, instance.getName());
		commands.add(command);
		return commands;
	}

	public static List<String> buildStopVmCommands(Instance instance) {
		List<String> commands = new ArrayList<String>();
		String command = String.format(stopVmCommand, instance.getName());
		commands.add(command);
		return commands;
	}

}
