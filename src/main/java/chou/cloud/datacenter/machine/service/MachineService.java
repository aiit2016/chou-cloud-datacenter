package chou.cloud.datacenter.machine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chou.cloud.datacenter.machine.entity.Machine;
import chou.cloud.datacenter.machine.repository.MachineRepository;

@Service
@Transactional
public class MachineService {

	@Autowired
	MachineRepository machineRepository;

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
}
