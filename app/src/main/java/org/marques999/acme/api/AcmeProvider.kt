package org.marques999.acme.api

class AcmeProvider(private val token: String, private val crypto: CryptographyProvider) {

    fun provideAcme(): AcmeService {
        return AcmeService(AcmeFactory.createAuthorized(token), crypto)
    }
}