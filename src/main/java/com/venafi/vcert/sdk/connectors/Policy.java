package com.venafi.vcert.sdk.connectors;

import com.venafi.vcert.sdk.endpoint.AllowedKeyConfiguration;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class Policy {
    private Collection<String> subjectCNRegexes;
    private Collection<String> subjectORegexes;
    private Collection<String> subjectOURegexes;
    private Collection<String> subjectSTRegexes;
    private Collection<String> subjectLRegexes;
    private Collection<String> subjectCRegexes;
    private List<AllowedKeyConfiguration> allowedKeyConfigurations;
    private Collection<String> dnsSanRegExs;
    private Collection<String> ipSanRegExs;
    private Collection<String> emailSanRegExs;
    private Collection<String> uriSanRegExs;
    private Collection<String> upnSanRegExs;
    private boolean allowWildcards;
    private boolean allowKeyReuse;
}