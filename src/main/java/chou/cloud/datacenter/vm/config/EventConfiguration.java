package chou.cloud.datacenter.vm.config;

import static reactor.bus.selector.Selectors.$;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chou.cloud.datacenter.common.Consts;
import chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.vm.service.VirtualMachineRealService;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

@Component
public class EventConfiguration {
	private ExecutorService executor = Executors.newCachedThreadPool();

	@Autowired
	private EventBus r;

	@Autowired
	private VirtualMachineRealService virtualMachineRealService;

	@PostConstruct
	public void onStartUp() {
		r.on($(Consts.EVENT_TYPE_CREATEVM), createVirtualMachine());
		r.on($(Consts.EVENT_TYPE_STARTVM), startVirtualMachine());
		r.on($(Consts.EVENT_TYPE_STOPVM), stopVirtualMachine());
	}

	private Consumer<Event<Instance>> createVirtualMachine() {
//		return event -> virtualMachineRealService.create(event.getData());
		return event -> executor.execute(new Runnable() {
			@Override
			public void run() {
				virtualMachineRealService.create(event.getData());
			}
		});
	}

	private Consumer<Event<Instance>> startVirtualMachine() {
//		return event -> virtualMachineRealService.start(event.getData());
		return event -> executor.execute(new Runnable() {
			@Override
			public void run() {
				virtualMachineRealService.start(event.getData());
			}
		});
	}

	private Consumer<Event<Instance>> stopVirtualMachine() {
//		return event -> virtualMachineRealService.stop(event.getData());
		return event -> executor.execute(new Runnable() {
			@Override
			public void run() {
				virtualMachineRealService.stop(event.getData());
			}
		});
	}

}