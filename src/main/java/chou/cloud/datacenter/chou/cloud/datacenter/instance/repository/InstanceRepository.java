package chou.cloud.datacenter.chou.cloud.datacenter.instance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import chou.cloud.datacenter.chou.cloud.datacenter.instance.entity.Instance;

public interface InstanceRepository extends JpaRepository<Instance, Long> {

	List<Instance> findByStatus(String status);

}
