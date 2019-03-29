package com.venafi.vcert.sdk.connectors.cloud;


import com.venafi.vcert.sdk.connectors.cloud.domain.UserAccount;
import com.venafi.vcert.sdk.connectors.cloud.domain.UserDetails;
import com.venafi.vcert.sdk.utils.FeignUtils;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Collections.singletonList;

public interface Cloud {
    @Headers("tppl-api-key: {apiKey}")
    @RequestLine("GET /v1/useraccounts")
    UserDetails authorize(@Param("apiKey") String apiKey);

    static Cloud connect(String baseUrl) {
        return FeignUtils.client(Cloud.class, baseUrl);
    }

    @Headers("tppl-api-key: {apiKey}")
    @RequestLine("GET /v1/zones/tag/{zone}")
    Zone zoneByTag(@Param("zone") String zone, @Param("apiKey") String apiKey);

    @Headers("tppl-api-key: {apiKey}")
    @RequestLine("GET /v1/certificatepolicies/{id}")
    CertificatePolicy policyById(@Param("id") String id, @Param("apiKey") String apiKey);

    @Headers({"tppl-api-key: {apiKey}", "Content-Type: application/json"})
    @RequestLine("POST /v1/useraccounts")
    UserDetails register(@Param("apiKey") String apiKey, UserAccount userAccount);

    @Headers({"tppl-api-key: {apiKey}", "Content-Type: application/json"})
    @RequestLine("POST /v1/certificatesearch")
    CertificateSearchResponse searchCertificates(@Param("apiKey") String apiKey, SearchRequest searchRequest);

    @Data
    @NoArgsConstructor
    class SearchRequest {
        private Expression expression;
        private Object ordering;
        private Paging paging;

        SearchRequest(Expression expression) {
            this.expression = expression;
        }

        static SearchRequest findByFingerPrint(String fingerprint){
            return new SearchRequest(
                    new Cloud.Expression(singletonList(
                            new Cloud.Operand("fingerprint", "MATCH", fingerprint))));
        }
    }

    @Data
    @AllArgsConstructor
    class Expression {
        private List<Operand> operands;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Operand {
        private String field;
        private String operator;
        private Object value;
    }

    @Data
    class Paging {
        private Integer pageNumber;
        private Integer pageSize;
    }

    @Data
    class CertificateSearchResponse {
        private Integer count;
        private List<Certificate> certificates;
    }

    @Data
    class Certificate {
        private String id;
        private String managedCertificateId;
        private List<String> subjectCN;
    }
}