package chou.cloud.datacenter.vm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chou.cloud.datacenter.common.Consts;
import chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.instance.repository.InstanceRepository;
import chou.cloud.datacenter.machine.entity.Machine;
import chou.cloud.datacenter.machine.service.MachineService;
import chou.cloud.datacenter.utils.CommandBuilder;
import chou.cloud.datacenter.utils.CommandExecuter;
import chou.cloud.datacenter.utils.SshServerInfo;

@Component
public class VirtualMachineRealService {

	private static final Logger logger = LoggerFactory.getLogger(VirtualMachineRealService.class);

	@Autowired
	InstanceRepository instanceRepository;

	@Autowired
	MachineService machineService;

	public void start(Instance instance) {
		logger.debug("Deal with event EVENT_TYPE_STARTVM. (instance.name=" + instance.getName() + ")");

		Machine machine = machineService.getAvailableMachine(instance);
		if (machine == null) {
			instance.setStatus(Consts.INSTANCE_STATUS_INVALID);
			instance.setDescription("No available resource!");
			instanceRepository.save(instance);

			logger.error("No available resource to start virtual machine!");
			return;
		}

		instance.setMachineId(machine.getId());
		instanceRepository.save(instance);

		// start VM
		SshServerInfo ssi = new SshServerInfo();
		ssi.setHostName(machine.getIpAddress());
		CommandExecuter ce = new CommandExecuter(ssi);
		List<String> commands = CommandBuilder.buildStartVmCommands(instance);
		boolean status = ce.exeCommands(commands);

		// update status
		if (!status) {
			instance.setStatus(Consts.INSTANCE_STATUS_INVALID);
			instance.setDescription("Failured to start virtual machine!");
			instanceRepository.save(instance);

			logger.error("Failured to start virtual machine!");
		} else {
			instance.setStatus(Consts.INSTANCE_STATUS_ACTIVED);
			instanceRepository.save(instance);

			logger.debug("Virtual machine is started.");
		}
	}

	public void stop(Instance instance) {
		logger.debug("Deal with event EVENT_TYPE_STOPVM. (instance.name=" + instance.getName() + ")");

		Machine machine = machineService.find(instance.getMachineId());

		// stop VM
		SshServerInfo ssi = new SshServerInfo();
		ssi.setHostName(machine.getIpAddress());
		CommandExecuter ce = new CommandExecuter(ssi);
		List<String> commands = CommandBuilder.buildStopVmCommands(instance);
		boolean status = ce.exeCommands(commands);

		// update status
		if (!status) {
			instance.setStatus(Consts.INSTANCE_STATUS_INVALID);
			instance.setDescription("Failured to stop virtual machine!");
			instanceRepository.save(instance);

			logger.error("Failured to stop virtual machine!");
		} else {
			instance.setStatus(Consts.INSTANCE_STATUS_INACTIVED);
			instanceRepository.save(instance);

			logger.debug("Virtual machine is stopped.");
		}
	}

}