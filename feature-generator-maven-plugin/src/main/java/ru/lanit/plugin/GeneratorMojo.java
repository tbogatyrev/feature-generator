package ru.lanit.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lanit.generator.ApiTestsGenerator;
import ru.lanit.utils.ReflectionsUtil;

import java.net.URI;
import java.util.Map;

import static java.net.URI.create;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.INSTALL, threadSafe = true)
public class GeneratorMojo extends AbstractMojo {

    private final Logger LOGGER = LoggerFactory.getLogger(GeneratorMojo.class);

    @Parameter(name = "swaggerPath", property = "feature.generator.maven.plugin.swaggerPath")
    private String swaggerPath;

    @Parameter(name = "swaggerUrl", property = "feature.generator.maven.plugin.swaggerUrl")
    private String swaggerUrl;

    @Parameter(name = "pathToDownloadSwagger", property = "feature.generator.maven.plugin.pathToDownloadSwagger")
    private String pathToDownloadSwagger;

    @Parameter(name = "generateAdditionalFiles", property = "feature.generator.maven.plugin.generateAdditionalFiles")
    private boolean generateAdditionalFiles;

    @Parameter(name = "swaggerCharset", property = "test.generator.maven.plugin.swaggerCharset")
    private String swaggerCharset;

    @Parameter(name = "pomValues")
    protected Map<?, ?> pomValues;

    @Override
    public void execute() throws MojoExecutionException {

        ReflectionsUtil.registerUrlTypes();

        ApiTestsGenerator testsGenerator;

        try {
            if (swaggerPath == null && swaggerUrl == null) {
                throw new MojoExecutionException("The test generator requires 'swaggerPath' or 'swaggerUrl'");
            }

            URI uri = swaggerPath != null ? create(swaggerPath) : create(swaggerUrl);

            testsGenerator = new ApiTestsGenerator(uri);
            if (Boolean.TRUE.equals(generateAdditionalFiles)) {

                testsGenerator.generateAdditionalFiles();

                if (pomValues == null) {
                    LOGGER.error("A pomValues (pomValues) is required.");
                    throw new MojoExecutionException("The test generator requires 'pomValues'");
                }
                if (pomValues.containsKey("groupId")) {
                    testsGenerator.setGroupId(pomValues.get("groupId").toString());
                }
                if (pomValues.containsKey("artifactId")) {
                    testsGenerator.setArtifactId(pomValues.get("artifactId").toString());
                }
                if (pomValues.containsKey("projectVersion")) {
                    testsGenerator.setProjectVersion(pomValues.get("projectVersion").toString());
                }
            }
            testsGenerator.generate();
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException(
                    "Test generation failed. See above for the full exception.");
        }
    }
}
