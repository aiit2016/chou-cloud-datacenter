package chou.cloud.datacenter.key.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chou.cloud.datacenter.key.entity.Key;

public interface KeyRepository extends JpaRepository<Key, Long> {

}
