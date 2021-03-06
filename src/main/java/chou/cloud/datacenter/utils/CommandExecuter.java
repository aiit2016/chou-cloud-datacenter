package chou.cloud.datacenter.utils;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class CommandExecuter {

	private static final Logger logger = LoggerFactory.getLogger(CommandExecuter.class);

	private SshServerInfo ssi;

	public CommandExecuter(SshServerInfo ssi) {
		super();
		this.ssi = ssi;
		if (this.ssi == null) {
			ssi = new SshServerInfo();
		}
		if (StringUtils.isEmpty(ssi.getUserName())) {
//			ssi.setUserName("admin");
			ssi.setUserName("root");
		}
		if (StringUtils.isEmpty(ssi.getPassword())) {
//			ssi.setPassword("net03.password");
			ssi.setPassword("net030password");
		}
		if (ssi.getPort() == null) {
			ssi.setPort(22);
		}
		if (StringUtils.isEmpty(ssi.getChannelType())) {
			ssi.setChannelType("exec");
		}
	}

	public boolean exeCommands(List<String> commands) {
		boolean status = false;
		for (String command : commands) {
			status = exeCommand(command);
			if (!status) {
				break;
			}
		}
		return status;
	}

	public boolean exeCommand(String command) {
		logger.debug("execute command: " + command);

		boolean status = false;

		try {
			Hashtable<String, String> config = new Hashtable<String, String>();
			config.put("StrictHostKeyChecking", "no");
			JSch.setConfig(config);

			JSch jsch = new JSch();
			// String knownhost = "/home/****/.ssh/known_hosts"; // known_hosts
			// jsch.setKnownHosts(knownhost);

			// connect session
			Session session = jsch.getSession(ssi.getUserName(), ssi.getHostName(), ssi.getPort());
			session.setPassword(ssi.getPassword());
			session.connect();

			// exec command remotely
			ChannelExec channel = (ChannelExec) session.openChannel(ssi.getChannelType());
			channel.setCommand(command);
			channel.connect();

			// get stdout
			InputStream in = channel.getInputStream();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					logger.debug(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			logger.debug("Command exit status:" + channel.getExitStatus());
			status = channel.getExitStatus() == 0;
			channel.disconnect();
			session.disconnect();
		} catch (Exception ex) {
			logger.error("Command execute error!(command=" + command + ")", ex);
		}

		logger.debug("exit status: " + status);
		return status;
	}

}
