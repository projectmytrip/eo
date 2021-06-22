package it.reply.compliance.gdpr.authorization.saml;

import static java.util.Collections.emptyList;
import static org.springframework.security.saml2.core.Saml2X509Credential.Saml2X509CredentialType.DECRYPTION;
import static org.springframework.security.saml2.core.Saml2X509Credential.Saml2X509CredentialType.ENCRYPTION;
import static org.springframework.security.saml2.core.Saml2X509Credential.Saml2X509CredentialType.SIGNING;
import static org.springframework.security.saml2.core.Saml2X509Credential.Saml2X509CredentialType.VERIFICATION;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.security.saml2.login")
@Import(X509CredentialsConverters.class)
public class Saml2LoginBootConfiguration {

    private List<RelyingParty> relyingParties;

    @Bean
    @ConditionalOnMissingBean
    public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() {
        return new InMemoryRelyingPartyRegistrationRepository(getRelyingParties(relyingParties));
    }

    public void setRelyingParties(List<RelyingParty> providers) {
        this.relyingParties = providers;
    }

    private List<RelyingPartyRegistration> getRelyingParties(List<RelyingParty> sampleRelyingParties) {
        return sampleRelyingParties.stream()
                .map(p -> RelyingPartyRegistration.withRegistrationId(p.getRegistrationId())
                        .entityId(p.getEntityId())
                        .assertionConsumerServiceLocation(p.getConsumerUrl())
                        .signingX509Credentials(c -> c.addAll(p.getSigningCredentials()))
                        .decryptionX509Credentials(c -> c.addAll(p.getSigningCredentials()))
                        .assertingPartyDetails(builder -> builder.entityId(p.getIdpEntityId())
                                .singleSignOnServiceLocation(p.getWebSsoUrl())
                                .verificationX509Credentials(c -> c.addAll(p.getProviderCredentials()))
                                .encryptionX509Credentials(c -> c.addAll(p.getProviderCredentials())))
                        .build())
                .collect(Collectors.toList());
    }

    @Bean
    public Converter<HttpServletRequest, RelyingPartyRegistration> getResolver() {
        return new DefaultRelyingPartyRegistrationResolver(relyingPartyRegistrationRepository());
    }

    @Bean
    public Saml2MetadataFilter metadataFilter() {
        return new Saml2MetadataFilter(getResolver(), new OpenSamlMetadataResolver());
    }

    @Data
    public static class RelyingParty {

        private String entityId;
        private String idpEntityId;
        private List<Saml2X509Credential> signingCredentials = emptyList();
        private List<X509Certificate> verificationCredentials = emptyList();
        private String registrationId;
        private String webSsoUrl;
        private String localSpEntityIdTemplate;
        private String consumerUrl;

        public void setSigningCredentials(List<X509KeyCertificatePair> credentials) {
            this.signingCredentials = credentials.stream()
                    .map(c -> new Saml2X509Credential(c.getPrivateKey(), c.getCertificate(), SIGNING, DECRYPTION))
                    .collect(Collectors.toList());
        }

        public void setVerificationCredentials(List<X509Certificate> credentials) {
            this.verificationCredentials = new LinkedList<>(credentials);
        }

        public List<Saml2X509Credential> getProviderCredentials() {
            LinkedList<Saml2X509Credential> result = new LinkedList<>();
            for (X509Certificate c : getVerificationCredentials()) {
                result.add(new Saml2X509Credential(c, ENCRYPTION, VERIFICATION));
            }
            return result;
        }

        public String getConsumerUrl() {
            return consumerUrl + Saml2WebSsoAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;
        }
    }

    public static class X509KeyCertificatePair {

        private RSAPrivateKey privateKey;
        private X509Certificate certificate;

        public RSAPrivateKey getPrivateKey() {
            return this.privateKey;
        }

        public void setPrivateKey(RSAPrivateKey privateKey) {
            this.privateKey = privateKey;
        }

        public X509Certificate getCertificate() {
            return certificate;
        }

        public void setCertificate(X509Certificate certificate) {
            this.certificate = certificate;
        }

    }

}