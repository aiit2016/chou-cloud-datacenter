package chou.cloud.datacenter.instance.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Created by Koala Cheung on 2016/10/31.
 */
@Entity
@Data
public class Instance {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private Long machineId;

    @NotNull
    private Integer cpuSize;

    @NotNull
    private Integer memorySize;

    @NotNull
    @Size(min = 1, max = 16)
    private String status;
}
