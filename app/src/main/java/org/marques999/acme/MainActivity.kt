package org.marques999.acme

import android.app.AlertDialog
import android.app.Fragment
import android.app.FragmentManager

import android.os.Bundle

import android.support.v7.app.AppCompatActivity

import android.util.Base64

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import org.marques999.acme.api.*
import org.marques999.acme.common.HttpErrorHandler
import org.marques999.acme.model.Token
import org.marques999.acme.view.ProductFragment

import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        application = getApplication() as AcmeStore

        application.authenticationApi
            .login("admin", "admin")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onAuthorized, HttpErrorHandler(this))
    }

    private fun changeFragment(fragment: Fragment, cleanStack: Boolean = false) {

        val fragmentTransaction = fragmentManager.beginTransaction()

        if (cleanStack) {
            clearBackStack()
        }

        fragmentTransaction.replace(R.id.activity_base_content, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun clearBackStack() {

        if (fragmentManager.backStackEntryCount <= 0) {
            return
        }

        fragmentManager.popBackStack(
            fragmentManager.getBackStackEntryAt(0).id, FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onBackPressed() {

        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    private fun generateKeypair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(368)
        return keyPairGenerator.generateKeyPair()
    }

    private val keyFactory = KeyFactory.getInstance("RSA")

    private fun encodePrivateKey(privateKey: PrivateKey): String {
        return "-----BEGIN RSA PRIVATE KEY-----\n" + Base64.encodeToString(
            privateKey.encoded, Base64.DEFAULT
        ) + "-----END RSA PRIVATE KEY-----\n"
    }

    private fun encodePublicKey(publicKey: PublicKey): String {

        return "-----BEGIN PUBLIC KEY-----\n" + Base64.encodeToString(
            publicKey.encoded, Base64.DEFAULT
        ) + "-----END PUBLIC KEY-----\n"
    }

    private val publicKey = decodePublicKey(
        """-----BEGIN PUBLIC KEY-----
MEowDQYJKoZIhvcNAQEBBQADOQAwNgIvAL1L9h1N9xqNe0I4ddyjKD6lv0ArcEhBJbU550urvmvJ
qa1Rm8Zr+V0+VCp9swcCAwEAAQ==
-----END PUBLIC KEY-----"""
    )

    private val privateKey = decodePrivateKey(
        """-----BEGIN PRIVATE KEY-----
MIIBAgIBADANBgkqhkiG9w0BAQEFAASB7TCB6gIBAAIvAL1L9h1N9xqNe0I4ddyjKD6lv0ArcEhB
JbU550urvmvJqa1Rm8Zr+V0+VCp9swcCAwEAAQIuQMQ3rekaDaywqoSU1uu//kdJe1Qhc6a2yGVi
IGzGWDohXDFi/BBaon6D5fJL6QIYAPlTZgR1+jRKpQ9SD687Y4+J+hzwtE+DAhgAwl0wx2zRyIuc
cFySxq3/scDNwAF3Ey0CFybw+7IeqyGXtwgZjRGVeQtmRYZXohH5AhgAg3MJQWaMPqiFJczGC460
BmCSBlA3WwUCGADa450Hyr7r45CqM8xJkERvv3ZuJonBVQ==
-----END PRIVATE KEY-----"""
    )

    private fun decodePrivateKey(privateKey: String): PrivateKey {

        val publicKeyContent = privateKey.replace(
            "\\n",
            ""
        ).replaceFirst(
            "-----BEGIN PRIVATE KEY-----",
            ""
        ).replaceFirst(
            "-----END PRIVATE KEY-----",
            ""
        )

        return keyFactory.generatePrivate(
            PKCS8EncodedKeySpec(Base64.decode(publicKeyContent, Base64.DEFAULT))
        ) as RSAPrivateKey
    }

    private fun decodePublicKey(publicKey: String): PublicKey {

        val publicKeyContent = publicKey.replace(
            "\\n",
            ""
        ).replaceFirst(
            "-----BEGIN PUBLIC KEY-----",
            ""
        ).replaceFirst(
            "-----END PUBLIC KEY-----",
            ""
        )

        return keyFactory.generatePublic(
            X509EncodedKeySpec(Base64.decode(publicKeyContent, Base64.DEFAULT))
        ) as RSAPublicKey
    }

    private val onAuthorized = Consumer<Token> {

        AlertDialog.Builder(this)
            .setTitle("Authorized")
            .setMessage(it.token)
            .setPositiveButton(android.R.string.ok, null)
            .show()

        application.acmeApi = AcmeProvider(it.token, CryptographyProvider(privateKey)).provideAcme()
        changeFragment(ProductFragment())
    }

    private lateinit var application: AcmeStore
}
