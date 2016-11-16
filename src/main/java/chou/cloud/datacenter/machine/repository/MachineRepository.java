package chou.cloud.datacenter.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chou.cloud.datacenter.machine.entity.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

}
