package chou.cloud.datacenter.utils;

import lombok.Data;

/**
 * Created by Koala Cheung on 2016/10/31.
 */
@Data
public class SshServerInfo {

	private String hostName;

	private Integer port;

	private String userName;

	private String password;

	private String channelType;

}
