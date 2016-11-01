package chou.cloud.datacenter.chou.cloud.datacenter.instance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chou.cloud.datacenter.chou.cloud.datacenter.instance.entity.Instance;

public interface InstanceRepository extends JpaRepository<Instance, Long> {

}
