package chou.cloud.datacenter.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

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
			ssi.setUserName("");
		}
		if (StringUtils.isEmpty(ssi.getPassword())) {
			ssi.setPassword("");
		}
		if (ssi.getPort() == null) {
			ssi.setPort(22);
			;
		}
		if (StringUtils.isEmpty(ssi.getChannelType())) {
			ssi.setChannelType("exec");
			;
		}
	}

	public int exeCommands(List<String> commands) {
		for(String command: commands) {
			int status = exeCommand(command);
			if (status != 0) {
				return status;
			}
		}
	}


	public int exeCommand(String command) {
		int status = -1;

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
					logger.debug("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			status = channel.getExitStatus();
			channel.disconnect();
			session.disconnect();
		} catch (Exception ex) {
			logger.error("Command execute error!(command=" + command + ")", ex);
		}

		return status;
	}

}
