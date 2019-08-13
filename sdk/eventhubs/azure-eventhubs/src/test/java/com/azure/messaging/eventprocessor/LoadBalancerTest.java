package com.azure.messaging.eventprocessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import org.junit.Test;

public class LoadBalancerTest {

    @Test
    public void testLoadBalance() {
        Set<String> ehPartitions = new HashSet<>(Arrays.asList("0", "1", "2", "3", "4", "5"));

        PartitionManager pos = new PartitionManagerImpl();
        Map<String, Ownership> ownedPartitions = new ConcurrentHashMap<>();
        LoadBalancer loadBalancerA = new LoadBalancer("a", "event-hub-name", "consumer-group", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerB = new LoadBalancer("b", "event-hub-name", "consumer-group", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerC = new LoadBalancer("c", "event-hub-name", "consumer-group", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerD = new LoadBalancer("d", "event-hub-name", "consumer-group", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerE = new LoadBalancer("e", "event-hub-name", "consumer-group", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerF = new LoadBalancer("f", "event-hub-name", "consumer-group", ehPartitions, pos,
            ownedPartitions);
        LoadBalancer loadBalancerG = new LoadBalancer("g", "event-hub-name", "consumer-group", ehPartitions, pos,
            ownedPartitions);

        // run each load balancer 6 times
        IntStream.range(0, 6). forEach(unused -> loadBalancerA.balance());
        IntStream.range(0, 6). forEach(unused -> loadBalancerB.balance());
        IntStream.range(0, 6). forEach(unused -> loadBalancerC.balance());
        IntStream.range(0, 6). forEach(unused -> loadBalancerD.balance());
        IntStream.range(0, 6). forEach(unused -> loadBalancerE.balance());
        IntStream.range(0, 6). forEach(unused -> loadBalancerF.balance());
        IntStream.range(0, 6). forEach(unused -> loadBalancerG.balance());
        IntStream.range(0, 6). forEach(unused -> loadBalancerC.balance());
    }

}
