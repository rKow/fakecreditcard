package com.coderdkk.creditcard.fake.report;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class FakeCreditCardGenerationReportCreator {

  private final FakeCreditCardGenerationReportDataProvider dataProvider;

  public Resource get(String generationId, Boolean decrypted) {
    VelocityEngine velocityEngine = new VelocityEngine();
    Properties props = new Properties();
    props.put("resource.loader.class.description", "Velocity Classpath Resource Loader");
    props.put("resource.loader", "class");
    props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    velocityEngine.init(props);

    Template t = velocityEngine.getTemplate("report-templates/fake-credit-card-generation.html");

    VelocityContext context = dataProvider.getContext(generationId, decrypted);

    StringWriter writer = new StringWriter();
    t.merge(context, writer);

    return new ByteArrayResource(writer.toString().getBytes());
  }

}
