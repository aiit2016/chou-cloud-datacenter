package chou.cloud.datacenter.key.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chou.cloud.datacenter.instance.repository.InstanceRepository;
import chou.cloud.datacenter.key.entity.Key;
import chou.cloud.datacenter.key.repository.KeyRepository;

@Service
@Transactional
public class KeyService {

	@Autowired
	KeyRepository keyRepository;

	@Autowired
	InstanceRepository instanceRepository;

	public List<Key> findAll() {
		return keyRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	public Key save(Key key) {
		return keyRepository.save(key);
	}

	public void delete(Long id) {
		keyRepository.delete(id);
	}

	public Key find(Long id) {
		return keyRepository.findOne(id);
	}

}
