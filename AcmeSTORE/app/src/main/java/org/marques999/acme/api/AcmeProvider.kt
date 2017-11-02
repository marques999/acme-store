package org.marques999.acme.api

import org.marques999.acme.model.Session

class AcmeProvider(private val session: Session, private val crypto: CryptographyService) {

    fun provideAcme(): AcmeService {
        return AcmeService(AcmeFactory.createAuthorized(session.token!!), session.username, crypto)
    }
}