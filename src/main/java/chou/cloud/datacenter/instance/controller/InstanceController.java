package chou.cloud.datacenter.instance.controller;

import chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.instance.service.InstanceService;
import chou.cloud.datacenter.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Koala Cheung on 2016/10/31.
 */
@RestController
@RequestMapping("/instances")
public class InstanceController {
	@Autowired
	InstanceService instanceService;

	@Autowired
	MachineService machineService;

	@RequestMapping(method = RequestMethod.GET)
	List<Instance> getInstances() {
		return instanceService.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	Instance insertInstance(@Validated @RequestBody Instance instance) {
		return instanceService.createInstance(instance);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	Instance updateInstance(@PathVariable("id") Long id, @Validated @RequestBody Instance instance) {
		instance.setId(id);
		return instanceService.save(instance);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void deleteInstance(@PathVariable("id") Long id) {
		instanceService.delete(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	Instance getInstance(@PathVariable("id") Long id) {
		return instanceService.find(id);
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	List<Instance> getInstances(@RequestParam Map<String, String> requestParams) {
		String status = requestParams.get("status");
		return instanceService.findByStatus(status);
	}

	@RequestMapping(value = "{id}/up", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Instance upInstance(@PathVariable("id") Long id) {
		return instanceService.upInstance(id);
	}

	@RequestMapping(value = "{id}/down", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Instance downInstance(@PathVariable("id") Long id) {
		return instanceService.downInstance(id);
	}

	@RequestMapping(value = "{id}/asup", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	List<Instance> asupInstance(@PathVariable("id") Long id) {
		return instanceService.asupInstance(id, 5);
	}

	@RequestMapping(value = "{id}/asdown", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	List<Instance> asdownInstance(@PathVariable("id") Long id) {
		return instanceService.asdownInstance(id);
	}

}
