package chou.cloud.datacenter.chou.cloud.datacenter.vm;

import static reactor.bus.selector.Selectors.$;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chou.cloud.datacenter.chou.cloud.datacenter.ctools.Consts;
import chou.cloud.datacenter.chou.cloud.datacenter.instance.entity.Instance;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

@Component
public class EventConfiguration {

	@Autowired
	private EventBus r;

	@Autowired
	private VirtualMachineRealService virtualMachineRealService;

	@PostConstruct
	public void onStartUp() {
		r.on($(Consts.EVENT_TYPE_STARTVM), startVirtualMachine());
		r.on($(Consts.EVENT_TYPE_STOPVM), stopVirtualMachine());
	}

	private Consumer<Event<Instance>> startVirtualMachine() {
		return event -> virtualMachineRealService.start(event.getData());
	}

	private Consumer<Event<Instance>> stopVirtualMachine() {
		return event -> virtualMachineRealService.stop(event.getData());
	}

}