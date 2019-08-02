/* Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0 */
package com.aws.iot.gg2k;

import static com.aws.iot.dependency.State.*;

public class GenericExternalService extends GGService {
    public GenericExternalService(com.aws.iot.config.Topics c) {
        super(c);
    }
    @Override
    public void install() {
        log().significant("install", this);
        run("install", false, null);
        super.install();
    }
    @Override
    public void awaitingStartup() {
        log().significant("awaitingStartup", this);
        run("awaitingStartup", false, null);
        super.awaitingStartup();
    }
    @Override
    public void startup() {
        log().significant("startup", this);
        if (!run("startup", false, exit -> {
            if (exit == 0) {
                setState(Finished);
                log().significant("Finished", GenericExternalService.this);
            } else {
                setState(Errored);
                log().error("Failed", exit, this);
            }
        }))
            setState(Finished);
    }
    @Override
    public void shutdown() {
        log().significant("shutdown", this);
        run("shutdown", false, null);
    }

}
