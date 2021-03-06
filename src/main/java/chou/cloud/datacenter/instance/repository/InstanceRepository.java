package chou.cloud.datacenter.instance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import chou.cloud.datacenter.instance.entity.Instance;

public interface InstanceRepository extends JpaRepository<Instance, Long> {

	List<Instance> findByStatus(String status);

	List<Instance> findByName(String name);

	List<Instance> findByMachineId(Long machineId);

}
