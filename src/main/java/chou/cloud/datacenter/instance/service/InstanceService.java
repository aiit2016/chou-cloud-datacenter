package chou.cloud.datacenter.instance.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chou.cloud.datacenter.common.Consts;
import chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.instance.repository.InstanceRepository;
import chou.cloud.datacenter.vm.service.VirtualMachineService;

@Service
@Transactional
public class InstanceService {

	@Autowired
	InstanceRepository instanceRepository;

	@Autowired
	VirtualMachineService virtualMachineService;

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

	public List<Instance> findByStatus(String status) {
		return instanceRepository.findByStatus(status);
	}

	public List<Instance> findByName(String name) {
		return instanceRepository.findByName(name);
	}

	public Instance upInstance(Long id) {
		Instance instance = instanceRepository.findOne(id);
		if (Consts.INSTANCE_STATUS_STARTING.equals(instance.getStatus())
				|| Consts.INSTANCE_STATUS_ACTIVED.equals(instance.getStatus())) {
			return instance;
		}

		instance.setStatus(Consts.INSTANCE_STATUS_STARTING);
		instance = instanceRepository.save(instance);

		virtualMachineService.start(instance);

		return instance;
	}

	public Instance downInstance(Long id) {
		Instance instance = instanceRepository.findOne(id);
		if (Consts.INSTANCE_STATUS_STOPPING.equals(instance.getStatus())
				|| Consts.INSTANCE_STATUS_INACTIVED.equals(instance.getStatus())) {
			return instance;
		}

		instance.setStatus(Consts.INSTANCE_STATUS_STOPPING);
		instance = instanceRepository.save(instance);

		virtualMachineService.stop(instance);

		return instance;
	}

	public List<Instance> asupInstance(Long id, int size) {
		List<Instance> instances = new ArrayList<Instance>(size);
		Instance instance = upInstance(id);
		instances.add(instance);

		for (int i = 0; i < size - 1; i++) {
			instance.setId(null);
			instance = instanceRepository.save(instance);
			instance = upInstance(instance.getId());
			instances.add(instance);
		}

		return instances;
	}

	public List<Instance> asdownInstance(Long id) {
		Instance instance = instanceRepository.findOne(id);
		List<Instance> instances = instanceRepository.findByName(instance.getName());

		for (int i = 0; i < instances.size(); i++) {
			if (!instance.getId().equals(instances.get(i).getId())){
				instances.set(i, downInstance(instances.get(i).getId()));
			}
		}

		return instances;
	}

}