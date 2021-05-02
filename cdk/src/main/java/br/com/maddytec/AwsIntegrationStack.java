package br.com.maddytec;

import lombok.Getter;
import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.LogGroup;

public class AwsIntegrationStack extends Stack {

    @Getter
    private Cluster cluster;

    public AwsIntegrationStack(final Construct scope, final String id, Cluster cluster) {
        super(scope, id, null);

        ApplicationLoadBalancedFargateService awsIntegrationService = ApplicationLoadBalancedFargateService.Builder.create(this, "AWS_INTEGRATION")
                .serviceName("aws_integration")
                .cluster(cluster)
                .desiredCount(2)
                .listenerPort(8080)
                .cpu(512)
                .memoryLimitMiB(1024)
                .publicLoadBalancer(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .containerName("aws_integration")
                                .image(ContainerImage.fromRegistry("maddytec/aws_integration:1.1.1"))
                                .containerPort(8080)
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder.create(this, "aws_integration_group")
                                                .logGroupName("aws_integration")
                                                .removalPolicy(RemovalPolicy.DESTROY)
                                                .build())
                                        .streamPrefix("aws_integration")
                                        .build()))
                                .build())
                .build();

        ScalableTaskCount scalableTaskCount = awsIntegrationService.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(2)
                .maxCapacity(4)
                .build());

        scalableTaskCount.scaleOnCpuUtilization("AwsIntegrationServiceAutoScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(60)
                .scaleInCooldown(Duration.minutes(2))
                .scaleOutCooldown(Duration.minutes(2))
                .build());

        awsIntegrationService.getTargetGroup().configureHealthCheck(new HealthCheck.Builder()
                .path("/actuator/health")
                .port("8080")
                .healthyHttpCodes("200")
                .build());
    }

}
