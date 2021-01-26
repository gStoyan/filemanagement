package filemanagement.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;


public class Certificate {

  public static void main(String[] args)
      throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, OperatorCreationException, IOException {
    Security.addProvider(new BouncyCastleProvider());
    Pair<PrivateKey, Certificate> pair = generateSelfSignedCert("app.example.com");
    JcaPEMWriter writer = new JcaPEMWriter(new PrintWriter(System.out));
    writer.writeObject(pair.getLeft());
    writer.writeObject(pair.getRight());
    writer.flush();
  }

  private final static String bcProvider = BouncyCastleProvider.PROVIDER_NAME;

  public static Pair<PrivateKey, Certificate> generateSelfSignedCert(String commonName)
      throws NoSuchProviderException, NoSuchAlgorithmException, OperatorCreationException, CertificateException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", bcProvider);
    keyPatant.now();
    Instant endDate = startDate.plus(2 * 365, ChronoUnit.DAYS);
    JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
        dnName, certSerialNumber, Date.from(startDatirGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    X500Name dnName = new X500Name("CN=" + commonName);
    BigInteger certSerialNumber = BigInteger.valueOf(System.currentTimeMillis());
    String signatureAlgorithm = "SHA256WithRSA";
    ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm)
        .build(keyPair.getPrivate());
    Instant startDate = Inse), Date.from(endDate), dnName,
        keyPair.getPublic());
    Certificate certificate = new JcaX509CertificateConverter().setProvider(bcProvider)
        .getCertificate(certBuilder.build(contentSigner));
    return Pair.of(keyPair.getPrivate(), certificate);
  }
}