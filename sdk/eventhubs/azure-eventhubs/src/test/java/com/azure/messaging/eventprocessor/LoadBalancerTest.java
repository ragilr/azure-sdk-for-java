package com.azure.messaging.eventprocessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;

public class LoadBalancerTest {

    @Test
    public void testLoadBalance() {
        Set<String> ehPartitions = new HashSet<>(Arrays.asList("0", "1", "2", "3", "4", "5"));

        PartitionManager pos = new PartitionManagerImpl();
        Map<String, Ownership> ownedPartitions = new ConcurrentHashMap<>();
        LoadBalancer loadBalancerA = new LoadBalancer("a", "b", "c", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerB = new LoadBalancer("b", "b", "c", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerC = new LoadBalancer("c", "b", "c", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerD = new LoadBalancer("d", "b", "c", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerE = new LoadBalancer("e", "b", "c", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerF = new LoadBalancer("f", "b", "c", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerG = new LoadBalancer("g", "b", "c", ehPartitions, pos,
            ownedPartitions);


        loadBalancerA.balance();
        loadBalancerA.balance();
        loadBalancerA.balance();
        loadBalancerA.balance();
        loadBalancerA.balance();
        loadBalancerA.balance();

        System.out.println("__________________________________");
        loadBalancerB.balance();
        loadBalancerB.balance();
        loadBalancerB.balance();
        loadBalancerB.balance();
        loadBalancerB.balance();
        loadBalancerB.balance();

        System.out.println("__________________________________");
        loadBalancerD.balance();
        loadBalancerD.balance();
        loadBalancerD.balance();
        loadBalancerD.balance();
        loadBalancerD.balance();
        loadBalancerD.balance();

        System.out.println("__________________________________");
        loadBalancerE.balance();
        loadBalancerE.balance();
        loadBalancerE.balance();
        loadBalancerE.balance();
        loadBalancerE.balance();
        loadBalancerE.balance();


        System.out.println("__________________________________");
        loadBalancerF.balance();
        loadBalancerF.balance();
        loadBalancerF.balance();
        loadBalancerF.balance();
        loadBalancerF.balance();
        loadBalancerF.balance();


        System.out.println("__________________________________");
        loadBalancerG.balance();
        loadBalancerG.balance();
        loadBalancerG.balance();
        loadBalancerG.balance();
        loadBalancerG.balance();
        loadBalancerG.balance();

        System.out.println("__________________________________");
        loadBalancerC.balance();
        loadBalancerC.balance();
        loadBalancerC.balance();
        loadBalancerC.balance();
        loadBalancerC.balance();
        loadBalancerC.balance();

    }

}
