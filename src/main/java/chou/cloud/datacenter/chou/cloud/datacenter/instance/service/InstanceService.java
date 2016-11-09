package chou.cloud.datacenter.chou.cloud.datacenter.instance.service;

import chou.cloud.datacenter.chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.chou.cloud.datacenter.instance.repository.InstanceRepository;
import chou.cloud.datacenter.chou.cloud.datacenter.machine.entity.Machine;
import chou.cloud.datacenter.chou.cloud.datacenter.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstanceService {
	@Autowired
	MachineService MachineService;

	@Autowired
	InstanceRepository instanceRepository;

	public List<Instance> findAll() {
		return instanceRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	public Instance save(Instance instance) {
		return instanceRepository.save(instance);
	}

	public void delete(Long id) {
		instanceRepository.delete(id);
	}

	public Instance find(Long id) {
		return instanceRepository.findOne(id);
	}

	public Machine getMachine(Instance instance) {
		Long machineId = instance.getMachineId();
		return MachineService.find(machineId);
	}
}
