package chou.cloud.datacenter.machine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import chou.cloud.datacenter.machine.entity.Machine;
import chou.cloud.datacenter.machine.service.MachineService;

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
