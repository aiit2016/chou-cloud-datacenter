package chou.cloud.datacenter.machine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.instance.repository.InstanceRepository;
import chou.cloud.datacenter.machine.entity.Machine;
import chou.cloud.datacenter.machine.repository.MachineRepository;

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

	public Machine getAvailableMachine(Instance newInstance) {
		Machine availableMachine = null;
		for (Machine machine : machineRepository.findAll()) {
			List<Instance> instances = instanceRepository.findByMachineId(machine.getId());
			int usingCpu = 0;
			int usingMemory = 0;

			for (Instance instance : instances) {
				usingCpu += instance.getCpuSize();
				usingMemory += instance.getMemorySize();
			}

			long cpuRoom = machine.getCpuSize() - usingCpu - newInstance.getCpuSize();
			long memoryRoom = machine.getMemorySize() - usingMemory - newInstance.getMemorySize();
			if (cpuRoom >= 0 && memoryRoom >= 0) {
				availableMachine = machine;
				break;
			}
		}

		return availableMachine;
	}
}
