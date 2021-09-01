package com.github.marschall.spring.batch.benchmarks;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobKeyGenerator;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.util.Assert;

public final class StreamingJobKeyGenerator implements JobKeyGenerator<JobParameters> {

  @Override
  public String generateKey(JobParameters source) {
    Assert.notNull(source, "source must not be null");
    Map<String, JobParameter> props = source.getParameters();

    HashingOutputStream hashingStream = new HashingOutputStream();
    try (Writer writer = new OutputStreamWriter(hashingStream, UTF_8)) {
      List<String> keys = new ArrayList<>(props.keySet());
      Collections.sort(keys);
      for (String key : keys) {
        JobParameter jobParameter = props.get(key);
        if(jobParameter.isIdentifying()) {
          writer.write(key);
          writer.write('=');
          Object value = jobParameter.getValue();
          if (value != null) {
            writer.write(jobParameter.toString());
          }
          writer.write(';');
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to compute MD-5 hash", e);
    }

    byte[] md5 = hashingStream.digest();
    return hexEncode(md5);
  }
  
  private static String hexEncode(byte[] md5) {
    byte[] hex = new byte[32];
    for (int i = 0; i < md5.length; i++) {
      byte b = md5[i];
      hex[i * 2] = (byte) Character.forDigit((b >>> 4) & 0xF, 16);
      hex[i * 2 + 1] = (byte) Character.forDigit(b & 0xF, 16);
      
    }
    return new String(hex, ISO_8859_1);
  }

  static final class HashingOutputStream extends OutputStream {

    private MessageDigest messageDigest;

    HashingOutputStream() {
      try {
        this.messageDigest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
        throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", e);
      }
    }

    @Override
    public void write(int b) throws IOException {
      this.messageDigest.update((byte) b);
    }

    @Override
    public void write(byte[] b) {
      this.messageDigest.update(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
      this.messageDigest.update(b, off, len);
    }

    byte[] digest() {
      return this.messageDigest.digest();
    }

  }

}
