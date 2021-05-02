package br.com.maddytec;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.services.ecs.Cluster;

public class CdkApp {
    public static void main(final String[] args) {
        App app = new App();

        VpcStack vpc = new VpcStack(app, "Vpc");
        ClusterStack cluster = new ClusterStack(app, "Cluster", vpc.getVpc());
        cluster.addDependency(vpc);

        app.synth();
    }
}
