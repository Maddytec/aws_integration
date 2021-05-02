package br.com.maddytec;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;

public class ClusterStack extends Stack {

    public ClusterStack(final Construct scope, final String id, Vpc vpc) {
        super(scope, id, null);

        Cluster.Builder.create(this, id)
                .clusterName("cluster-maddytec-01")
                .vpc(vpc)
                .build();
    }
}
