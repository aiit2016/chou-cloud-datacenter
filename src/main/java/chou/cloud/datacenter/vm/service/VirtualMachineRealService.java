package chou.cloud.datacenter.vm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chou.cloud.datacenter.common.Consts;
import chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.instance.repository.InstanceRepository;

@Component
public class VirtualMachineRealService {

	private static final Logger logger = LoggerFactory.getLogger(VirtualMachineRealService.class);

	@Autowired
	InstanceRepository instanceRepository;

	public void start(Instance instance) {
		// TODO: start virtual machine
		logger.debug("Deal with event EVENT_TYPE_STARTVM. (instance.name=" + instance.getName() + ")");

		try {
			Thread.currentThread().sleep(5 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug("Virtual machine is started. (instance.name=" + instance.getName() + ")");

		instance.setStatus(Consts.INSTANCE_STATUS_ACTIVED);
		instanceRepository.save(instance);
	}

	public void stop(Instance instance) {
		// TODO: stop virtual machine
		logger.debug("Deal with event EVENT_TYPE_STOPVM. (instance.name=" + instance.getName() + ")");

		try {
			Thread.currentThread().sleep(5 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug("Virtual machine is stopped. (instance.name=" + instance.getName() + ")");

		instance.setStatus(Consts.INSTANCE_STATUS_INACTIVED);
		instanceRepository.save(instance);
	}

}