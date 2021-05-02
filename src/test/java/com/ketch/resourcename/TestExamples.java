package com.ketch.resourcename;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestExamples {

    public static final Map<Object, Object>[] EXAMPLES = new Map[]{
            Stream.of(new Object[][]{
                    {"s", "srn:service:region:tenant:account:organization:resource/info1/info2"},
                    {"n", ResourceName.newInstance(
                            new String[]{
                                    "service",
                                    "region",
                                    "tenant",
                                    "account",
                                    "organization",
                                    "resource",
                                    "info1",
                                    "info2"
                            })
                    },
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])),
            Stream.of(new Object[][]{
                    {"s", "srn:service:region:tenant:account:organization:resource/info1"},
                    {"n", ResourceName.newInstance(
                            new String[]{
                                    "service",
                                    "region",
                                    "tenant",
                                    "account",
                                    "organization",
                                    "resource",
                                    "info1"
                            })
                    },
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])),
            Stream.of(new Object[][]{
                    {"s", "srn:service:region:tenant:account:organization:resource"},
                    {"n", ResourceName.newInstance(
                            new String[]{
                                    "service",
                                    "region",
                                    "tenant",
                                    "account",
                                    "organization",
                                    "resource"
                            })
                    },
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])),
            Stream.of(new Object[][]{
                    {"s", "srn:service:region:tenant::organization:resource"},
                    {"n", ResourceName.newInstance().
                            withService("service").
                            withRegion("region").
                            withTenant("tenant").
                            withOrganization("organization").
                            withResource("resource")
                    },
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])),
            Stream.of(new Object[][]{
                    {"s", "srn:service:*:tenant::organization:*"},
                    {"n", ResourceName.newInstance().
                            withService("service").
                            withRegion("*").
                            withTenant("tenant").
                            withOrganization("organization").
                            withResource("*")
                    },
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])),
            Stream.of(new Object[][]{
                    {"s", "srn:service:region:tenant:account:organization:id/_mkto_trk/id:844&token:_mch-dev.axonic"},
                    {"n", ResourceName.newInstance(
                            new String[] {
                                    "service",
                                    "region",
                                    "tenant",
                                    "account",
                                    "organization",
                                    "id",
                                    "_mkto_trk",
                                    "id:844&token:_mch-dev.axonic"
                            })
                    },
            }).collect(Collectors.toMap(data -> data[0], data -> data[1]))
    };
}

