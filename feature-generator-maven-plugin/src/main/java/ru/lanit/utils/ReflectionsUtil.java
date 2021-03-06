package ru.lanit.utils;

import org.reflections.vfs.Vfs;
import org.reflections.vfs.Vfs.Dir;
import org.reflections.vfs.Vfs.File;
import org.reflections.vfs.Vfs.UrlType;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReflectionsUtil {
    private static final String FILE_PROTOCOL = "file";
    private static final List<String> ENDINGS = Arrays.asList(".pom", ".jnilib", "*");

    public static void registerUrlTypes() {
        final List<UrlType> urlTypes = new LinkedList<>();
        urlTypes.add(new EmptyUrlType(ENDINGS));
        urlTypes.addAll(Arrays.asList(Vfs.DefaultUrlTypes.values()));
        Vfs.setDefaultURLTypes(urlTypes);
    }

    private static class EmptyUrlType implements UrlType {

        private final List<String> endings;

        private EmptyUrlType(final List<String> endings) {
            this.endings = endings;
        }

        @Override
        public boolean matches(URL url) {
            final String protocol = url.getProtocol();
            final String externalForm = url.toExternalForm();
            if (!protocol.equals(FILE_PROTOCOL)) {
                return false;
            }
            for (String ending : endings) {
                if (externalForm.endsWith(ending)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Dir createDir(final URL url) throws Exception {
            return emptyVfsDir(url);
        }

        private static Dir emptyVfsDir(final URL url) {
            return new Dir() {
                @Override
                public String getPath() {
                    return url.toExternalForm();
                }

                @Override
                public Iterable<File> getFiles() {
                    return Collections.emptyList();
                }

                @Override
                public void close() {}
            };
        }
    }
}