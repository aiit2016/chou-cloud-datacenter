package chou.cloud.datacenter.machine.service;

import chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.instance.repository.InstanceRepository;
import chou.cloud.datacenter.machine.entity.Machine;
import chou.cloud.datacenter.machine.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MachineService {

	@Autowired
	MachineRepository machineRepository;

	@Autowired
	InstanceRepository instanceRepository;

	public List<Machine> findAll() {
		return machineRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	public Machine save(Machine machine) {
		return machineRepository.save(machine);
	}

	public void delete(Long id) {
		machineRepository.delete(id);
	}

	public Machine find(Long id) {
		return machineRepository.findOne(id);
	}

	public Instance startCreatingInstance(Instance newInstance) {
		long maxScore = 0;
		Machine appropriateMachine = null;
		for(Map.Entry<Machine, Long> entry: getAvailableMachineWithScore(newInstance).entrySet()) {
			long score = entry.getValue();
			if (maxScore < score) {
				appropriateMachine = entry.getKey();
				maxScore = score;
			}
		}

		if (appropriateMachine == null) {
			return null;
		}
		newInstance.setMachineId(appropriateMachine.getId());

		//TODO: callCreateShell by other shell
		return newInstance;
	}

	private Map<Machine, Long> getAvailableMachineWithScore(Instance newInstance) {
		List<Instance> instances = instanceRepository.findAll();
		Map<Machine, Long> availableMachines = new HashMap<Machine, Long>();

		for (Machine machine: machineRepository.findAll()) {
			int usingCpu = 0;
			int usingMemory = 0;

			for (Instance instance: instances) {
				if (instance.getMachineId().longValue() == machine.getId().longValue()) {
					usingCpu += instance.getCpuSize();
					usingMemory += instance.getMemorySize();
				}
			}

			long cpuRoom = machine.getCpuSize() - usingCpu - newInstance.getCpuSize();
			long memoryRoom = machine.getMemorySize() - usingMemory - newInstance.getMemorySize();
			if (cpuRoom <= 0 || memoryRoom <= 0){
				continue;
			}

			availableMachines.put(machine, cpuRoom * cpuRoom + memoryRoom * memoryRoom);
		}

		return availableMachines;
	}
}
