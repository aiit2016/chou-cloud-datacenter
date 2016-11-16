package chou.cloud.datacenter.machine.controller;

import chou.cloud.datacenter.machine.entity.Machine;
import chou.cloud.datacenter.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Koala Cheung on 2016/10/31.
 */
@RestController
@RequestMapping("/machines")
public class MachineController {
	@Autowired
	MachineService machineService;

	@RequestMapping(method = RequestMethod.GET)
	List<Machine> getMachines() {
		return machineService.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	Machine insertMachine(@Validated @RequestBody Machine machine) {
		return machineService.save(machine);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	Machine updateMachine(@PathVariable("id") Long id, @Validated @RequestBody Machine machine) {
		machine.setId(id);
		return machineService.save(machine);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void deleteMachine(@PathVariable("id") Long id) {
		machineService.delete(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	Machine getMachine(@PathVariable("id") Long id) {
		return machineService.find(id);
	}
}
