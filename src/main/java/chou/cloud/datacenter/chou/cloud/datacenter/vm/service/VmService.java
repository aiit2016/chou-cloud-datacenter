package chou.cloud.datacenter.chou.cloud.datacenter.vm.service;

import chou.cloud.datacenter.chou.cloud.datacenter.instance.entity.Instance;
import chou.cloud.datacenter.chou.cloud.datacenter.instance.service.InstanceService;
import chou.cloud.datacenter.chou.cloud.datacenter.machine.entity.Machine;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by mrasu on 16/11/09.
 */
public class VmService {
    @Autowired
    InstanceService instanceService;

    public void createVm(Machine machine) {
        execShell(machine, "ls");
    }

    public void updateVm(Instance instance) {
        Machine machine = instanceService.getMachine(instance);
        if (machine != null) {
            execShell(machine, "ls");
        }
    }

    private String execShell(Machine machine, String command) {
        try {
            System.out.println(machine.getId());
            Process process = new ProcessBuilder(command).start();
            String text;
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char)c);
            }
            text = builder.toString();
            int ret = process.waitFor();

            if (ret == 0) {
                return text;
            } else {
                return "NG: " + Integer.toString(ret);
            }
        } catch (IOException | InterruptedException e) {
            return e.toString();
        }
    }
}
