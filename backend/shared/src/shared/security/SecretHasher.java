package shared.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

public class SecretHasher {
	private int iterations;
	private int memlimit;
	private int hashLength;
	private int parallelism;

	/**
	 * @param iterations Number of Argon2 iterations
	 * @param memlimit Amount of memory in KB allocated to the argon generator
	 * @param hashLength Fixed-size for the hash
	 * @param parallelism Amount of cores in parallel assigned to calculate Argon2 Hashes
	 */
	public SecretHasher(int iterations, int memlimit, int hashLength, int parallelism) {
		this.iterations		= iterations;
		this.memlimit			= memlimit;
		this.parallelism	= parallelism;
		this.hashLength		= hashLength;
	}

	/**
	 * @return A random generated salt, which should then be saved
	 */
	public String generateSalt16Byte() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	/**
	 * @param secret	The secret in plain-text
	 * @param salt		Should be different for each and every password to prevent rainbow tables
	 * @return The secret as an hash with size[hashLength] using the given salt
	 */
	public String hashSecretWithSalt(String secret, String salt) {
		byte[] hash = new byte[hashLength];

		Argon2Parameters.Builder builder = 
			new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
				.withVersion(Argon2Parameters.ARGON2_VERSION_13)
				.withIterations(iterations)
				.withMemoryAsKB(memlimit)
				.withSalt(Base64.getDecoder().decode(salt))
				.withParallelism(parallelism);

		Argon2BytesGenerator gen = new Argon2BytesGenerator();
			gen.init(builder.build());
			gen.generateBytes(secret.getBytes(StandardCharsets.UTF_8), hash, 0, hash.length);

		return Base64.getEncoder().encodeToString(hash);
	}
}
