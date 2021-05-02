package br.com.maddytec;

import lombok.Getter;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.Vpc;

public class VpcStack extends Stack {

    @Getter
    private Vpc vpc;

    public VpcStack(final Construct scope, final String id) {
        super(scope, id, null);

       vpc = Vpc.Builder.create(this, "VpcMaddytec01")
                .maxAzs(3)
                .build();
    }
}
