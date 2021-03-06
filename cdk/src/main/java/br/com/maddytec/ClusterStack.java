package br.com.maddytec;

import lombok.Getter;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;

public class ClusterStack extends Stack {

    @Getter
    private Cluster cluster;

    public ClusterStack(final Construct scope, final String id, Vpc vpc) {
        super(scope, id, null);

        cluster = Cluster.Builder.create(this, id)
                .clusterName("cluster-maddytec-01")
                .vpc(vpc)
                .build();
    }
}
