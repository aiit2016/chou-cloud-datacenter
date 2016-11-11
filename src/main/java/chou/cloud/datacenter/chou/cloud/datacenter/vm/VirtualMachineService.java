package chou.cloud.datacenter.chou.cloud.datacenter.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chou.cloud.datacenter.chou.cloud.datacenter.ctools.Consts;
import chou.cloud.datacenter.chou.cloud.datacenter.instance.entity.Instance;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class VirtualMachineService {

	private static final Logger logger = LoggerFactory.getLogger(VirtualMachineRealService.class);

	@Autowired
	private EventBus r;

	public void start(Instance instance) {
		logger.debug("Fire event EVENT_TYPE_STARTVM. (instance.name=" + instance.getName() + ")");
		r.notify(Consts.EVENT_TYPE_STARTVM, Event.wrap(instance));
	}

	public void stop(Instance instance) {
		logger.debug("Fire event EVENT_TYPE_STOPVM. (instance.name=" + instance.getName() + ")");
		r.notify(Consts.EVENT_TYPE_STOPVM, Event.wrap(instance));
	}

}