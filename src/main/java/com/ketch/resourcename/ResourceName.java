package com.ketch.resourcename;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A ResourceName describes an expression of a resource.
 *
 */
public class ResourceName {

    private static final String PREFIX = "srn";

    private static final String DEFAULT_RESOURCE_VALUE = "";

    private static final char SEPARATOR = ':';

    private static final char INFO_SEPARATOR = '/';

    private static final String SERVICE = "service";

    private static final String REGION = "region";

    private static final String TENANT = "tenant";

    private static final String ACCOUNT = "account";

    private static final String ORGANIZATION = "organization";

    private static final String RESOURCE = "resource";

    private static final String[] ATTRIBUTES = {
            SERVICE,
            REGION,
            TENANT,
            ACCOUNT,
            ORGANIZATION,
            RESOURCE
    };

    private final Map<String, String> _attributes = new HashMap<String, String>();

    private final List<String> _info = new LinkedList<String>();

    public static ResourceName newInstance() {
        return newInstance(null);
    }

    public static ResourceName newInstance(String[] parts) {
        ResourceName rn = new ResourceName();
        if (parts != null && parts.length > 0) {
            rn = rn.withService(parts[0]);
            if (parts.length > 1) {
                rn = rn.withRegion(parts[1]);
                if (parts.length > 2) {
                    rn = rn.withTenant(parts[2]);
                    if (parts.length > 3) {
                        rn = rn.withAccount(parts[3]);
                        if (parts.length > 4) {
                            rn = rn.withOrganization(parts[4]);
                            if (parts.length > 5) {
                                rn = rn.withResource(parts[5]);
                                if (parts.length > 6) {
                                    rn = rn.withInfo(Arrays.copyOfRange(parts, 6, parts.length));
                                }
                            }
                        }
                    }
                }
            }
        }
        return rn;
    }

    public ResourceName withService(String service) {
        _attributes.put(SERVICE, service);
        return this;
    }

    public ResourceName withRegion(String region) {
        _attributes.put(REGION, region);
        return this;
    }

    public ResourceName withTenant(String tenant) {
        _attributes.put(TENANT, tenant);
        return this;
    }

    public ResourceName withAccount(String account) {
        _attributes.put(ACCOUNT, account);
        return this;
    }

    public ResourceName withOrganization(String organization) {
        _attributes.put(ORGANIZATION, organization);
        return this;
    }

    public ResourceName withResource(String resource) {
        _attributes.put(RESOURCE, resource);
        return this;
    }

    public ResourceName withInfo(String... info) {
        _info.addAll(Arrays.asList(info));
        return this;
    }

    public String getService() {
        return getAttribute(SERVICE);
    }

    public String getRegion() {
        return getAttribute(REGION);
    }

    public String getTenant() {
        return getAttribute(TENANT);
    }

    public String getAccount() {
        return getAttribute(ACCOUNT);
    }

    public String getOrganization() {
        return getAttribute(ORGANIZATION);
    }

    public String getResource() {
        return getAttribute(RESOURCE);
    }

    private String getAttribute(String attribute) {
        return _attributes.getOrDefault(attribute, DEFAULT_RESOURCE_VALUE);
    }

    public List<String> getInfo() {
        return _info;
    }

    /**
     * Return a String representation of this resource
     * @return the resource represented as a String
     */
    public String encode() {
        List<String> rnl = new LinkedList<String>();
        rnl.add(PREFIX);
        for (String attr : ATTRIBUTES) {
            rnl.add(getAttribute(attr));
        }

        String rns = StringUtils.join(rnl, SEPARATOR);
        if (_info.size() > 0) {
            List<String> rnip = new LinkedList<String>();
            rnip.add(rns);
            rnip.addAll(_info);
            return StringUtils.join(rnip, INFO_SEPARATOR);
        }
        return rns;

    }

    /**
     * Decode the specified string as a Ketch Resource
     * @param s the string to decode
     * @return the decoded ResourceName or InvalidArgumentException if bad input
     */
    public static ResourceName decode(String s) {
        String[] parts = StringUtils.splitByWholeSeparatorPreserveAllTokens(s, String.valueOf(SEPARATOR), 7);
        if (parts.length != 7) {
            throw new IllegalArgumentException(
                    String.format(
                            "decode: bad format for resource name %s",
                            s
                    )
            );
        }
        if (!parts[0].equals(PREFIX)) {
            throw new IllegalArgumentException(
                    String.format(
                            "decode: prefix %s required for resource name %s", PREFIX, s
                    )
            );
        }
        String[] rsp = Arrays.copyOfRange(parts, 1, 6);
        ResourceName rn = ResourceName.newInstance(rsp);
        String[] rip = StringUtils.split(parts[6], INFO_SEPARATOR);
        if (rip.length > 0) {
            rn = rn.withResource(rip[0]);
            if (rip.length > 1) {
                rn = rn.withInfo(Arrays.copyOfRange(rip, 1, rip.length));
            }
        }
        return rn;
    }

    public boolean equals(Object o) {
        if (o instanceof ResourceName) {
            ResourceName other = (ResourceName)o;
            for (String attr : ATTRIBUTES) {
                if (!getAttribute(attr).equals(other.getAttribute(attr))) {
                    return false;
                }
            }
            return getInfo().equals(other.getInfo());
        }
        return false;
    }
}
