package chou.cloud.datacenter.chou.cloud.datacenter.instance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chou.cloud.datacenter.chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.chou.cloud.datacenter.instance.repository.InstanceRepository;

@Service
@Transactional
public class InstanceService {

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
}
